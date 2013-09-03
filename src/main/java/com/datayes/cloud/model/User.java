package com.datayes.cloud.model;

import javax.persistence.*;

/**
 * User: changhai
 * Date: 13-8-16
 * Time: 下午3:43
 * DataYes
 */
@Entity
@Table(name = "cloud_user", uniqueConstraints = {@UniqueConstraint(name = "uk_name_tenant_id", columnNames = {"name", "tenant_id"})})

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column
    private String name;
    @Column
    private String password;
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", tenant=" + tenant +
                '}';
    }

}
