package com.datayes.cloud.openstack.access;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * User: changhai
 * Date: 13-8-13
 * Time: 上午11:17
 * DataYes
 */
public class Server {
    private String id;
    private String name;
    private String status;
    private String imageRef;
    private String flavorRef;
    private String user_data;
    @JsonProperty("block_device_mapping")
    private List<BlockDeviceMapping> blockDeviceMappings = new ArrayList<BlockDeviceMapping>();
    private List<ServerAddress> novanetwork = new ArrayList<ServerAddress>();
    private List<Link> links  = new ArrayList<Link>();
    private Flavor flavor;
    private Image image;
    @JsonIgnore
    private int vcpu;
    @JsonIgnore
    private double ram;
    @JsonIgnore
    private int disk;

    public Server() {
    }

    public Server(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getImageRef() {
        return imageRef;
    }

    public void setImageRef(String imageRef) {
        this.imageRef = imageRef;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFlavorRef() {
        return flavorRef;
    }

    public void setFlavorRef(String flavorRef) {
        this.flavorRef = flavorRef;
    }

    public List<BlockDeviceMapping> getBlockDeviceMappings() {
        return blockDeviceMappings;
    }

    public void setBlockDeviceMappings(List<BlockDeviceMapping> blockDeviceMappings) {
        this.blockDeviceMappings = blockDeviceMappings;
    }

    public String getUser_data() {
        return user_data;
    }

    public void setUser_data(String user_data) {
        this.user_data = user_data;
    }

    public void addBlockDeviceMapping(String volumeId, String deviceName) {
        blockDeviceMappings.add(new BlockDeviceMapping(volumeId, deviceName));
    }

    public List<ServerAddress> getNovanetwork() {
        return novanetwork;
    }

    public void setNovanetwork(List<ServerAddress> novanetwork) {
        this.novanetwork = novanetwork;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public Flavor getFlavor() {
        return flavor;
    }

    public void setFlavor(Flavor flavor) {
        this.flavor = flavor;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public int getVcpu() {
        return vcpu;
    }

    public void setVcpu(int vcpu) {
        this.vcpu = vcpu;
    }

    public double getRam() {
        return ram;
    }

    public void setRam(double ram) {
        this.ram = ram;
    }

    public int getDisk() {
        return disk;
    }

    public void setDisk(int disk) {
        this.disk = disk;
    }

    @Override
    public String toString() {
        return "Server{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                ", imageRef='" + imageRef + '\'' +
                ", flavorRef='" + flavorRef + '\'' +
                ", user_data='" + user_data + '\'' +
                ", blockDeviceMappings=" + blockDeviceMappings +
                ", novanetwork=" + novanetwork +
                ", links=" + links +
                ", flavor=" + flavor +
                ", image=" + image +
                ", vcpu=" + vcpu +
                ", ram=" + ram +
                ", disk=" + disk +
                '}';
    }
}
