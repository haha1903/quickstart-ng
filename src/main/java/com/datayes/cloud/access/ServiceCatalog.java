package com.datayes.cloud.access;

import java.util.List;

/**
 * User: changhai
 * Date: 13-8-14
 * Time: 下午2:00
 * DataYes
 */
public class ServiceCatalog {
    private List<Endpoint> endpoints;
    private String type;
    private String name;

    public List<Endpoint> getEndpoints() {
        return endpoints;
    }

    public void setEndpoints(List<Endpoint> endpoints) {
        this.endpoints = endpoints;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
