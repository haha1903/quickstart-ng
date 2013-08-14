package com.datayes.cloud.access;

import java.util.HashMap;
import java.util.Map;

/**
 * User: changhai
 * Date: 13-8-9
 * Time: 下午4:54
 * DataYes
 */
public class Auth {
    private Map auth;

    public Auth(String username, String password, String tenantName) {
        if (auth == null) auth = new HashMap();
        Map passwordCredentials = new HashMap();
        passwordCredentials.put("username", username);
        passwordCredentials.put("password", password);
        auth.put("passwordCredentials", passwordCredentials);
        auth.put("tenantName", tenantName);
    }

    public Map getAuth() {
        return auth;
    }
}
