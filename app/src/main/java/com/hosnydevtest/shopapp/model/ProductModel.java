package com.hosnydevtest.shopapp.model;

public class ProductModel {

    private String id;
    private String id_category;
    private String name;
    private String logo;
    private String details;
    private String image1;
    private String image2;
    private String image3;
    private int price;

    public ProductModel() {
    }

    public ProductModel(String id, String id_category, String name, String logo, String details, String image1, String image2, String image3, int price) {
        this.id = id;
        this.id_category = id_category;
        this.name = name;
        this.logo = logo;
        this.details = details;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_category() {
        return id_category;
    }

    public void setId_category(String id_category) {
        this.id_category = id_category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getImage3() {
        return image3;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
