package com.datayes.cloud.dao;

import java.io.Serializable;

import com.datayes.cloud.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by changhai on 13-8-16.
 * DataYes
 */
@Repository
@Transactional
public abstract class BaseDao {
    @Autowired
    private SessionFactory sessionFactory;

    public <T> Serializable save(T t) {
        return getSession().save(t);
    }

    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }
}
