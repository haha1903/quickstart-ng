package com.datayes.cloud.access;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: changhai
 * Date: 13-8-12
 * Time: 下午4:22
 * DataYes
 */
public class Subnet {
    private String id;
    @JsonProperty("network_id")
    private String networkId;
    @JsonProperty("ip_version")
    private String ipVersion = "4";
    private String cidr;
    @JsonProperty("allocation_pools")
    private List<Map<String, String>> allocationPools = new ArrayList<Map<String, String>>();

    public Subnet() {
    }

    public Subnet(String id) {
        this.id = id;
    }

    public Subnet(String networkId, String cidr, String start, String end) {
        this.networkId = networkId;
        this.cidr = cidr;
        HashMap<String, String> allocationPool = new HashMap<String, String>();
        allocationPools.add(allocationPool);
        allocationPool.put("start", start);
        allocationPool.put("end", end);
    }

    public String getNetworkId() {
        return networkId;
    }

    public void setNetworkId(String networkId) {
        this.networkId = networkId;
    }

    public String getIpVersion() {
        return ipVersion;
    }

    public void setIpVersion(String ipVersion) {
        this.ipVersion = ipVersion;
    }

    public String getCidr() {
        return cidr;
    }

    public void setCidr(String cidr) {
        this.cidr = cidr;
    }

    public List<Map<String, String>> getAllocationPools() {
        return allocationPools;
    }

    public void setAllocationPools(List<Map<String, String>> allocationPools) {
        this.allocationPools = allocationPools;
    }

    @Override
    public String toString() {
        return "Subnet{" +
                "networkId='" + networkId + '\'' +
                ", ipVersion='" + ipVersion + '\'' +
                ", cidr='" + cidr + '\'' +
                ", allocationPools=" + allocationPools +
                '}';
    }
}
