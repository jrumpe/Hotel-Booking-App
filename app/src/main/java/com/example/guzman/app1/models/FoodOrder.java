package com.example.guzman.app1.models;

public class FoodOrder {
    private String name;
    private String email ;
    private String phone;
    private String priceyafood;
    private String nameyafood;
    private String street;
    private String road;
    private String house;
    private String comment;
    private String status;
    private String paymentState;

    public FoodOrder() {
    }

    public FoodOrder(String name, String email, String phone, String priceyafood, String nameyafood, String street, String road, String house, String comment, String status, String paymentState) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.priceyafood = priceyafood;
        this.nameyafood = nameyafood;
        this.street = street;
        this.road = road;
        this.house = house;
        this.comment = comment;
        this.status = status;
        this.paymentState = paymentState;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPriceyafood() {
        return priceyafood;
    }

    public void setPriceyafood(String priceyafood) {
        this.priceyafood = priceyafood;
    }

    public String getNameyafood() {
        return nameyafood;
    }

    public void setNameyafood(String nameyafood) {
        this.nameyafood = nameyafood;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getRoad() {
        return road;
    }

    public void setRoad(String road) {
        this.road = road;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentState() {
        return paymentState;
    }

    public void setPaymentState(String paymentState) {
        this.paymentState = paymentState;
    }
}
