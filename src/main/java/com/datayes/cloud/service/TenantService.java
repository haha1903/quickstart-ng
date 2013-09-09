package com.datayes.cloud.service;

import com.datayes.cloud.dao.CloudDao;
import com.datayes.cloud.model.Tenant;
import com.datayes.cloud.model.User;
import com.datayes.cloud.openstack.CloudManager;
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
        cloudDao.save(tenant);
        User admin = new User();
        admin.setName(tenant.getAdmin());
        admin.setPassword(tenant.getPassword());
        admin.setTenant(tenant);
        cloudManager.createTenant(tenant.getName(), tenant.getName());
        cloudManager.createZimbra(tenant.getName());
        userService.createUser(admin);
    }
}
