package com.datayes.cloud.openstack.access;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * User: changhai
 * Date: 13-8-14
 * Time: 上午8:50
 * DataYes
 */
public class BlockDeviceMapping {
    @JsonProperty("volume_id")
    private String volumeId;
    @JsonProperty("device_name")
    private String deviceName;

    public BlockDeviceMapping() {
    }

    public BlockDeviceMapping(String volumeId, String deviceName) {
        this.volumeId = volumeId;
        this.deviceName = deviceName;
    }

    public String getVolumeId() {
        return volumeId;
    }

    public void setVolumeId(String volumeId) {
        this.volumeId = volumeId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    @Override
    public String toString() {
        return "BlockDeviceMapping{" +
                "volumeId='" + volumeId + '\'' +
                ", deviceName='" + deviceName + '\'' +
                '}';
    }
}
