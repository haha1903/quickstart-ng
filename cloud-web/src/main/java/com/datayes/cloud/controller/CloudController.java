package com.datayes.cloud.controller;

import com.datayes.cloud.exception.CloudException;
import com.datayes.cloud.exception.LoginException;
import com.datayes.cloud.model.*;
import com.datayes.cloud.openstack.access.Server;
import com.datayes.cloud.service.OpenstackContextFactory;
import com.datayes.cloud.service.TenantService;
import com.datayes.cloud.service.UserService;
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
        List<CloudService> services = userService.getServices();
        model.put("services", services);
        return "users";
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public void addUser(@RequestBody User user) {
        User currentUser = getCurrentUser();
        user.setTenant(currentUser.getTenant());
        userService.createUser(user);
    }

    private User getCurrentUser() {
        User exampl = new User();
        exampl.setName(SsoContext.getUser().getName());
        return userService.getUser(exampl);
    }

    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    public void addUserService(long id, @RequestBody CloudService service) throws CloudException {
        User user = userService.getUser(id);
        List<CloudService> cloudServices = userService.getService(service);
        if (cloudServices.isEmpty())
            throw new CloudException("service not found: " + service);
        CloudService newService = cloudServices.get(0);
        if (service.isEnabled()) {
            user.addService(newService);
        } else {
            user.removeService(newService);
        }
        userService.update(user);
    }

    @RequestMapping(value = "/userinfo", method = RequestMethod.GET)
    public String userinfo(long id, Map model) {
        User user = userService.getUser(id);
        model.put("user", user);
        List<CloudService> enabledServices = user.getServices();
        List<CloudService> services = userService.getServices();
        for (CloudService service : services) {
            if (enabledServices.contains(service))
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
    public void service(@RequestBody CloudService service) {
        userService.addService(service);
    }

    @RequestMapping(value = "/server", method = RequestMethod.POST)
    public void server(@RequestBody CloudServer server) {
        User user = getCurrentUser();
        server.setTenant(user.getTenant());
        userService.addServer(server);
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

    @RequestMapping(value = "/monitor", method = RequestMethod.GET)
    public String monitor(Map model) throws IOException {
        User user = getCurrentUser();
        List<Server> servers = userService.getServers("datayes_staging");
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
}
