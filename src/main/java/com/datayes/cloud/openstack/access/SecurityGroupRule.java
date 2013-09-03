package com.datayes.cloud.openstack.access;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created with IntelliJ IDEA.
 * User: user
 * Date: 9/3/13
 * Time: 11:15 AM
 * To change this template use File | Settings | File Templates.
 */
public class SecurityGroupRule {
    private int id;
    @JsonProperty("from_port")
    private String fromPort;
    @JsonProperty("to_port")
    private String toPort;
    @JsonProperty("ip_protocol")
    private String ipProtocol;
    @JsonProperty("parent_group_id")
    private int parentGroupId;
    private String cidr;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFromPort() {
        return fromPort;
    }

    public void setFromPort(String fromPort) {
        this.fromPort = fromPort;
    }

    public String getToPort() {
        return toPort;
    }

    public void setToPort(String toPort) {
        this.toPort = toPort;
    }

    public String getIpProtocol() {
        return ipProtocol;
    }

    public void setIpProtocol(String ipProtocol) {
        this.ipProtocol = ipProtocol;
    }

    public int getParentGroupId() {
        return parentGroupId;
    }

    public void setParentGroupId(int parentGroupId) {
        this.parentGroupId = parentGroupId;
    }

    public String getCidr() {
        return cidr;
    }

    public void setCidr(String cidr) {
        this.cidr = cidr;
    }
}
