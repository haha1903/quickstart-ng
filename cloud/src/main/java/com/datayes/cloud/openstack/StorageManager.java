package com.datayes.cloud.openstack;

import com.datayes.cloud.openstack.access.Volume;
import com.datayes.cloud.util.DeleteUtil;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.SimpleType;

import java.io.IOException;
import java.util.List;

/**
 * User: changhai
 * Date: 13-8-13
 * Time: 下午3:14
 * DataYes
 */
public class StorageManager {
    public static final String AVAILABLE = "available";
    private OpenstackContext ctx;

    public StorageManager(OpenstackContext openstackContext) {
        this.ctx = openstackContext;
    }

    public Volume createVolume(Volume volume) throws IOException, InterruptedException {
        final Volume result = ctx.post(ctx.getVolumeUrl() + "/volumes", "volume", volume, "volume", Volume.class);
        DeleteUtil.waitStatus(new DeleteUtil.StatusHandler<Volume>() {
                                  @Override
                                  public Volume getStatus() throws IOException {
                                      return ctx.get(ctx.getVolumeUrl() + "/volumes/" + result.getId(), "volume", Volume.class);
                                  }
                              }, new DeleteUtil.StatusChecker<Volume>() {
                                  @Override
                                  public boolean checkStatus(Volume status) {
                                      return AVAILABLE.equals(status.getStatus());
                                  }
                              }
        );
        return result;
    }

    public List<Volume> listVolumes() throws IOException {
        return ctx.get(ctx.getVolumeUrl() + "/volumes", "volumes", CollectionType.construct(List.class, SimpleType.construct(Volume.class)));
    }

    public void deleteVolume(final String volumeId) throws IOException, InterruptedException {
        ctx.delete(ctx.getVolumeUrl() + "/volumes/" + volumeId);
        DeleteUtil.waitStatus(new DeleteUtil.StatusHandler<Volume>() {
                                  @Override
                                  public Volume getStatus() throws IOException {
                                      return ctx.get(ctx.getVolumeUrl() + "/volumes/" + volumeId, "volume", Volume.class);
                                  }
                              }, new DeleteUtil.StatusChecker<Volume>() {
                                  @Override
                                  public boolean checkStatus(Volume status) {
                                      return status == null;
                                  }
                              }
        );
    }
}
