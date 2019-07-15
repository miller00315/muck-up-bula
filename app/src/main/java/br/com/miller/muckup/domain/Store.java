package br.com.miller.muckup.domain;

import java.util.ArrayList;

public class Store {

    private int id;
    private String name;
    private String description;
    private ArrayList<Departament> departaments;
    private String image;
    private String address;
    private String time;
    private String phone;
    private String city;
    private int classification = 0;

    public int getClassification() { return classification; }

    public void setClassification(int classification) { this.classification = classification; }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Departament> getDepartaments() {
        return departaments;
    }

    public void setDepartaments(ArrayList<Departament> departaments) {
        this.departaments = departaments;
    }
}
