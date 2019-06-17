package br.com.miller.muckup.models;

import android.net.Uri;

public class Result {

    private int id;
    private double value;
    private String description;
    private String title;
    private double sendValue;
    private Uri image;

    public Uri getImage() {
        return image;
    }

    public void setImage(Uri image) {
        this.image = image;
    }

    public int getIdStore() {
        return idStore;
    }

    public void setIdStore(int idStore) {
        this.idStore = idStore;
    }

    private int idStore;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value =  value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getSendValue() {
        return sendValue;
    }

    public void setSendValue(double sendValue) {
        this.sendValue = sendValue;
    }
}
