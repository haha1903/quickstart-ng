package com.datayes.cloud.openstack.access;

import java.util.List;

/**
 * User: changhai
 * Date: 13-8-13
 * Time: 下午1:22
 * DataYes
 */
public class Flavor {
    private String id;
    private String name;
    private List<Link> links;
    private int ram;
    private int vcpus;
    private int disk;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public int getRam() {
        return ram;
    }

    public void setRam(int ram) {
        this.ram = ram;
    }

    public int getVcpus() {
        return vcpus;
    }

    public void setVcpus(int vcpus) {
        this.vcpus = vcpus;
    }

    public int getDisk() {
        return disk;
    }

    public void setDisk(int disk) {
        this.disk = disk;
    }

    @Override
    public String toString() {
        return "Flavor{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", links=" + links +
                ", ram=" + ram +
                ", vcpus=" + vcpus +
                ", disk=" + disk +
                '}';
    }
}
