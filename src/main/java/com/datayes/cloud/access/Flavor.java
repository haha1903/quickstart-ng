package com.datayes.cloud.access;

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

    @Override
    public String toString() {
        return "Flavor{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", links=" + links +
                '}';
    }
}
