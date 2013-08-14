package com.datayes.cloud;

import com.datayes.cloud.access.*;
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
        OpenstackContext openstackContext = new OpenstackContext("http://10.20.112.226:5000/v2.0", "http://10.20.112.226:35357/v2.0", "admin", "aaaaaa", "demo");
        TenantManager tenantManager = new TenantManager(openstackContext);
        tenantManager.deleteTenant("tenant1");
        List<Tenant> tenants = tenantManager.listTenants();
        int oldSize = tenants.size();
        Tenant tenant = tenantManager.createTenant("tenant1", "tenant1 desc", true);
        assertEquals("tenant1", tenant.getName());
        tenants = tenantManager.listTenants();
        assertEquals(oldSize + 1, tenants.size());
    }

    @Test
    public void testCreateNetwork() throws Exception {
        String identityServiceUrl = "http://10.20.112.226:5000/v2.0";
        String identityAdminUrl = "http://10.20.112.226:35357/v2.0";
        OpenstackContext openstackContext = new OpenstackContext(identityServiceUrl, identityAdminUrl, "admin", "aaaaaa", "demo");
        TenantManager tenantManager = new TenantManager(openstackContext);
        tenantManager.deleteTenant("tenant1");
        Tenant tenant = tenantManager.createTenant("tenant1", "tenant1 desc", true);
        System.out.println(tenant.getId());
        OpenstackContext tenant1Context = new OpenstackContext(identityServiceUrl, identityAdminUrl, "admin", "aaaaaa", "tenant1");
        NetworkManager networkManager = new NetworkManager(tenant1Context);
        Network network = networkManager.createNetwork("network1", false);
        Subnet subnet = networkManager.createSubnet(network);
        System.out.println(subnet);
//        networkManager.createNetwork("network2", false);
        ComputeManager computeManager = new ComputeManager(tenant1Context);
        Server server = computeManager.createServer(new Server("server1"));
        StorageManager storageManager = new StorageManager(tenant1Context);
        Volume volume = storageManager.createVolume(new Volume("v1", 1));
        computeManager.attach(server.getId(), volume.getId());
        System.out.println(volume);
    }
}
