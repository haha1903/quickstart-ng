package com.datayes.cloud;

/**
 * User: changhai
 * Date: 13-8-13
 * Time: 上午11:17
 * DataYes
 */
public class Server {
    private String id;
    private String name;
    private String imageRef;
    private String flavorRef;

    public Server() {
    }

    public Server(String id, String name, String imageRef, String flavorRef) {
        this.id = id;
        this.name = name;
        this.imageRef = imageRef;
        this.flavorRef = flavorRef;
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

    public String getFlavorRef() {
        return flavorRef;
    }

    public void setFlavorRef(String flavorRef) {
        this.flavorRef = flavorRef;
    }
}
