package com.datayes.cloud;

import com.datayes.cloud.model.Tenant;
import com.datayes.cloud.model.User;
import com.datayes.cloud.service.OpenstackContextFactory;
import com.mysql.jdbc.Driver;
import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.ldap.core.NameClassPairCallbackHandler;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.security.ldap.SpringSecurityLdapTemplate;

import javax.naming.NameClassPair;
import javax.naming.directory.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;

/**
 * User: changhai
 * Date: 13-8-15
 * Time: 下午4:12
 * DataYes
 */
public class ContextTest {
    @Test
    public void testBeans() throws Exception {
        SessionFactory sessionFactory = getSessionFactory();
        Session session = sessionFactory.openSession();
        Tenant tenant = new Tenant();
        tenant.setDomain("name");
        tenant.setAdmin("admin");
        tenant.setPassword("password");
        session.save(tenant);
        session.flush();
        session.close();
    }

    @Test
    public void testSearchByExample() throws Exception {
        SessionFactory sessionFactory = getSessionFactory();
        Session session = sessionFactory.openSession();
        User example = new User();
        example.setName("ssotest");
        Criteria criteria = session.createCriteria(User.class).add(Example.create(example));
        List<User> list = criteria.list();
        for (User user : list) {
            System.out.println(user);
        }
    }

    private SessionFactory getSessionFactory() throws IOException {
        LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUsername("root");
        dataSource.setPassword("");
        dataSource.setDriverClassName(Driver.class.getName());
        dataSource.setUrl("jdbc:mysql://localhost/haha");
        localSessionFactoryBean.setDataSource(dataSource);
        localSessionFactoryBean.setPackagesToScan("com.datayes.cloud.model");
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.show_sql", "true");
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "update");
        localSessionFactoryBean.setHibernateProperties(hibernateProperties);
        localSessionFactoryBean.afterPropertiesSet();
        return localSessionFactoryBean.getObject();
    }

    @Test
    public void testContext() throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("context/cloud-beans.xml");
        System.out.println(context);
        OpenstackContextFactory factory = context.getBean(OpenstackContextFactory.class);
        System.out.println(factory);
        /*SpringSecurityLdapTemplate ldapTemplate = context.getBean(SpringSecurityLdapTemplate.class);
        List list = ldapTemplate.list("cn=users,dc=datayestest,dc=com");
        for (Object o : list) {
            System.out.println(o);
        }*/
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
