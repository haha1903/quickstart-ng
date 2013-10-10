package com.datayes.cloud.openstack;

import com.datayes.cloud.openstack.access.Server;
import com.datayes.cloud.openstack.access.Tenant;
import com.datayes.cloud.openstack.access.Volume;
import com.datayes.cloud.util.ServerInitUtil;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: user
 * Date: 9/2/13
 * Time: 3:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class CloudManager {
    @Value("${openstack.identityServerUrl}")
    private String identityServiceUrl;

    @Value("${openstack.username}")
    private String userName;

    @Value("${openstack.password}")
    private String password;

    public void setIdentityServiceUrl(String identityServiceUrl) {
        this.identityServiceUrl = identityServiceUrl;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public OpenstackContext getAdminContext() throws Exception{
        return new OpenstackContext(identityServiceUrl, userName, password, userName);
    }

    public List<Tenant> listTenant() throws Exception {
        TenantManager tenantManager = new TenantManager(getAdminContext());
        List<Tenant> tenants = tenantManager.listTenants();
        return tenants;
    }

    public Tenant createTenant(String tenantName, String desc) throws Exception {
        TenantManager tenantManager = new TenantManager(getAdminContext());
        List<Tenant> tenants = tenantManager.listTenants();
        boolean exists = false;
        for (Tenant t : tenants){
            if (t.getName().equalsIgnoreCase(tenantName)){
                throw new Exception("Tenant already exists!");
            }
        }
        Tenant tenant = tenantManager.createTenant(tenantName, desc, true);

        OpenstackContext tenant1Context = new OpenstackContext(identityServiceUrl, userName, password, tenantName);
        ComputeManager computeManager = new ComputeManager(tenant1Context);
        computeManager.addSecurityGroupRule(ComputeManager.DEFAULT, 1,65535,"tcp","0.0.0.0/0");

        return tenant;
    }

    public void deleteTenant(String tenantName) throws Exception {
        TenantManager tenantManager = new TenantManager(getAdminContext());
        tenantManager.deleteTenant(tenantName);
    }

    public Server createServer(String tenantName, int volumnSize, ServerInitUtil.ServerFlavor flavor, ServerInitUtil.ServerType type) throws Exception{
        OpenstackContext tenant1Context = new OpenstackContext(identityServiceUrl, userName, password, tenantName);
        ComputeManager computeManager = new ComputeManager(tenant1Context);
        StorageManager storageManager = new StorageManager(tenant1Context);

        String serverName=type.getStrValue();
        Volume volume = storageManager.createVolume(new Volume(serverName+"vol", volumnSize));

        Server server = new Server(serverName);
        server = computeManager.createServer(server,volume.getId(),flavor,tenantName,type);
        return computeManager.bindFloatingIp(server);
    }

    public Server createZimbra(String tenantName) throws Exception {
        return createServer(tenantName, 10, ServerInitUtil.ServerFlavor.medium, ServerInitUtil.ServerType.ZIMBRA_SERVER);
    }

}
