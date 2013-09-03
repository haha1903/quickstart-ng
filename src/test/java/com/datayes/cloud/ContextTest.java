package com.datayes.cloud;

import com.datayes.cloud.model.Tenant;
import com.mysql.jdbc.Driver;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.NameClassPairCallbackHandler;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.security.ldap.SpringSecurityLdapTemplate;

import javax.naming.NameClassPair;
import javax.naming.directory.*;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * User: changhai
 * Date: 13-8-15
 * Time: 下午4:12
 * DataYes
 */
public class ContextTest {
    @Test
    public void testBeans() throws Exception {
        LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUsername("root");
        dataSource.setPassword("");
        dataSource.setDriverClassName(Driver.class.getName());
        dataSource.setUrl("jdbc:mysql://localhost/haha");
        localSessionFactoryBean.setDataSource(dataSource);
        localSessionFactoryBean.setPackagesToScan("com.datayes.cloud.model");
        localSessionFactoryBean.afterPropertiesSet();
        SessionFactory sessionFactory = localSessionFactoryBean.getObject();
        Session session = sessionFactory.openSession();
        Tenant tenant = new Tenant();
        tenant.setName("name");
        tenant.setAdmin("admin");
        tenant.setPassword("password");
        session.save(tenant);
        session.flush();
        session.close();
    }

    @Test
    public void testContext() throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("context/cloud-beans.xml");
        System.out.println(context);
        SpringSecurityLdapTemplate ldapTemplate = context.getBean(SpringSecurityLdapTemplate.class);
        List list = ldapTemplate.list("cn=users,dc=datayestest,dc=com");
        for (Object o : list) {
            System.out.println(o);
        }
    }

    @Test
    public void testLdap() throws Exception {
        //设置系统属性中ssl连接的证书和密码
        System.setProperty("javax.net.ssl.trustStore", "/Users/changhai/.keystore");
        System.setProperty("javax.net.ssl.trustStorePassword", "tomcat");
        LdapContextSource contextSource = new LdapContextSource();
        contextSource.setUrl("ldaps://10.20.112.213:636");
        contextSource.setBase("");
        contextSource.setUserDn("cn=administrator,cn=users,DC=datayestest,DC=com");
        contextSource.setPassword("4rfv$RFV");
        contextSource.afterPropertiesSet();
        SpringSecurityLdapTemplate ldapTemplate = new SpringSecurityLdapTemplate(contextSource);
        ldapTemplate.afterPropertiesSet();
        ldapTemplate
                .search("cn=users,DC=datayestest,DC=com", "(objectClass=*)", SearchControls.SUBTREE_SCOPE, true, new NameClassPairCallbackHandler() {
                    @Override
                    public void handleNameClassPair(NameClassPair nameClassPair) {
                        System.out.println(nameClassPair);
                    }
                });
        ldapTemplate.bind("cn=hhh,CN=Users,DC=datayestest,DC=com", null, getAttrs());
        BasicAttribute member = new BasicAttribute("member", "cn=hhh,CN=Users,DC=datayestest,DC=com");
        ModificationItem[] memberOfs = {new ModificationItem(DirContext.ADD_ATTRIBUTE, member)};
        ldapTemplate.modifyAttributes("CN=Administrators,CN=Builtin,DC=datayestest,DC=com", memberOfs);
    }

    private Attributes getAttrs() throws UnsupportedEncodingException {
        Attributes attrs = new BasicAttributes(true);

        // 设置属性
        putAttribute(attrs, "userPrincipalName", "hhh@datayestest.com");
        putAttribute(attrs, "sAMAccountName", "hhh");
        putAttribute(attrs, "mail", "hhh@datayestest.com");
        putAttribute(attrs, "givenName", "hhh");
        putAttribute(attrs, "sn", "hhh");
        putAttribute(attrs, "cn", "hhh");
        putAttribute(attrs, "description", "desc");
        putAttribute(attrs, "company", "datayestest");
        putAttribute(attrs, "department", "datayestest");
        putAttribute(attrs, "displayName", "hhh");

//        putAttribute(attrs, "userPassword", "!@#4QWEr");
//        putAttribute(attrs, "memberOf", "CN=Administrators,CN=Builtin,DC=datayestest,DC=com");
        putAttribute(attrs, "unicodePwd", "\"!@#4QWEr\"".getBytes("UTF-16LE"));
        putAttribute(attrs, "userAccountControl", "544");

        Attribute objclass = new BasicAttribute("objectclass");
        objclass.add("top");
        objclass.add("person");
        objclass.add("organizationalPerson");
        objclass.add("user");
        attrs.put(objclass);
        return attrs;
    }

    private void putAttribute(Attributes attrs, String name, Object value) {
        attrs.put(new BasicAttribute(name, value));
    }
}
