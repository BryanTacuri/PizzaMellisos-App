package com.example.pizzamellisos.entities;

import java.util.ArrayList;

public class SaleHeaderForView {
    private String uid;
    private String client;
    private double total;
    private String observation;
    private ArrayList<SaleDetailForView>  details;



    public SaleHeaderForView(String uid) {
        this.uid = uid;
        this.client = "";
        this.total = 0;
        this.observation = "Sin Observacion";
        this.details = new ArrayList<>();
    }
    public SaleHeaderForView(String uid, String client, double total, String observation, ArrayList<SaleDetailForView> details) {
        this.uid = uid;
        this.client = client;
        this.total = total;
        this.observation = observation;
        this.details = details;
    }
}
