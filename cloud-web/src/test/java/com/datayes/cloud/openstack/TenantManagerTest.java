package com.datayes.cloud.openstack;

import com.datayes.cloud.openstack.access.*;
import com.datayes.cloud.service.OpenstackContextFactory;
import com.datayes.cloud.util.ServerInitUtil;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.SimpleType;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * User: changhai
 * Date: 13-8-9
 * Time: 下午4:14
 */
public class TenantManagerTest {

    private static final Logger log = LoggerFactory.getLogger(TenantManagerTest.class);
    private String identityServiceUrl = "http://10.20.112.65:5000/v2.0/tokens";
    private OpenstackContext openstackContext;

    @Before
    public void setUp() throws Exception {
//        openstackContext = new OpenstackContext(identityServiceUrl, "admin", "admin", "admin");
    }

    @Test
    public void testGetUsage() throws Exception {
        OpenstackContextFactory factory = new OpenstackContextFactory();
        factory.setIdentityServiceUrl("http://10.22.201.201:5000/v2.0/tokens");
        factory.setUsername("admin");
        factory.setPassword("admin");
        OpenstackContext ctx = factory.createContext("datayes_staging");
        List<Server> servers = ctx
                .get(ctx.getComputeUrl() + "/servers/detail", "servers", CollectionType.construct(List.class, SimpleType.construct(Server.class)));
        List<Volume> volumes = ctx
                .get(ctx.getVolumeUrl() + "/volumes/detail", "volumes", CollectionType.construct(List.class, SimpleType.construct(Volume.class)));
        for (Server server : servers) {
            Flavor flavor = ctx.get(ctx.getComputeUrl() + "/flavors/" + server.getFlavor().getId(), "flavor", Flavor.class);
            Volume volume = getVolume(server, volumes);
            server.setVcpu(flavor.getVcpus());
            server.setRam(flavor.getRam());
            server.setDisk(flavor.getDisk() + (volume == null ? 0 : volume.getSize()));
        }


    }

    private Volume getVolume(Server server, List<Volume> volumes) {
        for (Volume volume : volumes) {
            List<VolumeAttachment> attachments = volume.getAttachments();
            for (VolumeAttachment attachment : attachments) {
                if (server.getId().equals(attachment.getServerId()))
                    return volume;
            }
        }
        return null;
    }

    @Test
    public void testListTenant() throws Exception {
        TenantManager tenantManager = new TenantManager(openstackContext);
        List<Tenant> tenants = tenantManager.listTenants();
        for (Tenant tenant : tenants) {
            log.debug("tenant = {}", tenant);
        }
    }

    @Test
    public void testCreateTenant() throws Exception {
        TenantManager tenantManager = new TenantManager(openstackContext);
        List<Tenant> tenants = tenantManager.listTenants();
        boolean exists = false;
        for (Tenant t : tenants) {
            if (t.getName().equals("tenant1")) {
                tenantManager.deleteTenant("tenant1");
                exists = true;
                break;
            }
        }
        int oldSize = tenants.size() - (exists ? 1 : 0);
        Tenant tenant = tenantManager.createTenant("tenant1", "tenant1 desc", true);
        assertEquals("tenant1", tenant.getName());
        tenants = tenantManager.listTenants();
        assertEquals(oldSize + 1, tenants.size());
    }

    @Test
    public void testCreateNetwork() throws Exception {
        TenantManager tenantManager = new TenantManager(openstackContext);
        tenantManager.deleteTenant("tenant1");
        Tenant tenant = tenantManager.createTenant("tenant1", "tenant1 desc", true);
        System.out.println(tenant.getId());
        OpenstackContext tenant1Context = new OpenstackContext(identityServiceUrl, "admin", "admin", "tenant1");
        ComputeManager computeManager = new ComputeManager(tenant1Context);
        StorageManager storageManager = new StorageManager(tenant1Context);
        Volume volume = storageManager.createVolume(new Volume("v1", 1));
        Server server = new Server("server1");
        Server result = computeManager
                .createServer(server, volume.getId(), ServerInitUtil.ServerFlavor.tiny, "TestVM", ServerInitUtil.ServerType.ZIMBRA_SERVER);
        System.out.println(volume);
        System.out.println(result);
    }

    @Test
    public void testDeleteTenant() throws Exception {
        TenantManager tenantManager = new TenantManager(openstackContext);
        tenantManager.deleteTenant("tenant1");
    }

    @Test
    public void testListServers() throws Exception {
        OpenstackContext tenant1Context = new OpenstackContext(identityServiceUrl, "admin", "admin", "tenant1");
        ComputeManager computeManager = new ComputeManager(tenant1Context);
        List<Server> servers = computeManager.listServers();
        for (Server server : servers) {
            System.out.println(server);
        }
    }

    @Test
    public void testListVolumes() throws Exception {
        OpenstackContext tenant1Context = new OpenstackContext(identityServiceUrl, "admin", "admin", "tenant1");
        StorageManager storageManager = new StorageManager(tenant1Context);
        List<Volume> volumes = storageManager.listVolumes();
        for (Volume volume : volumes) {
            System.out.println(volume);
            //storageManager.deleteVolume(volume.getId());
        }
    }

    @Test
    public void testListNetworks() throws Exception {
        OpenstackContext tenant1Context = new OpenstackContext(identityServiceUrl, "admin", "admin", "tenant1");
        NetworkManager networkManager = new NetworkManager(tenant1Context);
        List<Network> networks = networkManager.listNetworks();
        for (Network network : networks) {
            System.out.println(network);
        }
    }

    @Test
    public void testReadScript() throws Exception {
        ComputeManager computeManager = new ComputeManager(openstackContext);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("ZIMBRA_SERVER", "/home/user/workspace/cloud-automation/trunk/src/zimbra/ubuntu12.04/installzimbra-ubuntu12.04");
        String script = (ServerInitUtil.getScript(ServerInitUtil.ServerType.ZIMBRA_SERVER, "datayestest.com", false));
        System.out.println(script);
    }

    @Test
    public void createZimbraServer() throws Exception {
        CloudManager cloudManager = new CloudManager();
        cloudManager.setIdentityServiceUrl(identityServiceUrl);
        cloudManager.setUserName("admin");
        cloudManager.setPassword("admin");
        cloudManager.createTenant("datayestest.com", "");
        cloudManager.createServer("datayestest.com", 20, ServerInitUtil.ServerFlavor.medium, ServerInitUtil.ServerType.ZIMBRA_SERVER);

    }
}
