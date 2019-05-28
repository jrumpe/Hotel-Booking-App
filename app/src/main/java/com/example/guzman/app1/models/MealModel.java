package com.example.guzman.app1.models;

import android.os.Parcel;
import android.os.Parcelable;

public class MealModel implements Parcelable {
    String foodName;
    String foodLikes;
    String foodPrice;
    String image;



    public MealModel(Parcel in) {
        foodName = in.readString();
        foodLikes = in.readString();
        foodPrice = in.readString();
        image = in.readString();
    }

    public MealModel() {

    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodLikes() {
        return foodLikes;
    }

    public void setFoodLikes(String foodLikes) {
        this.foodLikes = foodLikes;
    }

    public String getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(String foodPrice) {
        this.foodPrice = foodPrice;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    public static final Creator<MealModel> CREATOR = new Creator<MealModel>() {
        @Override
        public MealModel createFromParcel(Parcel in) {
            return new MealModel(in);
        }

        @Override
        public MealModel[] newArray(int size) {
            return new MealModel[size];
        }
    };



    public static Creator<MealModel> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(foodName);
        dest.writeString(foodLikes);
        dest.writeString(foodPrice);
        dest.writeString(image);
    }
}
