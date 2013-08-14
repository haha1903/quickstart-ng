package com.datayes.cloud.access;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * User: changhai
 * Date: 13-8-14
 * Time: 下午2:04
 * DataYes
 */
public class Metadata {
    private List<String> roles;
    @JsonProperty("is_admin")
    private int isAdmin;

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public int getAdmin() {
        return isAdmin;
    }

    public void setAdmin(int admin) {
        isAdmin = admin;
    }
}
