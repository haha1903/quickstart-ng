package com.datayes.cloud.dao;

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
public abstract class BaseDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Transactional
    public <T> void save(T t) {
        getSession().save(t);
    }

    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }
}
