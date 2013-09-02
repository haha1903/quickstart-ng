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
}
