package com.datayes.cloud.openstack.access;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.ArrayList;

/**
 * User: changhai
 * Date: 13-8-12
 * Time: 下午3:53
 * DataYes
 */
public class Network {
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    private String id;
    private String name;
    @JsonProperty("admin_state_up")
    private boolean adminStateUp;
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    private Boolean shared;
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    private String status;
    @JsonProperty("tenant_id")
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    private String tenantId;
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    private ArrayList<Subnet> subnets;

    public Network() {
    }

    public Network(String name, boolean adminStateUp) {
        this.name = name;
        this.adminStateUp = adminStateUp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAdminStateUp() {
        return adminStateUp;
    }

    public void setAdminStateUp(boolean adminStateUp) {
        this.adminStateUp = adminStateUp;
    }

    public Boolean getShared() {
        return shared;
    }

    public void setShared(Boolean shared) {
        this.shared = shared;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public ArrayList<Subnet> getSubnets() {
        return subnets;
    }

    public void setSubnets(ArrayList<Subnet> subnets) {
        this.subnets = subnets;
    }

    @Override
    public String toString() {
        return "Network{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", adminStateUp=" + adminStateUp +
                ", shared=" + shared +
                ", status='" + status + '\'' +
                ", tenantId='" + tenantId + '\'' +
                '}';
    }
}
