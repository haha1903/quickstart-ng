package com.datayes.cloud;

import com.datayes.cloud.access.Volume;

import java.io.IOException;

/**
 * User: changhai
 * Date: 13-8-13
 * Time: 下午3:14
 * DataYes
 */
public class StorageManager {
    private OpenstackContext openstackContext;

    public StorageManager(OpenstackContext openstackContext) {
        this.openstackContext = openstackContext;
    }

    public Volume createVolume(Volume volume) throws IOException {
        return openstackContext.post("http://10.20.112.226:8776/v1/" + openstackContext.getTenant().getId() + "/volumes", "volume", volume, "volume", Volume.class);
    }
}
