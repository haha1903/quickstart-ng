package com.datayes.cloud.openstack.access;

/**
 * Created with IntelliJ IDEA.
 * User: user
 * Date: 9/2/13
 * Time: 9:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class Floating_ip {
    //RESP BODY: {"floating_ip": {"instance_id": null, "ip": "10.20.112.84", "fixed_ip": null, "id": 4, "pool": "nova"}}
    private String instance_id;
    private String ip;
    private String fixed_ip;
    private String id;
    private String pool;


    public String getInstance_id() {
        return instance_id;
    }

    public void setInstance_id(String instance_id) {
        this.instance_id = instance_id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getFixed_ip() {
        return fixed_ip;
    }

    public void setFixed_ip(String fixed_ip) {
        this.fixed_ip = fixed_ip;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPool() {
        return pool;
    }

    public void setPool(String pool) {
        this.pool = pool;
    }



}
