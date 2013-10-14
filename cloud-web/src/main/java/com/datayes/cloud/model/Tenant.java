package com.datayes.cloud.model;

import javax.persistence.*;

/**
 * User: changhai
 * Date: 13-8-16
 * Time: 下午3:43
 * DataYes
 */
@Entity
@Table(name = "cloud_tenant")
public class Tenant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(unique = true, updatable = false, nullable = false)
    private String domain;
    @Column(name = "ad_url")
    private String adUrl;
    @Column(name = "ad_user")
    private String adUser;
    @Column(name = "ad_password")
    private String adPassword;
    private transient boolean enabled;

    private transient String password;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAdUrl() {
        return adUrl;
    }

    public void setAdUrl(String adUrl) {
        this.adUrl = adUrl;
    }

    public String getAdUser() {
        return adUser;
    }

    public void setAdUser(String adUser) {
        this.adUser = adUser;
    }

    public String getAdPassword() {
        return adPassword;
    }

    public void setAdPassword(String adPassword) {
        this.adPassword = adPassword;
    }

    @Override
    public String toString() {
        return "Tenant{" +
                "id=" + id +
                ", domain='" + domain + '\'' +
                ", adUrl='" + adUrl + '\'' +
                ", adUser='" + adUser + '\'' +
                ", adPassword='" + adPassword + '\'' +
                ", enabled=" + enabled +
                ", password='" + password + '\'' +
                '}';
    }
}
