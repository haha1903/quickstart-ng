package com.datayes.cloud.service;

import com.datayes.cloud.dao.CloudDao;
import com.datayes.cloud.model.Tenant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public void create(Tenant tenant) {
        cloudDao.save(tenant);
    }
}
