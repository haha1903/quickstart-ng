package com.datayes.cloud.service;

import com.datayes.cloud.dao.CloudDao;
import com.datayes.cloud.model.CloudService;
import com.datayes.cloud.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.ldap.SpringSecurityLdapTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.naming.directory.*;
import java.io.UnsupportedEncodingException;
import java.util.List;

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
}