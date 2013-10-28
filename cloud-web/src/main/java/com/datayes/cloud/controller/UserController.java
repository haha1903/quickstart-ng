package com.datayes.cloud.controller;

import com.datayes.cloud.dao.CloudDao;
import com.datayes.cloud.model.Tenant;
import com.datayes.cloud.model.User;
import com.datayes.cloud.service.TenantService;
import com.datayes.cloud.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by changhai on 13-10-10.
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private CloudDao cloudDao;
    @Autowired
    private UserService userService;
    
    private User getCurrentUser(){
        //TODO: currently hard code tenantId here
        User user = new User();
        //u.setTenantId(10);
        return user;
    }
    
    private Tenant getCurrentTenant() {
        //TODO: currently hard code tenantId here
        //long tenantId = getCurrentUser().getTenantId();
        //return cloudDao.get(Tenant, tenantId);
        Tenant tenant = new Tenant();
        tenant.setId(10);
        tenant.setAdUrl("10.20.112.213");
        tenant.setAdUser("isAdacount");
        tenant.setCompany("datayestest");
        tenant.setDomain("datayestest.com");
        tenant.setInitPassword("datayes@123");
        return tenant;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<User> list() {
        return userService.getUsers(getCurrentTenant());//cloudDao.findAll(User.class);
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public void create(@RequestBody User user) {
        //cloudDao.save(user);
        userService.createUser(getCurrentTenant(),user);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public User get(@PathVariable long id) {
        return cloudDao.get(User.class, id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable long id) {
        cloudDao.delete(User.class, id);
    }
    @RequestMapping(method = RequestMethod.PUT)
    public void update(User user) {
        cloudDao.update(user);
    }
}
