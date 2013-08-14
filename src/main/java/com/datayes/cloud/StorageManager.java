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
        for (int i = 0; i < MAX_WAIT; i++) {
            Volume status = openstackContext.get("http://10.20.112.226:8776/v1/" + openstackContext.getTenant().getId() + "/volumes/" + result.getId(), "volume", Volume.class);
            if (AVAILABLE.equals(status.getStatus())) break;
            Thread.sleep(INTERVAL);
        }
        return result;
    }
}
