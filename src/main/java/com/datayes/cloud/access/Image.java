package com.datayes.cloud.access;

/**
 * User: changhai
 * Date: 13-8-13
 * Time: 下午1:36
 * DataYes
 */
public class Image {
    private String id;
    private String name;

    public Image() {
    }

    public Image(String name) {
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

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Image{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
