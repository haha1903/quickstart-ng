package com.datayes.cloud;

import com.mysql.jdbc.Driver;
import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;

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
        com.datayes.cloud.model.Test test = new com.datayes.cloud.model.Test();
        test.setId(123);
        test.setName("444");
        session.save(test);
        session.flush();
        session.close();
    }
}
