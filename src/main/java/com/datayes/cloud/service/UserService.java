package com.datayes.cloud.service;

import com.datayes.cloud.dao.CloudDao;
import com.datayes.cloud.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User: changhai
 * Date: 13-9-2
 * Time: 下午12:42
 * DataYes
 */
@Service
public class UserService {
    @Autowired
    private CloudDao cloudDao;

    public boolean check(User user) {
        return cloudDao.checkUser(user);
    }
}
