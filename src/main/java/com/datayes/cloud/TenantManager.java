package com.datayes.cloud;

import com.datayes.cloud.access.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * User: changhai
 * Date: 13-8-9
 * Time: 下午4:13
 */
public class TenantManager {
    private static final Logger log = LoggerFactory.getLogger(TenantManager.class);
    public static final String PUBLIC_NETWORK = "public";
    private final OpenstackContext ctx;

    public TenantManager(OpenstackContext openstackContext) {
        this.ctx = openstackContext;
    }

    public Tenant createTenant(String name, String description, boolean enabled) throws IOException {
        Tenant tenant = new Tenant(name, description, enabled);
        Tenant result = ctx.post(ctx.getIdentityAdminUrl() + "/tenants", "tenant", tenant, "tenant", Tenant.class);
        User user = ctx.getUser();
        Role role = ctx.getRole("admin");
        ctx.put(ctx.getIdentityAdminUrl() + "/tenants/" + result.getId() + "/users/" + user.getId() + "/roles/OS-KSADM/" + role.getId());
        return result;
    }

    public List<Tenant> listTenants() throws IOException {
        return ctx.listTenants();
    }

    public void deleteTenant(String name) throws IOException, InterruptedException {
        OpenstackContext tenantContext = new OpenstackContext(ctx.getIdentityServiceUrl(), ctx.getUsername(), ctx.getPassword(), name);
        Tenant tenant = tenantContext.getTenant();
        if (tenant != null) {
            ComputeManager computeManager = new ComputeManager(tenantContext);
            List<Server> servers = computeManager.listServers();
            for (Server server : servers) computeManager.deleteServer(server.getId());
            StorageManager storageManager = new StorageManager(tenantContext);
            List<Volume> volumes = storageManager.listVolumes();
            for (Volume volume : volumes) storageManager.deleteVolume(volume.getId());
            NetworkManager networkManager = new NetworkManager(tenantContext);
            List<Network> networks = networkManager.listNetworks();
            for (Network network : networks) {
                if (!PUBLIC_NETWORK.equals(network.getName())) {
                    try {
                        networkManager.deleteNetwork(network.getId());
                    } catch (IOException e) {
                        log.error("can't delete network, network = {}", network, e);
                    }
                }
            }
            ctx.delete(ctx.getIdentityAdminUrl() + "/tenants/" + tenant.getId());
        }
    }
}
