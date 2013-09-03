package com.datayes.cloud.openstack.access;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created with IntelliJ IDEA.
 * User: user
 * Date: 9/3/13
 * Time: 9:56 AM
 * To change this template use File | Settings | File Templates.
 */
public class ServerAddress {
    private int version;
    private String addr;
    @JsonProperty("OS-EXT-IPS:type")
    private String type;

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
