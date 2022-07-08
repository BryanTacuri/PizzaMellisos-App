package com.example.pizzamellisos.entities;

public class Product {

    private String url;
    private String uid;
    private String nameProduct;
    private String descriptionProduct;
    private double priceProduct;
    private String state;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }



    public Product(String uid, String url, String nameProduct, String descriptionProduct, double priceProduct) {
        this.url = url;
        this.state="A";
        this.nameProduct = nameProduct;
        this.descriptionProduct = descriptionProduct;
        this.priceProduct = priceProduct;
        this.uid=uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public String getDescriptionProduct() {
        return descriptionProduct;
    }

    public void setDescriptionProduct(String descriptionProduct) {
        this.descriptionProduct = descriptionProduct;
    }

    public double getPriceProduct() {
        return priceProduct;
    }

    public void setPriceProduct(double priceProduct) {
        this.priceProduct = priceProduct;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


}
