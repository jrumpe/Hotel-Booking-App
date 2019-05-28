package com.example.guzman.app1.models;

public class Request {
    private String name;
    private String email;
    private String phone;
    private String room;
    private String price;
    private String arrival;
    private String depart;
    private String status;
    private String comment;
    private String paymentState;

    public Request() {
    }

    public Request(String name, String email, String phone, String room, String price, String arrival, String depart, String status, String comment, String paymentState) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.room = room;
        this.price = price;
        this.arrival = arrival;
        this.depart = depart;
        this.status = status;
        this.comment = comment;
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

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPaymentState() {
        return paymentState;
    }

    public void setPaymentState(String paymentState) {
        this.paymentState = paymentState;
    }
}
