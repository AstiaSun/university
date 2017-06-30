package com.univ.labs.objects;

import java.io.Serializable;

/**
 * Created by Анастасия on 14.05.2017.
 */
public class CreditCard implements Serializable{
    public final int CARD_NUMBER_LENGTH = 16;
    private String number;
    private int clientId;
    private int accountId;
    private String startDate;
    private String endDate;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
