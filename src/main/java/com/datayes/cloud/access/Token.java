package com.datayes.cloud.access;

import java.util.Date;

/**
 * User: changhai
 * Date: 13-8-14
 * Time: 下午2:09
 * DataYes
 */
public class Token {
    private String id;
    private Date expires;
    private Tenant tenant;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getExpires() {
        return expires;
    }

    public void setExpires(Date expires) {
        this.expires = expires;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }
}
