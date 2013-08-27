package com.datayes.cloud.openstack.access;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * User: changhai
 * Date: 13-8-13
 * Time: 下午3:15
 * DataYes
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Volume {
    private String id;
    @JsonProperty("display_name")
    private String displayName;
    private String status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String description;
    private int size;
    @JsonProperty("volume_type")
    private String volumeType;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("availability_zone")
    private String availabilityZone;

    public Volume() {
    }

    public Volume(String displayName, int size) {
        this.displayName = displayName;
        this.size = size;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getVolumeType() {
        return volumeType;
    }

    public void setVolumeType(String volumeType) {
        this.volumeType = volumeType;
    }

    public String getAvailabilityZone() {
        return availabilityZone;
    }

    public void setAvailabilityZone(String availabilityZone) {
        this.availabilityZone = availabilityZone;
    }

    @Override
    public String toString() {
        return "Volume{" +
                "id='" + id + '\'' +
                ", displayName='" + displayName + '\'' +
                ", status='" + status + '\'' +
                ", description='" + description + '\'' +
                ", size=" + size +
                ", volumeType='" + volumeType + '\'' +
                ", availabilityZone='" + availabilityZone + '\'' +
                '}';
    }
}
