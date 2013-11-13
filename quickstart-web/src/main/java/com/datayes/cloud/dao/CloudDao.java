package com.datayes.cloud.dao;

import com.datayes.cloud.model.Tenant;
import com.datayes.cloud.model.User;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

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
    
    
    
    public boolean checkDomain(String domain){
        return getSession().createCriteria(Tenant.class).add(Restrictions.eq("domain", domain)).list().size()==0;
    }
    
    public User findUserByName(String name, long tenantId){
        List<User> result = getSession().createCriteria(User.class)
                .add(Restrictions.eq("name", name))
                .add(Restrictions.eq("tenantId", tenantId)).list();
        if (result.size()>0) return result.get(0);
        return null;
    }
    
    public <T> List<T> findByConditions(Class<T> type, HashMap<String, Object> conditions){
        return getSession().createCriteria(type).add(Restrictions.allEq(conditions)).list();
        
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
