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
    @Column(name = "ad_user")
    private String adUser;
    @Column(name = "ad_url")
    private String adUrl;
    @Column(name = "init_password", nullable = false)
    private String initPassword;
    @Column(name = "admin_password", nullable = false)
    private String adminPassword;
    @Column(nullable = false)
    private String company;
    @Column(nullable = false)
    private String email;
    @Column
    private String phone;
    @Column
    private String status;
    
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

    public String getAdUser() {
        return adUser;
    }

    public void setAdUser(String adUser) {
        this.adUser = adUser;
    }

    public String getInitPassword() {
        return initPassword;
    }

    public void setInitPassword(String initPassword) {
        this.initPassword = initPassword;
    }
    
    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }
    
    public String getAdUrl() {
        return adUrl;
    }

    public void setAdUrl(String adUrl) {
        this.adUrl = adUrl;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status){
        this.status = status;
    }


    @Override
    public String toString() {
        return "Tenant{" +
                "id=" + id +
                ", domain='" + domain + '\'' +
                ", adUser='" + adUser + '\'' +
                ", initPassword='" + initPassword + '\'' +
                ", adUrl='" + adUrl + '\'' +
                ", company='" + company + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", status=" + status +                
                '}';
    }
    
    /*public enum TenantStatus{
        Pending("PENDING"),Enabled("ENABLED"),Disabled("DISABLED"),Deleted("DELETED");
        
        private String status;
        
        TenantStatus(String status){
            this.status = status;
        }
        
        @Override
        public String toString() {
            return status;
        }
    }*/
}
