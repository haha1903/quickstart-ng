package com.datayes.cloud.openstack.access;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: user
 * Date: 9/3/13
 * Time: 11:08 AM
 * To change this template use File | Settings | File Templates.
 */
public class SecurityGroup {
    private int id;
    private String name;
    private String description;
    @JsonProperty("tenant_id")
    private String tenantId;
    private List<SecurityGroupRule> rules;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public List<SecurityGroupRule> getRules() {
        return rules;
    }

    public void setRules(List<SecurityGroupRule> rules) {
        this.rules = rules;
    }
}
