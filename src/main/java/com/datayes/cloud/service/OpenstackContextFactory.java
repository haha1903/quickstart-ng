package com.datayes.cloud.service;

import com.datayes.cloud.openstack.OpenstackContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * User: changhai
 * Date: 13-9-8
 * Time: 下午5:48
 * DataYes
 */
@Service
public class OpenstackContextFactory {
    @Value("${openstack.identityServerUrl}")
    private String identityServiceUrl;
    @Value("${openstack.password}")
    private String password;
    @Value("${openstack.username}")
    private String username;

    public OpenstackContext createContext(String tenantName) throws IOException {
        return new OpenstackContext(identityServiceUrl, username, password, tenantName);
    }

    public String getIdentityServiceUrl() {
        return identityServiceUrl;
    }

    public void setIdentityServiceUrl(String identityServiceUrl) {
        this.identityServiceUrl = identityServiceUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "OpenstackContextFactory{" +
                "identityServiceUrl='" + identityServiceUrl + '\'' +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
