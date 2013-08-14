package com.datayes.cloud;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * User: changhai
 * Date: 13-8-13
 * Time: 下午3:51
 * DataYes
 */
public class VolumeAttachment {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String id;
    private String device;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String serverId;
    private String volumeId;

    public VolumeAttachment() {
    }

    public VolumeAttachment(String volumeId, String device) {
        this.device = device;
        this.volumeId = volumeId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getVolumeId() {
        return volumeId;
    }

    public void setVolumeId(String volumeId) {
        this.volumeId = volumeId;
    }
}
