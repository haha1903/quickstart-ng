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
    private String tenantName;
    private Map<String, String> passwordCredentials = new HashMap<String, String>();

    public Auth(String username, String password, String tenantName) {
        passwordCredentials.put("username", username);
        passwordCredentials.put("password", password);
        this.tenantName = tenantName;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public Map<String, String> getPasswordCredentials() {
        return passwordCredentials;
    }

    public void setPasswordCredentials(Map<String, String> passwordCredentials) {
        this.passwordCredentials = passwordCredentials;
    }
}
