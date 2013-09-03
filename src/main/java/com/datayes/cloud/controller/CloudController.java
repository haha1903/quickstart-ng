package com.datayes.cloud.controller;

import com.datayes.cloud.exception.CloudException;
import com.datayes.cloud.exception.LoginException;
import com.datayes.cloud.model.Tenant;
import com.datayes.cloud.model.User;
import com.datayes.cloud.service.TenantService;
import com.datayes.cloud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
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
        return "index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/service", method = RequestMethod.GET)
    public String service() {
        return "service";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public void login(Map model, @RequestBody User user) throws LoginException {
        if (!userService.check(user))
            throw new LoginException("login failure");
        model.put("message", "success");
    }

    @RequestMapping("/haha")
    public void haha(Map map) {
        map.put("a", "b");
    }

    @RequestMapping(value = "/tenant", method = RequestMethod.POST)
    public void createTenant(Map model, @RequestBody Tenant tenant) {
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
