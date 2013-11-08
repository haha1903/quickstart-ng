package com.datayes.cloud.controller;

import com.datayes.cloud.dao.CloudDao;
import com.datayes.cloud.model.Tenant;
import com.datayes.cloud.model.User;
import com.datayes.cloud.service.TenantService;
import com.datayes.cloud.service.UserService;
import com.datayes.cloud.util.CommonUtil;
import com.datayes.cloud.util.ContextUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by changhai on 13-10-10.
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private CloudController cloudController;
    
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<User> list() {
        return userService.getUsers(cloudController.getCurrentTenant());//cloudDao.findAll(User.class);
    }
    
    @RequestMapping(value = "/dept",method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Integer> getDept() {
        List<User> list = userService.getUsers(cloudController.getCurrentTenant());//cloudDao.findAll(User.class);
        Map<String, Integer> map= new LinkedHashMap<String, Integer>();
        int other = 0;
        for(User user:list){
            if (user.getDept()!=null && !user.getDept().isEmpty())
            {
                CommonUtil.countMap(map, user.getDept());
            }
            else {
                other++;
            }
        }
        if (other>0) map.put("其它", other);
        //map.put("技术部", 7);
        //map.put("投资部", 5);
        
        return map;
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public void create(@RequestBody User user) {
        userService.createUser(cloudController.getCurrentTenant(),user);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public User get(@PathVariable long id) {
        return userService.getUser(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable long id) {
        userService.delete(id);
    }
    
    @RequestMapping(method = RequestMethod.PUT)
    public void update(User user) {
        userService.updateUser(cloudController.getCurrentTenant(),user);
    }
}
