package com.datayes.cloud.access;

/**
 * User: changhai
 * Date: 13-8-14
 * Time: 下午2:01
 * DataYes
 */
public class Endpoint {
    private String id;
    private String reigon;
    private String internalURL;
    private String adminURL;
    private String publicURL;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReigon() {
        return reigon;
    }

    public void setReigon(String reigon) {
        this.reigon = reigon;
    }

    public String getInternalURL() {
        return internalURL;
    }

    public void setInternalURL(String internalURL) {
        this.internalURL = internalURL;
    }

    public String getAdminURL() {
        return adminURL;
    }

    public void setAdminURL(String adminURL) {
        this.adminURL = adminURL;
    }

    public String getPublicURL() {
        return publicURL;
    }

    public void setPublicURL(String publicURL) {
        this.publicURL = publicURL;
    }
}
