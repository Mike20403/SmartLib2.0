package com.app.smartlibhost.model;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import static java.text.DateFormat.getDateTimeInstance;

public class Order {
    ArrayList<SachFB> borrowbooks;
    Long date_borrowed;
    String customer_UID;
    String status;
    Long date_payback;
    String key;


    public Order(ArrayList<SachFB> borrowbooks, Long date_borrowed, String customer_UID, String status, Long date_payback, String key) {
        this.borrowbooks = borrowbooks;
        this.date_borrowed = date_borrowed;
        this.customer_UID = customer_UID;
        this.status = status;
        this.date_payback = date_payback;
        this.key = key;
    }

    public Order() {
    }

    public Long getDate_payback() {
        return date_payback;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
    public ArrayList<SachFB> getBorrowbooks() {
        return borrowbooks;
    }

    public void setBorrowbooks(ArrayList<SachFB> borrowbooks) {
        this.borrowbooks = borrowbooks;
    }

    public Long getDate_borrowed() {
        return date_borrowed;
    }

    public void setDate_borrowed(Long date_borrowed) {
        this.date_borrowed = date_borrowed;
    }

    public void setDate_payback(Long date_payback) {
        this.date_payback = date_payback;
    }

    public String getCustomer_UID() {
        return customer_UID;
    }

    public void setCustomer_UID(String customer_UID) {
        this.customer_UID = customer_UID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public static String getTimeDate(long timestamp){
        try{
            DateFormat dateFormat = getDateTimeInstance();
            Date netDate = (new Date(timestamp));
            return dateFormat.format(netDate);
        } catch(Exception e) {
            return "date";
        }
    }



}
