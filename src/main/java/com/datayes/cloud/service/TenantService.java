package com.datayes.cloud.service;

import com.datayes.cloud.dao.CloudDao;
import com.datayes.cloud.model.Tenant;
import com.datayes.cloud.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

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

    @Transactional
    public void create(Tenant tenant) {
        cloudDao.save(tenant);
        User admin = new User();
        admin.setName(tenant.getAdmin());
        admin.setPassword(tenant.getPassword());
        admin.setTenant(tenant);
        userService.createUser(admin);
    }
}
