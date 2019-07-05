package br.com.miller.muckup.models;

import android.util.Log;

import java.util.ArrayList;
import java.util.Date;

public class Buy {

    private String id;
    private int storeId;
    private int payMode = 0;
    private double troco;
    private String storeCity;
    private String userCity;
    private String userId;
    private Double totalValue;
    private Double sendValue;
    private Date solicitationDate, deliverDate, receiverDate;
    private ArrayList<Offer> offers;
    private String address;

    public Double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(Double value, int quantity) {

        if(totalValue == null){
            totalValue = value * quantity;
            Log.w("temp", "Era null");
        }else{

            totalValue += (value * quantity);

        }

    }

    public int getPayMode() {
        return payMode;
    }

    public void setPayMode(int payMode) {
        this.payMode = payMode;
    }

    public double getTroco() {
        return troco;
    }

    public void setTroco(double troco) {
        this.troco = troco;
    }

    public Double getSendValue() {
        return sendValue;
    }

    public void setSendValue(Double sendValue) {
        this.sendValue = sendValue;
    }

    public String getUserCity() {
        return userCity;
    }

    public void setUserCity(String userCity) {
        this.userCity = userCity;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStoreCity() {
        return storeCity;
    }

    public void setStoreCity(String storeCity) {
        this.storeCity = storeCity;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getSolicitationDate() {
        return solicitationDate;
    }

    public void setSolicitationDate(Date solicitationDate) {
        this.solicitationDate = solicitationDate;
    }

    public Date getDeliverDate() {
        return deliverDate;
    }

    public void setDeliverDate(Date deliverDate) {
        this.deliverDate = deliverDate;
    }

    public Date getReceiverDate() {
        return receiverDate;
    }

    public void setReceiverDate(Date receiverDate) {
        this.receiverDate = receiverDate;
    }

    public ArrayList<Offer> getOffers() {
        return offers;
    }

    public void setOffers(ArrayList<Offer> offers) {
        this.offers = offers;
    }


}
