package com.datayes.cloud.access;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * User: changhai
 * Date: 13-8-14
 * Time: 下午2:03
 * DataYes
 */
public class Access {
    @JsonProperty("serviceCatalog")
    private List<ServiceCatalog> serviceCatalogs;
    private Metadata metadata;
    private Token token;
    private User user;

    public List<ServiceCatalog> getServiceCatalogs() {
        return serviceCatalogs;
    }

    public void setServiceCatalogs(List<ServiceCatalog> serviceCatalogs) {
        this.serviceCatalogs = serviceCatalogs;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
