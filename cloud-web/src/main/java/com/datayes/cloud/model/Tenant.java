package com.datayes.cloud.model;

import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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
    private String name;
    @Column(nullable = false)
    private String admin;
    @Column
    private String address;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
                ", name='" + name + '\'' +
                ", admin='" + admin + '\'' +
                ", address='" + address + '\'' +
                ", adUser='" + adUser + '\'' +
                ", adPassword='" + adPassword + '\'' +
                ", enabled=" + enabled +
                ", password='" + password + '\'' +
                '}';
    }
}
