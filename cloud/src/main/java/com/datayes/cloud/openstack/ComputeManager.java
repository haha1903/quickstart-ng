package com.datayes.cloud.openstack;

import com.datayes.cloud.openstack.access.*;
import com.datayes.cloud.util.DeleteUtil;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.SimpleType;

import java.io.IOException;
import java.util.List;

/**
 * User: changhai
 * Date: 13-8-13
 * Time: 上午11:15
 * DataYes
 */
public class ComputeManager {
    public static final String FLAVOR_NAME = "m1.tiny";
    public static final String SELF = "self";
    private OpenstackContext ctx;

    public ComputeManager(OpenstackContext openstackContext) {
        this.ctx = openstackContext;
    }

    public Server createServer(Server server, String volumeId) throws IOException {
        String ref = getFlavorRef();
        server.setFlavorRef(ref);
        List<Image> images = ctx.get(ctx.getImageUrl() + "/v2/images?name=cirros-0.3.1-x86_64-uec", "images", CollectionType.construct(List.class, SimpleType.construct(Image.class)));
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

    private String getFlavorRef() throws IOException {
        List<Flavor> flavors = ctx.get(ctx.getComputeUrl() + "/flavors", "flavors", CollectionType.construct(List.class
                , SimpleType.construct(Flavor.class)));
        for (Flavor flavor : flavors) {
            if (FLAVOR_NAME.equals(flavor.getName())) {
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
