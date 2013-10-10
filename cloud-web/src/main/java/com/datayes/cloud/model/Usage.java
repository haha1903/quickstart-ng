package com.datayes.cloud.model;

/**
 * User: changhai
 * Date: 13-9-8
 * Time: 下午6:03
 * DataYes
 */
public class Usage {
    private long cpu;
    private long ram;
    private long disk;
    private long volumn;

    public long getCpu() {
        return cpu;
    }

    public void setCpu(long cpu) {
        this.cpu = cpu;
    }

    public long getRam() {
        return ram;
    }

    public void setRam(long ram) {
        this.ram = ram;
    }

    public long getDisk() {
        return disk;
    }

    public void setDisk(long disk) {
        this.disk = disk;
    }

    public long getVolumn() {
        return volumn;
    }

    public void setVolumn(long volumn) {
        this.volumn = volumn;
    }
}
