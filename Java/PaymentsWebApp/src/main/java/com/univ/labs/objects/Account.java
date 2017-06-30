package com.univ.labs.objects;

import java.io.Serializable;

/**
 * Created by Анастасия on 14.05.2017.
 */
public class Account implements Serializable{
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public enum Currency {
        UA, USD, EUR
    }
    private int id;
    private float balance;
    private Currency currency;
    private boolean isBlocked;
    private String password;
}
