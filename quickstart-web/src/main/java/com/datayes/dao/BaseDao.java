package com.datayes.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

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
