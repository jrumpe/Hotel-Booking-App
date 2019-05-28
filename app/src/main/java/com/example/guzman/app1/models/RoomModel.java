package com.example.guzman.app1.models;

import android.os.Parcel;
import android.os.Parcelable;

public class RoomModel implements Parcelable {
    String roomName;
    String roomLikes;
    String roomPrice;
    String image;



    public RoomModel() {
    }

    protected RoomModel(Parcel in) {
        roomName = in.readString();
        roomLikes = in.readString();
        roomPrice = in.readString();
        image = in.readString();

    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomLikes() {
        return roomLikes;
    }

    public void setRoomLikes(String roomLikes) {
        this.roomLikes = roomLikes;
    }

    public String getRoomPrice() {
        return roomPrice;
    }

    public void setRoomPrice(String roomPrice) {
        this.roomPrice = roomPrice;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }



    public static Creator<RoomModel> getCREATOR() {
        return CREATOR;
    }

    public static void setCREATOR(Creator<RoomModel> CREATOR) {
        RoomModel.CREATOR = CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(roomName);
        dest.writeString(roomLikes);
        dest.writeString(roomPrice);

        dest.writeString(image);
    }


    public static Creator<RoomModel> CREATOR = new Creator<RoomModel>() {
        @Override
        public RoomModel createFromParcel(Parcel in) {
            return new RoomModel(in);
        }

        @Override
        public RoomModel[] newArray(int size) {
            return new RoomModel[size];
        }
    };

}
