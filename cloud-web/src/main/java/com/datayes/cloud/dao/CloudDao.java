package com.datayes.cloud.dao;

import com.datayes.cloud.model.User;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: changhai
 * Date: 13-8-16
 * Time: 下午3:39
 * DataYes
 */
@Repository
@Transactional
public class CloudDao extends BaseDao {

    public boolean checkUser(User user) {
        Query query = getSession().createQuery("from User u where u.name = :name and u.password = :password").setString("name", user.getName())
                .setString("password", user
                        .getPassword());
        return query.list().size() > 0;
    }

    public <T> List<T> findAll(Class<T> type) {
        return getSession().createCriteria(type).list();
    }

    public <T> List<T> get(T example) {
        return getSession().createCriteria(example.getClass()).add(Example.create(example)).list();
    }

    public <T> void update(T type) {
        getSession().update(type);
    }

    public <T> T get(Class<T> type, long id) {
        return (T) getSession().get(type, id);
    }

    public <T> void delete(Class<T> type, long id) {
        getSession().delete(get(type, id));
    }

    public String getAdUrl(long tenantId) {
        // TODO Auto-generated method stub
        return "";
    }
}
