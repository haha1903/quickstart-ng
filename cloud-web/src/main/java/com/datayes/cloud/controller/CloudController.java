package com.datayes.cloud.controller;

import com.datayes.cloud.exception.CloudException;
import com.datayes.cloud.exception.LoginException;
import com.datayes.cloud.model.Server;
import com.datayes.cloud.model.Service;
import com.datayes.cloud.model.Tenant;
import com.datayes.cloud.model.User;
import com.datayes.cloud.service.TenantService;
import com.datayes.cloud.service.UserService;
import com.datayes.cloud.util.ContextUtil;
import com.datayes.paas.sso.SsoContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: changhai
 * Date: 13-8-15
 * Time: 下午6:19
 * DataYes
 */
@Controller
public class CloudController {
    @Autowired
    private TenantService tenantService;
    @Autowired
    private UserService userService;
    
   
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home() {
        return "redirect:index";
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String signup() {
        return "register";
    }
    
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String admin() {
        return "redirect:index?admin";
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String users(Map model) {
        List<User> users = userService.getAll();
        model.put("users", users);
        List<Service> services = userService.getServices();
        model.put("services", services);
        return "users";
    }
    
    @RequestMapping(value = "/resource", method = RequestMethod.GET)
    @ResponseBody
    public List<com.datayes.cloud.openstack.access.Server> getResource() throws IOException {
        return tenantService.getServers(getCurrentTenant());
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.GET)
    public String addUser() {
        return "addUser";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/service", method = RequestMethod.GET)
    public String service(Map model) {
        model.put("services", userService.getServices());
        return "service";
    }

    @RequestMapping(value = "/addService", method = RequestMethod.GET)
    public String addService() {
        return "addService";
    }

    @RequestMapping(value = "/addServer", method = RequestMethod.GET)
    public String addServer() {
        return "addServer";
    }

    @RequestMapping(value = "/service", method = RequestMethod.POST)
    public void service(@RequestBody Service service) {
        userService.addService(service);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public void login(Map model, @RequestBody User user) throws LoginException {
        if (!userService.check(user))
            throw new LoginException("login failure");
        model.put("message", "success");
    }

    @RequestMapping(value = "/monitor", method = RequestMethod.GET)
    public String monitor(Map model) throws IOException {
        User user = getCurrentUser();
        Tenant tenant = getCurrentTenant();
        List<com.datayes.cloud.openstack.access.Server> servers = tenantService.getServers(tenant);
        model.put("servers", servers);
        return "monitor";
    }

    @ExceptionHandler(CloudException.class)
    @ResponseBody
    public Map exp(HttpServletRequest request, HttpServletResponse response, CloudException e) {
        response.setStatus(500);
        Map model = new HashMap();
        model.put("message", e.getMessage());
        return model;
    }
    
    public User getCurrentUser(){
        //TODO: currently hard code tenantId here
        User user = new User();
        //u.setTenantId(10);
        return user;
    }
    
    public Tenant getCurrentTenant() {
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
}
