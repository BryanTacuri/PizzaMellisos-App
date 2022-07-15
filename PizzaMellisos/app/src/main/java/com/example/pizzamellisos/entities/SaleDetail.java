package com.example.pizzamellisos.entities;

public class SaleDetail {

    private String uid;
    private String uidSaleHeader;
    private String uidProduct;
    private int count;
    private double price;
    private double total;


    public SaleDetail(){}
    public SaleDetail(String uid, String uidSaleHeader, String uidProduct, int count, double price, double total) {
        this.uid = uid;
        this.uidSaleHeader = uidSaleHeader;
        this.uidProduct = uidProduct;
        this.count = count;
        this.price = price;
        this.total = total;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUidSaleHeader() {
        return uidSaleHeader;
    }

    public void setUidSaleHeader(String uidSaleHeader) {
        this.uidSaleHeader = uidSaleHeader;
    }

    public String getUidProduct() {
        return uidProduct;
    }

    public void setUidProduct(String uidProduct) {
        this.uidProduct = uidProduct;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
