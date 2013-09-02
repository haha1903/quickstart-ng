package com.datayes.cloud.openstack;

import com.datayes.cloud.openstack.access.*;
import com.datayes.cloud.util.DeleteUtil;
import com.datayes.cloud.util.ScriptUtil;
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
    public static final String FLAVOR_NAME = "m1.tiny";
    public static final String SELF = "self";
    private OpenstackContext ctx;

    @Autowired
    private ScriptUtil scriptUtil;

    private String getImageFileUrl(String img){
        if (img==null || img.isEmpty()) return "";
        return ctx.getImageUrl()+"/v2/images?name="+img;
    }

    public ComputeManager(OpenstackContext openstackContext) {
        this.ctx = openstackContext;
    }

    public Server createServer(Server server, String volumeId, String image, String key, String script) throws IOException {
        String ref = getFlavorRef();
        server.setFlavorRef(ref);
        List<Image> images = ctx.get(getImageFileUrl(image), "images", CollectionType.construct(List.class, SimpleType.construct(Image.class)));
        server.setUser_data(getEncodedScript(script));
        server.setKey_name(key);
        server.setImageRef(images.get(0).getId());
        server.addBlockDeviceMapping(volumeId, "/dev/vdb");
        return ctx.post(ctx.getComputeUrl() + "/servers", "server", server, "server", Server.class);
    }

    public String getEncodedScript(String script) {
        //TODO: change to private
        try{
            return Base64.encodeBase64URLSafeString(scriptUtil.getScript(script).getBytes());
        }
        catch(Exception ex){
            log.error("Error when get script file.",ex);
            return "";
        }
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
