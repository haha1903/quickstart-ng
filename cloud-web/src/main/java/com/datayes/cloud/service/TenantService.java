package com.datayes.cloud.service;

import java.io.Serializable;

import com.datayes.cloud.dao.CloudDao;
import com.datayes.cloud.model.Tenant;
import com.datayes.cloud.model.User;
import com.datayes.cloud.openstack.CloudManager;
import com.datayes.cloud.util.CommonUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * User: changhai
 * Date: 13-8-20
 * Time: 下午2:12
 * DataYes
 */
@Service
public class TenantService {
    @Autowired
    private CloudDao cloudDao;
    @Autowired
    private UserService userService;
    @Autowired
    private CloudManager cloudManager;

    @Transactional(rollbackFor = Exception.class)
    public void create(Tenant tenant) throws Exception {
        
        String pwd = CommonUtil.generatePassword(12);
        tenant.setInitPassword(pwd);
        pwd = CommonUtil.generatePassword(12);
        tenant.setAdminPassword(pwd);
        Serializable s = cloudDao.save(tenant);
        System.out.println(s);
        
        cloudManager.createTenant(tenant.getDomain(), tenant.getDomain());
        String url = cloudManager.createAD(tenant.getDomain());
        cloudManager.createZimbra(tenant.getDomain());
        
        tenant.setAdUrl(url);
        cloudDao.update(tenant);
                
        User userAdmin = new User();
        userAdmin.setPassword(tenant.getInitPassword());
        userAdmin.setName(tenant.getAdUser());
        userAdmin.setTenantId(tenant.getId());
        userService.createUser(tenant,userAdmin);
        //Manually add ad admin
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(long id) throws Exception {
        Tenant tenant = cloudDao.get(Tenant.class, id);
        cloudManager.deleteTenant(tenant.getDomain());
        cloudDao.delete(Tenant.class,id);                
    }
    
    @Transactional(rollbackFor = Exception.class)
    public void update(Tenant tenant) throws Exception {
        cloudDao.update(tenant);
    }

    @Transactional(rollbackFor = Exception.class)
    public void save(Tenant tenant) throws Exception {
        cloudDao.save(tenant);
        
    }

            
}
