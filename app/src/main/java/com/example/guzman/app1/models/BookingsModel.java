package com.example.guzman.app1.models;

public class BookingsModel {

    String name;
    String room;
    String amount;
    String arrival;
    String departure;
    String status;

    public BookingsModel() {
    }

    public BookingsModel(String name, String room, String amount, String arrival, String departure, String status) {
        this.name = name;
        this.room = room;
        this.amount = amount;
        this.arrival = arrival;
        this.departure = departure;
        this.status = status;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
