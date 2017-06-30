package com.univ.labs.objects;

import java.util.Date;

/**
 * Created by Анастасия on 15.05.2017.
 */
public class Payment {
    private int id;
    private float price;
    private int sourceAccountId;
    private int targetAccountId;
    private String dateOfPayment;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getDateOfPayment() {
        return dateOfPayment;
    }

    public void setDateOfPayment(String dateOfPayment) {
        this.dateOfPayment = dateOfPayment;
    }

    public int getSourceAccountId() {
        return sourceAccountId;
    }

    public void setSourceAccountId(int sourceAccountId) {
        this.sourceAccountId = sourceAccountId;
    }

    public int getTargetAccountId() {
        return targetAccountId;
    }

    public void setTargetAccountId(int targetAccountId) {
        this.targetAccountId = targetAccountId;
    }
}
