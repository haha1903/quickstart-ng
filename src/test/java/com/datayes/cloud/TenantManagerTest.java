package com.datayes.cloud;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * User: changhai
 * Date: 13-8-9
 * Time: 下午4:14
 */
public class TenantManagerTest {
    @Test
    public void testCreateTenant() throws Exception {
        OpenstackContext openstackContext = new OpenstackContext("admin", "aaaaaa", "demo");
        openstackContext.setIdentityServiceUrl("http://10.20.112.226:5000/v2.0");
        TenantManager tenantManager = new TenantManager(openstackContext);
        tenantManager.deleteTenant("tenant1");
        List<Tenant> tenants = tenantManager.listTenants();
        int oldSize = tenants.size();
        Tenant tenant = tenantManager.createTenant("tenant1", "tenant1 desc", true);
        assertEquals("tenant1", tenant.getName());
        tenants = tenantManager.listTenants();
        assertEquals(oldSize + 1, tenants.size());
    }
}
