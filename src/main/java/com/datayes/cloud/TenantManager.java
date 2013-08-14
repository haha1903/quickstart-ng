package com.datayes.cloud;

import java.io.IOException;
import java.util.List;

/**
 * User: changhai
 * Date: 13-8-9
 * Time: 下午4:13
 */
public class TenantManager {
    private final OpenstackContext openstackContext;

    public TenantManager(OpenstackContext openstackContext) {
        this.openstackContext = openstackContext;
    }

    public Tenant createTenant(String name, String description, boolean enabled) throws IOException {
        Tenant tenant = new Tenant(name, description, enabled);
        Tenant result = openstackContext.post("http://10.20.112.226:35357/v2.0/tenants", "tenant", tenant, "tenant", Tenant.class);
        User user = openstackContext.getUser();
        Role role = openstackContext.getRole("admin");
        openstackContext.put("http://10.20.112.226:35357/v2.0/tenants/" + result.getId() + "/users/" + user.getId() + "/roles/OS-KSADM/" + role.getId());
        return result;
    }

    public List<Tenant> listTenants() throws IOException {
        return openstackContext.listTenants();
    }

    public void deleteTenant(String name) throws IOException {
        openstackContext.deleteTenant(name);
    }
}
