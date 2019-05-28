package com.example.guzman.app1.models;

public class ReservationModel {
    String image, hotel, room, price, arrival, departure;

    public ReservationModel() {
    }

    public ReservationModel(String image, String hotel, String room, String price, String arrival, String departure) {
        this.image = image;
        this.hotel = hotel;
        this.room = room;
        this.price = price;
        this.arrival = arrival;
        this.departure = departure;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getHotel() {
        return hotel;
    }

    public void setHotel(String hotel) {
        this.hotel = hotel;
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

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }
}
