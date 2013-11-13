package com.datayes.cloud.model;

import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;

/**
 * User: changhai
 * Date: 13-9-10
 * Time: 下午3:20
 * DataYes
 */
@Entity
@Table(name = "cloud_tenant_service")
public class TenantService {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tenant_id")
    @ForeignKey(name = "none")
    private Tenant tenant;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "service_id")
    @ForeignKey(name = "none")
    private Service service;
    private boolean enable;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TenantService that = (TenantService) o;

        if (!service.equals(that.service)) {
            return false;
        }
        if (!tenant.equals(that.tenant)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = tenant.hashCode();
        result = 31 * result + service.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "TenantService{" +
                "tenant=" + tenant +
                ", service=" + service +
                ", enable=" + enable +
                '}';
    }
}
