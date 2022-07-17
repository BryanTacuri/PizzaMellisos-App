package com.example.pizzamellisos.entities;

public class SaleHeader {
    private String uid;
    private String client;
    private double total;
    private String observation;


    public SaleHeader() {


    }
    public SaleHeader(String uid) {
        this.uid = uid;

    }
    public SaleHeader(String uid, String client, double total, String observation) {
        this.uid = uid;
        this.client = client;
        this.total = total;
        this.observation = observation;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }
}
