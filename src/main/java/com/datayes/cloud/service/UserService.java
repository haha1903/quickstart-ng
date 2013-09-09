package com.datayes.cloud.service;

import com.datayes.cloud.dao.CloudDao;
import com.datayes.cloud.model.*;
import com.datayes.cloud.openstack.OpenstackContext;
import com.datayes.cloud.openstack.access.Flavor;
import com.datayes.cloud.openstack.access.Server;
import com.datayes.cloud.openstack.access.Volume;
import com.datayes.cloud.openstack.access.VolumeAttachment;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.SimpleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.ldap.SpringSecurityLdapTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.naming.directory.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: changhai
 * Date: 13-9-2
 * Time: 下午12:42
 * DataYes
 */
@Service
@Transactional
public class UserService {
    @Autowired
    private CloudDao cloudDao;
    @Autowired
    private OpenstackContextFactory openstackContextFactory;
    @Autowired
    private SpringSecurityLdapTemplate ldapTemplate;

    @PostConstruct
    public void init() {
//        System.setProperty("javax.net.ssl.trustStore", "/Users/changhai/.keystore");
//        System.setProperty("javax.net.ssl.trustStorePassword", "tomcat");
    }

    public boolean check(User user) {
        return cloudDao.checkUser(user);
    }

    public void createUser(User user) {
        cloudDao.save(user);
        String tenantDomain = user.getTenant().getName();
        String name = user.getName();
        ldapTemplate.bind("cn=" + name + ",CN=Users,DC=datayestest,DC=com", null, getUserAttrs(user));
        ModificationItem[] members = {new ModificationItem(DirContext.ADD_ATTRIBUTE, new BasicAttribute("member", "cn=" + name + ",CN=Users,DC=datayestest,DC=com"))};
        ldapTemplate.modifyAttributes("CN=Administrators,CN=Builtin,DC=datayestest,DC=com", members);
    }

    private Attributes getUserAttrs(User user) {
        try {
            String name = user.getName();
            String tenantDomain = user.getTenant().getName();
            Attributes attrs = new BasicAttributes(true);
            String email = name + "@" + tenantDomain;
            attrs.put(new BasicAttribute("userPrincipalName", email));
            attrs.put(new BasicAttribute("sAMAccountName", name));
            attrs.put(new BasicAttribute("mail", email));
            attrs.put(new BasicAttribute("givenName", name));
            attrs.put(new BasicAttribute("sn", name));
//        attrs.put(new BasicAttribute("description", "desc"));
            attrs.put(new BasicAttribute("company", tenantDomain));
            attrs.put(new BasicAttribute("department", user.getDept()));
            attrs.put(new BasicAttribute("displayName", name));

            attrs.put(new BasicAttribute("unicodePwd", ("\"" + user.getPassword() + "\"").getBytes("UTF-16LE")));
            attrs.put(new BasicAttribute("userAccountControl", "544"));

            Attribute objclass = new BasicAttribute("objectclass");
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

    public void addService(CloudService service) {
        cloudDao.save(service);
    }

    public List<CloudService> getServices() {
        return cloudDao.findAll(CloudService.class);
    }

    public void update(User user) {
        cloudDao.update(user);
    }

    public List<CloudService> getService(CloudService service) {
        return cloudDao.get(service);
    }

    public User getUser(long id) {
        return cloudDao.get(User.class, id);
    }

    public void addServer(CloudServer server) {
        cloudDao.save(server);
    }

    public List<CloudServer> getServers(Tenant tenant, String type) {
        CloudServer example = new CloudServer();
        example.setType(type);
        example.setTenant(tenant);
        return cloudDao.get(example);
    }

    public List<Server> getServers(String tenantName) throws IOException {
        OpenstackContext ctx = openstackContextFactory.createContext("datayes_staging");
        List<Server> servers = ctx
                .get(ctx.getComputeUrl() + "/servers/detail", "servers", CollectionType.construct(List.class, SimpleType.construct(Server.class)));
        List<Volume> volumes = ctx
                .get(ctx.getVolumeUrl() + "/volumes/detail", "volumes", CollectionType.construct(List.class, SimpleType.construct(Volume.class)));
        Map<String, Flavor> flavors = new HashMap<String, Flavor>();
        for (Server server : servers) {
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
            server.setRam(flavor.getRam());
            server.setDisk(flavor.getDisk() + (volume == null ? 0 : volume.getSize()));
        }
        return servers;
    }

    private Volume getVolume(Server server, List<Volume> volumes) {
        for (Volume volume : volumes) {
            List<VolumeAttachment> attachments = volume.getAttachments();
            for (VolumeAttachment attachment : attachments) {
                if (server.getId().equals(attachment.getServerId()))
                    return volume;
            }
        }
        return null;
    }
}