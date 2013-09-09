package com.datayes.cloud.controller;

import com.datayes.cloud.exception.CloudException;
import com.datayes.cloud.exception.LoginException;
import com.datayes.cloud.model.CloudService;
import com.datayes.cloud.model.Tenant;
import com.datayes.cloud.model.User;
import com.datayes.cloud.service.TenantService;
import com.datayes.cloud.service.UserService;
import com.datayes.paas.sso.SsoContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        return "service";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signup() {
        return "signup";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public void signup(@RequestBody Tenant tenant) throws Exception {
        tenantService.create(tenant);
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String users(Map model) {
        List<User> users = userService.getAll();
        model.put("users", users);
        return "users";
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public void addUser(@RequestBody User user) {
        User exampl = new User();
        exampl.setName(SsoContext.getUser().getName());
        User currentUser = userService.getUser(exampl);
        user.setTenant(currentUser.getTenant());
        userService.createUser(user);
    }
    @RequestMapping(value="/userinfo", method =  RequestMethod.GET)
    public String userinfo(long id,Map model) {
        User example = new User();
        example.setId(id);
        User user = userService.getUser(example);
        model.put("user", user);
        List<CloudService> enabledServices = user.getServices();
        List<CloudService> services = userService.getServices();
        for (CloudService service : services) {
            if(enabledServices.contains(service))
                service.setEnabled(true);
        }
        model.put("services", services);
        return "userinfo";
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
    public String service() {
        return "service";
    }

    @RequestMapping(value = "/addService", method = RequestMethod.GET)
    public String addService() {
        return "addService";
    }

    @RequestMapping(value = "/service", method = RequestMethod.POST)
    public void service(CloudService service) {
        userService.addService(service);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public void login(Map model, @RequestBody User user) throws LoginException {
        if (!userService.check(user))
            throw new LoginException("login failure");
        model.put("message", "success");
    }


    @RequestMapping(value = "/tenant", method = RequestMethod.POST)
    public void createTenant(Map model, @RequestBody Tenant tenant) throws Exception {
        tenantService.create(tenant);
    }

    @ExceptionHandler(CloudException.class)
    @ResponseBody
    public Map exp(HttpServletRequest request, HttpServletResponse response, CloudException e) {
        response.setStatus(500);
        Map model = new HashMap();
        model.put("message", e.getMessage());
        return model;
    }
}
