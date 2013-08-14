package com.datayes.cloud;

import com.datayes.cloud.access.*;
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
    private OpenstackContext openstackContext;

    public ComputeManager(OpenstackContext openstackContext) {
        this.openstackContext = openstackContext;
    }

    public Server createServer(Server server) throws IOException {
        Tenant tenant = openstackContext.getTenant();
        String ref = getFlavorRef(tenant);
        server.setFlavorRef(ref);
        List<Image> images = openstackContext.get("http://10.20.112.226:9292/v2/images?name=cirros-0.3.1-x86_64-uec", "images", CollectionType.construct(List.class, SimpleType.construct(Image.class)));
        server.setImageRef(images.get(0).getId());
        return openstackContext.post("http://10.20.112.226:8774/v2/" + tenant.getId() + "/servers", "server", server, "server", Server.class);
    }

    private String getFlavorRef(Tenant tenant) throws IOException {
        List<Flavor> flavors = openstackContext.get("http://10.20.112.226:8774/v2/" + tenant.getId() + "/flavors", "flavors", CollectionType.construct(List.class
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
        return openstackContext.post("http://10.20.112.226:8774/v2/" + openstackContext.getTenant().getId() + "/servers/" + serverId + "/os-volume_attachments", "volumeAttachment", new VolumeAttachment(volumeId, "/dev/vdd"), "volumeAttachment", VolumeAttachment.class);
    }
}
