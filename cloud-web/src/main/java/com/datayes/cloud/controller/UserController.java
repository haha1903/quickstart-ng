package com.datayes.cloud.controller;

import com.datayes.cloud.dao.CloudDao;
import com.datayes.cloud.model.User;
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

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<User> list() {
        return cloudDao.findAll(User.class);
    }
    @RequestMapping(method = RequestMethod.POST)
    public void create(@RequestBody User user) {
        cloudDao.save(user);
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
