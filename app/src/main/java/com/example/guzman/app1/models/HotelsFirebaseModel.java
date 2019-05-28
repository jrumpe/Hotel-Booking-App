package com.example.guzman.app1.models;

public class HotelsFirebaseModel {

    String hotelStars;
    String hotelName;
    String hotelDesc;
    String hotelLikes;
    String hotelLocation;
    String image;

    public HotelsFirebaseModel() {
    }

    public HotelsFirebaseModel(String hotelStars, String hotelName, String hotelDesc, String hotelLikes, String hotelLocation, String image) {
        this.hotelStars = hotelStars;
        this.hotelName = hotelName;
        this.hotelDesc = hotelDesc;
        this.hotelLikes = hotelLikes;
        this.hotelLocation = hotelLocation;
        this.image = image;
    }

    public String getHotelStars() {
        return hotelStars;
    }

    public void setHotelStars(String hotelStars) {
        this.hotelStars = hotelStars;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getHotelDesc() {
        return hotelDesc;
    }

    public void setHotelDesc(String hotelDesc) {
        this.hotelDesc = hotelDesc;
    }

    public String getHotelLikes() {
        return hotelLikes;
    }

    public void setHotelLikes(String hotelLikes) {
        this.hotelLikes = hotelLikes;
    }

    public String getHotelLocation() {
        return hotelLocation;
    }

    public void setHotelLocation(String hotelLocation) {
        this.hotelLocation = hotelLocation;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
