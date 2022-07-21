package com.example.pizzamellisos.entities;

import java.util.Date;

public class Promotion {
    private String uid;
    private String name;
    private String products;
    private float discount;
    private double price;
    //private Date date_start;
    //private Date date_end;
    private String state;
    private String url;

    public Promotion() {
    }

    public Promotion(String uid, String name, String products, float discount,
                     double price, String url) {
        this.uid = uid;
        this.name = name;
        this.products = products;
        this.discount = discount;
        this.price = price;
        //this.date_start = date_start;
        //this.date_end = date_end;
        this.state = "A";
        this.url = url;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProducts() {
        return products;
    }

    public void setProducts(String products) {
        this.products = products;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    /*public Date getDate_start() {
        return date_start;
    }

    public void setDate_start(Date date_start) {
        this.date_start = date_start;
    }

    public Date getDate_end() {
        return date_end;
    }

    public void setDate_end(Date date_end) {
        this.date_end = date_end;
    }*/

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}