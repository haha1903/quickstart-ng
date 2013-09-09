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
public class CloudDao extends BaseDao {

    @Transactional
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
}
