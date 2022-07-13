package com.example.pizzamellisos.entities;

import java.util.ArrayList;

public class SaleHeaderForView {
    private String uid;
    private String client;
    private double total;
    private String observation;
    private ArrayList<SaleDetailForView>  details;

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

    public ArrayList<SaleDetailForView> getDetails() {
        return details;
    }

    public void setDetails(ArrayList<SaleDetailForView> details) {
        this.details = details;
    }

    public SaleHeaderForView(String uid) {
        this.uid = uid;
        this.client = "";
        this.total = 0;
        this.observation = "Sin Observacion";
        this.details = new ArrayList<>();
    }
    public SaleHeaderForView(String uid, String client,
                             double total,
                             String observation,
                             ArrayList<SaleDetailForView> details) {
        this.uid = uid;
        this.client = client;
        this.total = total;
        this.observation = observation;
        this.details = details;
    }
}
