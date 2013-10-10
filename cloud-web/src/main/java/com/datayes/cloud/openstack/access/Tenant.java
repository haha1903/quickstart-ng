package com.datayes.cloud.openstack.access;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * User: changhai
 * Date: 13-8-9
 * Time: 下午4:30
 * DataYes
 */
public class Tenant {
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    private String id;
    private String name;
    private String description;
    private boolean enabled;

    public Tenant() {
    }

    public Tenant(String name, String description, boolean enabled) {
        this.name = name;
        this.description = description;
        this.enabled = enabled;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public String toString() {
        return "Tenant{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", enabled=" + enabled +
                '}';
    }
}
