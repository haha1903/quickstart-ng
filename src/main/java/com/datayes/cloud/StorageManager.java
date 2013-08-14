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
    public static final int MAX_WAIT = 10;
    public static final int INTERVAL = 1000;
    public static final String AVAILABLE = "available";
    private OpenstackContext openstackContext;

    public StorageManager(OpenstackContext openstackContext) {
        this.openstackContext = openstackContext;
    }

    public Volume createVolume(Volume volume) throws IOException, InterruptedException {
        Volume result = openstackContext.post("http://10.20.112.226:8776/v1/" + openstackContext.getTenant().getId() + "/volumes", "volume", volume, "volume", Volume.class);
        Volume status = null;
        for (int i = 0; i < MAX_WAIT; i++) {
            status = openstackContext.get("http://10.20.112.226:8776/v1/" + openstackContext.getTenant().getId() + "/volumes/" + result.getId(), "volume", Volume.class);
            if (isAvailable(status)) break;
            Thread.sleep(INTERVAL);
        }
        if(!isAvailable(status)) throw new IOException("create volume failure, volume = " + volume);
        return result;
    }

    private boolean isAvailable(Volume status) {
        return AVAILABLE.equals(status.getStatus());
    }
}
