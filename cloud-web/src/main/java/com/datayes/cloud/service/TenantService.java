package com.datayes.cloud.service;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.datayes.cloud.dao.CloudDao;
import com.datayes.cloud.model.Tenant;
import com.datayes.cloud.model.User;
import com.datayes.cloud.openstack.CloudManager;
import com.datayes.cloud.openstack.OpenstackContext;
import com.datayes.cloud.openstack.access.Flavor;
import com.datayes.cloud.openstack.access.Volume;
import com.datayes.cloud.openstack.access.VolumeAttachment;
import com.datayes.cloud.util.CommonUtil;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.SimpleType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * User: changhai
 * Date: 13-8-20
 * Time: 下午2:12
 * DataYes
 */
@Service
public class TenantService {
    @Autowired
    private CloudDao cloudDao;
    @Autowired
    private UserService userService;
    @Autowired
    private CloudManager cloudManager;
    @Autowired
    private OpenstackContextFactory openstackContextFactory;

    @Transactional(rollbackFor = Exception.class)
    public void create(Tenant tenant) throws Exception {
        
        String pwd = CommonUtil.generatePassword(12);
        tenant.setInitPassword(pwd);
        pwd = CommonUtil.generatePassword(12);
        tenant.setAdminPassword(pwd);
        Serializable s = cloudDao.save(tenant);
        System.out.println(s);
        
        cloudManager.createTenant(tenant.getDomain(), tenant.getDomain());
        String url = cloudManager.createAD(tenant.getDomain(),tenant.getAdminPassword());
        cloudManager.createZimbra(tenant.getDomain());
        
        tenant.setAdUrl(url);
        cloudDao.update(tenant);
                
        User userAdmin = new User();
        userAdmin.setPassword(tenant.getInitPassword());
        userAdmin.setName(tenant.getAdUser());
        userAdmin.setTenantId(tenant.getId());
        userService.createUser(tenant,userAdmin);
        //Manually add ad admin
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(long id) throws Exception {
        Tenant tenant = cloudDao.get(Tenant.class, id);
        cloudManager.deleteTenant(tenant.getDomain());
        cloudDao.delete(Tenant.class,id);                
    }
    
    @Transactional(rollbackFor = Exception.class)
    public void update(Tenant tenant) throws Exception {
        cloudDao.update(tenant);
    }

    @Transactional(rollbackFor = Exception.class)
    public void save(Tenant tenant) throws Exception {
        cloudDao.save(tenant);
        
    }

    public List<com.datayes.cloud.openstack.access.Server> getServers(Tenant tenant) throws IOException {
        OpenstackContext ctx = openstackContextFactory.createContext("datayes_staging");
        List<com.datayes.cloud.openstack.access.Server> servers = ctx
                .get(ctx.getComputeUrl() + "/servers/detail", "servers", CollectionType.construct(List.class, SimpleType.construct(com.datayes.cloud.openstack.access.Server.class)));
        List<Volume> volumes = ctx
                .get(ctx.getVolumeUrl() + "/volumes/detail", "volumes", CollectionType.construct(List.class, SimpleType.construct(Volume.class)));
        Map<String, Flavor> flavors = new HashMap<String, Flavor>();
        for (com.datayes.cloud.openstack.access.Server server : servers) {
            String flavorId = server.getFlavor().getId();
            Flavor flavor;
            if (flavors.containsKey(flavorId)) {
                flavor = flavors.get(flavorId);
            } else {
                flavor = ctx.get(ctx.getComputeUrl() + "/flavors/" + flavorId, "flavor", Flavor.class);
                flavors.put(flavorId, flavor);
            }
            Volume volume = getVolume(server, volumes);
            server.setVcpu(flavor.getVcpus());
            server.setRam(flavor.getRam() / 1024.0);
            server.setDisk(flavor.getDisk() + (volume == null ? 0 : volume.getSize()));
        }
        return servers;
    }

    private Volume getVolume(com.datayes.cloud.openstack.access.Server server, List<Volume> volumes) {
        for (Volume volume : volumes) {
            List<VolumeAttachment> attachments = volume.getAttachments();
            for (VolumeAttachment attachment : attachments) {
                if (server.getId().equals(attachment.getServerId()))
                    return volume;
            }
        }
        return null;
    }       
}
