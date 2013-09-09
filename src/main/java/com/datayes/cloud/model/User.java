package com.datayes.cloud.model;

import javax.persistence.*;
import java.util.List;

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
    private transient String password;
    @Column
    private String dept;
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "cloud_user_service", joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = {@JoinColumn(name = "service_id")})
    private List<CloudService> services;

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

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public List<CloudService> getServices() {
        return services;
    }

    public void setServices(List<CloudService> services) {
        this.services = services;
    }
    public void addService(CloudService service) {
        services.add(service);
    }

    public void removeService(CloudService service) {
        services.remove(service);
    }
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", dept='" + dept + '\'' +
                ", tenant=" + tenant +
                ", services=" + services +
                '}';
    }

}
