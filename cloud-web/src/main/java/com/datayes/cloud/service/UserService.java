package com.datayes.cloud.service;

import com.datayes.cloud.dao.CloudDao;
import com.datayes.cloud.model.Server;
import com.datayes.cloud.model.Service;
import com.datayes.cloud.model.Tenant;
import com.datayes.cloud.model.User;
import com.datayes.cloud.openstack.OpenstackContext;
import com.datayes.cloud.openstack.access.Flavor;
import com.datayes.cloud.openstack.access.Volume;
import com.datayes.cloud.openstack.access.VolumeAttachment;
import com.datayes.cloud.util.CommonUtil;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.SimpleType;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.security.ldap.SpringSecurityLdapTemplate;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.naming.NamingException;
import javax.naming.directory.*;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: changhai
 * Date: 13-9-2
 * Time: 下午12:42
 * DataYes
 */
@org.springframework.stereotype.Service
@Transactional
public class UserService {
    @Autowired
    private CloudDao cloudDao;
    @Autowired
    private OpenstackContextFactory openstackContextFactory;

    private static final String USER_TYPE = "Data.User";
    private static final String USER_CLASS = "person";
    private static final String ADMIN_ACCOUNT = "sso"; 
    
    @PostConstruct
    public void init() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sslContext = SSLContext.getInstance("TLS");
        TrustManager tm = new X509TrustManager() {
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };
        sslContext.init(null, new TrustManager[]{tm}, null);
        SSLContext.setDefault(sslContext);
//        System.setProperty("javax.net.ssl.trustStore", "/Users/changhai/.keystore");
//        System.setProperty("javax.net.ssl.trustStorePassword", "tomcat");
    }

    public boolean check(User user) {
        if (user.getTenantId()<1) return false;
        
        Tenant tenant = cloudDao.get(Tenant.class, user.getTenantId());
        if (tenant == null) return false;
        
        SpringSecurityLdapTemplate ldapTemplate = getLdapTemplate(tenant);
        EqualsFilter filter = new EqualsFilter("cn", user.getName());
        return ldapTemplate.authenticate("CN=Users", filter.toString(), user.getPassword());
        
    }

    public void createUser(Tenant tenant,User user) {
        SpringSecurityLdapTemplate ldapTemplate = getLdapTemplate(tenant);
        String name = user.getName();
        String dc = getDc(tenant);
        ldapTemplate.bind("cn=" + name + ",cn=users", null, getUserAttrs(user,tenant));
        BasicAttribute attr = new BasicAttribute("member", "cn=" + name + ",cn=users," + dc);
        ModificationItem[] members = {new ModificationItem(DirContext.ADD_ATTRIBUTE, attr)};
        ldapTemplate.modifyAttributes("cn=administrators,cn=builtin", members);
        
        user.setTenantId(tenant.getId());
        cloudDao.save(user);
    }
    
    public void updateUser(Tenant tenant, User user){
        SpringSecurityLdapTemplate ldapTemplate = getLdapTemplate(tenant);
        ModificationItem[] mods = {
                new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("sn", user.getSurname())),
                new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("givenName", user.getGivenName())),
                new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("department", user.getDept()))
        };
        String dc = getDc(tenant);
        ldapTemplate.modifyAttributes("cn="+user.getName()+",cn=users", mods);
    }
    
    private SpringSecurityLdapTemplate getAdminLdapTemplate(){
        //TODO:
        return null;
    }

    private SpringSecurityLdapTemplate getLdapTemplate(Tenant tenant) {
        try {
            if (tenant.getAdUrl()==null || tenant.getAdminPassword()==null) return null;
            String address = getAdUrl(tenant);//cloudDao.getAdUrl(tenant.getId());
            String base = getDc(tenant);
            LdapContextSource lcs = new LdapContextSource();
            
            lcs.setUrl(address);
            lcs.setBase(base);
            lcs.setUserDn("cn=" + ADMIN_ACCOUNT + ",cn=users," + base);
            lcs.setPassword(tenant.getAdminPassword());
            lcs.afterPropertiesSet();
            SpringSecurityLdapTemplate template = new SpringSecurityLdapTemplate(lcs);
            return template;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String getAdUrl(Tenant tenant) {
        return "ldaps://"+tenant.getAdUrl()+":636";
    }

    private String getDc(Tenant tenant) {
        String domain = tenant.getDomain();
        String[] dcs = StringUtils.split(domain, '.');
        StringBuilder s = new StringBuilder();
        for (String dc : dcs) {
            if (s.length()>0) s.append(',');
            s.append("dc=").append(dc);
        }
        return s.toString();
    }

    private Attributes getUserAttrs(User user, Tenant tenant) {
        try {
            String name = user.getName();
            Attributes attrs = new BasicAttributes(true);
            String email = name + "@" + tenant.getDomain();
            attrs.put(new BasicAttribute("userPrincipalName", email));
            attrs.put(new BasicAttribute("sAMAccountName", name));
            attrs.put(new BasicAttribute("cn", name));
            attrs.put(new BasicAttribute("mail", email));
            attrs.put(new BasicAttribute("givenName", user.getGivenName()));
            attrs.put(new BasicAttribute("sn", user.getSurname()));
//        attrs.put(new BasicAttribute("description", "desc"));
            attrs.put(new BasicAttribute("company", tenant.getCompany()));
            attrs.put(new BasicAttribute("department", user.getDept()));
            attrs.put(new BasicAttribute("displayName", user.getSurname()+user.getGivenName()));
            //Description is used for workflow and other app to identify human users;
            attrs.put(new BasicAttribute("description", USER_TYPE));
            attrs.put(new BasicAttribute("unicodePwd", ("\"" + user.getPassword() + "\"").getBytes("UTF-16LE")));
            attrs.put(new BasicAttribute("userAccountControl", "544"));

            Attribute objclass = new BasicAttribute("objectClass");
            objclass.add("top");
            objclass.add("person");
            objclass.add("organizationalPerson");
            objclass.add("user");
            attrs.put(objclass);
            return attrs;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAll() {
        return cloudDao.findAll(User.class);
    }

    public User getUser(User user) {
        List<User> users = cloudDao.get(user);
        return users.isEmpty() ? null : users.get(0);
    }

    public void addService(Service service) {
        cloudDao.save(service);
    }

    public List<Service> getServices() {
        return cloudDao.findAll(Service.class);
    }

    public void update(User user) {
        cloudDao.update(user);
    }

    public List<Service> getService(Service service) {
        return cloudDao.get(service);
    }

    public User getUser(long id) {
        return cloudDao.get(User.class, id);
    }

    public void addServer(Server server) {
        cloudDao.save(server);
    }

    public List<Server> getServers(Tenant tenant, String type) {
        Server example = new Server();
        example.setType(type);
        return cloudDao.get(example);
    }

    public List<com.datayes.cloud.openstack.access.Server> getServers(String tenantName) throws IOException {
        OpenstackContext ctx = openstackContextFactory.createContext("datayes_staging");
        List<com.datayes.cloud.openstack.access.Server> servers = ctx
                .get(ctx.getComputeUrl() + "/servers/detail", "servers", CollectionType.construct(List.class, SimpleType.construct(com.datayes.cloud.openstack.access.Server.class)));
        List<Volume> volumes = ctx
                .get(ctx.getVolumeUrl() + "/volumes/detail", "volumes", CollectionType.construct(List.class, SimpleType.construct(Volume.class)));
        Map<String, Flavor> flavors = new HashMap<String, Flavor>();
        for (com.datayes.cloud.openstack.access.Server server : servers) {
            String flavorId = server.getFlavor().getId();
            Flavor flavor;
            if (flavors.containsKey(flavorId)) {
                flavor = flavors.get(flavorId);
            } else {
                flavor = ctx.get(ctx.getComputeUrl() + "/flavors/" + flavorId, "flavor", Flavor.class);
                flavors.put(flavorId, flavor);
            }
            Volume volume = getVolume(server, volumes);
            server.setVcpu(flavor.getVcpus());
            server.setRam(flavor.getRam() / 1024.0);
            server.setDisk(flavor.getDisk() + (volume == null ? 0 : volume.getSize()));
        }
        return servers;
    }

    private Volume getVolume(com.datayes.cloud.openstack.access.Server server, List<Volume> volumes) {
        for (Volume volume : volumes) {
            List<VolumeAttachment> attachments = volume.getAttachments();
            for (VolumeAttachment attachment : attachments) {
                if (server.getId().equals(attachment.getServerId()))
                    return volume;
            }
        }
        return null;
    }
    
    private class UserAttributesMapper implements AttributesMapper {

        public Object mapFromAttributes(Attributes attrs) throws NamingException {
            User user = new User();
            //user.setId(CommonUtil.getRandom());
            user.setName((String)attrs.get("sAMAccountName").get());
            if (attrs.get("givenName")!=null){
                user.setGivenName((String)attrs.get("givenName").get());
            }
            if (attrs.get("sn")!=null){
                user.setSurname((String)attrs.get("sn").get());
            }
            if (attrs.get("department")!=null){
                user.setDept((String)attrs.get("department").get());
            }
            
            return user;
        }

    }

    public List<User> getUsers(Tenant tenant) {
        SpringSecurityLdapTemplate ldapTemplate = getLdapTemplate(tenant);
        EqualsFilter filter = new EqualsFilter("objectclass", USER_CLASS);
        List<User> userList = ldapTemplate.search("CN=Users", filter.toString(),SearchControls.SUBTREE_SCOPE, new UserAttributesMapper());
        for (User user:userList){
            getIdAndPermission(user, tenant.getId());
        }
        return userList;
        
    }

    private void getIdAndPermission(User user, long tenantId) {
        User dbUser = cloudDao.findUserByName(user.getName(), tenantId);
        if (dbUser == null){
            user.setTenantId(tenantId);
            cloudDao.save(user);
        }
        else{
            user.setId(dbUser.getId());
            user.setTenantId(dbUser.getTenantId());
            //user.setPermission(dbUser.getPermission());
        }
    }

}