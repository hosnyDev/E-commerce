package com.hosnydevtest.shopapp.model;

public class CategoryModel {

    private String id;
    private String name;
    private String image;

    public CategoryModel(String id, String name, String image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public CategoryModel() {
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
