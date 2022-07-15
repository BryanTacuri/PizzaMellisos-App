package com.example.pizzamellisos.entities;

public class SaleDetailForView {

    private String uid;

    private Product producto;
    private int count;
    private double price;
    private double total;



    public SaleDetailForView(String uid, Product producto, int count, double price, double total) {
        this.uid = uid;
        this.producto = producto;
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

    public Product getProducto() {
        return producto;
    }

    public void setProducto(Product producto) {
        this.producto = producto;
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
