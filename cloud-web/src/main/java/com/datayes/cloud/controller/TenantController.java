package com.datayes.cloud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.datayes.cloud.dao.CloudDao;
import com.datayes.cloud.model.Tenant;
import com.datayes.cloud.model.User;
import com.datayes.cloud.service.TenantService;

/**
 * Created by hongsi on 13-10-18.
 */
@Controller
@RequestMapping("/tenant")
public class TenantController {
    @Autowired
    private CloudDao cloudDao;
    
    @Autowired
    private TenantService tenantService;
    
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<Tenant> list() {
        return cloudDao.findAll(Tenant.class);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Tenant get(@PathVariable long id) {
        return cloudDao.get(Tenant.class,id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable long id) throws Exception{
        tenantService.delete(id);
    }
    
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Tenant create(@RequestBody Tenant tenant) throws Exception{
        tenantService.create(tenant);
        return tenant;
    }
    
    
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable long id, @RequestBody Tenant tenant) throws Exception {
        tenantService.update(tenant);
    }
    
    @RequestMapping(method = RequestMethod.PUT)
    public void update(@RequestBody Tenant tenant) throws Exception {
        tenantService.update(tenant);
    }
    
    @RequestMapping(value="/checkDomain", method = RequestMethod.POST)
    @ResponseBody
    public boolean checkDomain(String domain){
        return cloudDao.checkDomain(domain);
    }
}
