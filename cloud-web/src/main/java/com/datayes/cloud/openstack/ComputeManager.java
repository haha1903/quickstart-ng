package com.datayes.cloud.openstack;

import com.datayes.cloud.openstack.access.*;
import com.datayes.cloud.util.DeleteUtil;
import com.datayes.cloud.util.ServerInitUtil;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.SimpleType;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.util.HashMap;
import java.util.List;

/**
 * User: changhai
 * Date: 13-8-13
 * Time: 上午11:15
 * DataYes
 */
public class ComputeManager {
    private static final Logger log = LoggerFactory.getLogger(ComputeManager.class);
    //public static final String FLAVOR_NAME = "m1.tiny";
    public static final String SELF = "self";
    public static final String DEFAULT = "default";
    private OpenstackContext ctx;

    public ComputeManager(OpenstackContext openstackContext) {
        this.ctx = openstackContext;
    }

    public Server createServer(Server server, String volumnId, ServerInitUtil.ServerFlavor flavor, String domainName, ServerInitUtil.ServerType serverType) throws Exception{
        try{
            String imageUrl = ctx.getImageUrl()+"/v2/images?name="+ServerInitUtil.getImageUrl(serverType);
            String encodedScript = ServerInitUtil.getScript(serverType,domainName,true);
            String flavorRef= getFlavorRef(flavor);
            return createServer(server, volumnId, flavorRef,domainName, imageUrl, encodedScript);
        }
        catch(Exception ex){
            log.error("Error creating server.", ex);
            throw ex;
        }
    }

    public Server createServer(Server server, String volumeId, String flavorRef, String domainName, String image, String script) throws IOException {
        server.setFlavorRef(flavorRef);
        List<Image> images = ctx.get(image, "images", CollectionType.construct(List.class, SimpleType.construct(Image.class)));
        server.setUser_data(script);
        server.setImageRef(images.get(0).getId());
        server.addBlockDeviceMapping(volumeId, "/dev/vdb");
        return ctx.post(ctx.getComputeUrl() + "/servers", "server", server, "server", Server.class);

    }

    public List<Server> listServers() throws IOException {
        List<Server> servers = ctx.get(ctx.getComputeUrl() + "/servers", "servers", CollectionType.construct(List.class, SimpleType.construct(Server.class)));
        for (Server server : servers) {
            System.out.println(server);
        }
        return servers;
    }

    private String getFlavorRef(ServerInitUtil.ServerFlavor serverFlavor) throws IOException {
        List<Flavor> flavors = ctx.get(ctx.getComputeUrl() + "/flavors", "flavors", CollectionType.construct(List.class
                , SimpleType.construct(Flavor.class)));
        for (Flavor flavor : flavors) {
            if (serverFlavor.getStrValue().equalsIgnoreCase(flavor.getName())) {
                for (Link link : flavor.getLinks()) {
                    if (SELF.equals(link.getRel())) {
                        return link.getHref();
                    }
                }
            }
        }
        return null;
    }

    public VolumeAttachment attach(String serverId, String volumeId) throws IOException {
        return ctx.post(ctx.getComputeUrl() + "/servers/" + serverId + "/os-volume_attachments", "volumeAttachment", new VolumeAttachment(volumeId, "/dev/vdd"), "volumeAttachment", VolumeAttachment.class);
    }

    public List<Action> getActions(String serverId) throws IOException {
        return ctx.get(ctx.getComputeUrl() + "/servers/" + serverId + "/os-instance-actions", "instanceActions", CollectionType.construct(List.class, SimpleType.construct(Action.class)));
    }

    public void deleteServer(final String serverId) throws IOException {
        ctx.delete(ctx.getComputeUrl() + "/servers/" + serverId);
        DeleteUtil.waitStatus(new DeleteUtil.StatusHandler<Server>() {
                                  @Override
                                  public Server getStatus() throws IOException {
                                      return ctx.get(ctx.getComputeUrl() + "/servers/" + serverId, "server", Server.class);
                                  }
                              }, new DeleteUtil.StatusChecker<Server>() {
                                  @Override
                                  public boolean checkStatus(Server status) throws IOException {
                                      return status == null;
                                  }
                              }
        );
    }

    public Floating_ip getFloatIp() throws Exception{
        return ctx.post(ctx.getComputeUrl()+"/os-floating-ips", "", new Floating_ip(), "floating_ip",Floating_ip.class);
    }

    public Server getServerDetail(String serverId) throws Exception {
        if (serverId==null || serverId.isEmpty()) return null;

        List<Server> servers = ctx.get(ctx.getComputeUrl()+"/servers/detail","servers", CollectionType.construct(List.class, SimpleType.construct(Server.class)));
        for (Server server: servers)
            if (server.getId().equals(serverId))
                return server;
        return null;
    }

    public Server bindFloatingIp(Server serverToBind) throws Exception {
        Floating_ip ip = getFloatIp();
        Server serverDetail =  getServerDetail(serverToBind.getId());

        if (serverDetail==null) return null;

        String selfLink=null;
        for (Link link : serverDetail.getLinks()){
             if (SELF.equals(link.getRel())) {
                selfLink = link.getHref();
                break;
            }
        }

        if (selfLink==null) return null;

        //http://10.20.112.65:8774/v2/e4c4e0ffe2934e9d84707da62e14155b/servers/88bb0fc2-bcc0-4d3a-834c-38f425559d14/action -X POST -H "X-Auth-Project-Id: admin" -H "User-Agent: python-novaclient" -H "Content-Type: application/json" -H "Accept: application/json" -H "X-Auth-Token: 4f231d77ece749b596ecb1855f8007bf" -d '{"addFloatingIp": {"address": "10.20.112.84"}}'
        HashMap<String, String> addrParam= new HashMap<String, String>();
        addrParam.put("address", ip.getIp());
        ctx.post(selfLink+"/action", "addFloatingIp", addrParam);

        return serverDetail;
    }

    public SecurityGroupRule addSecurityGroupRule(String groupName, int fromPort, int toPort, String protocol, String cidr) throws Exception{
        List<SecurityGroup> securityGroups =  ctx.get(ctx.getComputeUrl()+"/os-security-groups","security_groups", CollectionType.construct(List.class, SimpleType.construct(SecurityGroup.class)));
        int groupId = -1;
        for (SecurityGroup sg: securityGroups){
            if (sg.getName().equals(groupName)) {
                groupId = sg.getId();
                break;
            }
        }

        if (groupId<0) {
            log.error("Specified group not exist, failed to add rule.");
            return null;
        }

        SecurityGroupRule rule = new SecurityGroupRule();
        rule.setParentGroupId(groupId);
        rule.setFromPort(String.valueOf(fromPort));
        rule.setToPort(String.valueOf(toPort));
        rule.setIpProtocol(protocol);
        rule.setCidr(cidr);

        return ctx.post(ctx.getComputeUrl()+"/os-security-group-rules", "security_group_rule", rule, "security_group_rule",SecurityGroupRule.class);
    }
}
