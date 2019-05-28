package com.example.guzman.app1.models;

import android.os.Parcel;
import android.os.Parcelable;

public class HotelModel implements Parcelable{

    String hotelStars;
    String hotelName;
    String hotelDesc;
    String hotelLikes;
    String hotelLocation;
    String image;

    public HotelModel() {
    }


    protected HotelModel(Parcel in) {
        hotelStars = in.readString();
        hotelName = in.readString();
        hotelDesc = in.readString();
        hotelLikes = in.readString();
        hotelLocation = in.readString();
        image = in.readString();
    }

    public static final Creator<HotelModel> CREATOR = new Creator<HotelModel>() {
        @Override
        public HotelModel createFromParcel(Parcel in) {
            return new HotelModel(in);
        }

        @Override
        public HotelModel[] newArray(int size) {
            return new HotelModel[size];
        }
    };


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

    public String getHotelLikes() {
        return hotelLikes;
    }

    public void setHotelLikes(String hotelLikes) {
        this.hotelLikes = hotelLikes;
    }

    public String getHotelDesc() {
        return hotelDesc;
    }

    public void setHotelDesc(String hotelDesc) {
        this.hotelDesc = hotelDesc;
    }

    public String getHotelLocation() {
        return hotelLocation;
    }

    public void setHotelLocation(String hotelLocation) {
        this.hotelLocation = hotelLocation;
    }

    public static Creator<HotelModel> getCREATOR() {
        return CREATOR;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(hotelStars);
        dest.writeString(hotelName);
        dest.writeString(hotelDesc);
        dest.writeString(hotelLikes);
        dest.writeString(hotelLocation);
        dest.writeString(image);
    }
}
