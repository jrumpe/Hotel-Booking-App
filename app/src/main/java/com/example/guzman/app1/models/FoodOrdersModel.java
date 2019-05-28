package com.example.guzman.app1.models;

public class FoodOrdersModel {

    String useremail;
    String userphone;
    String foodname;
    String amount;
    String status;

    public FoodOrdersModel() {
    }

    public FoodOrdersModel(String useremail, String userphone, String foodname, String amount, String status) {
        this.useremail = useremail;
        this.userphone = userphone;
        this.foodname = foodname;
        this.amount = amount;
        this.status = status;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public String getUserphone() {
        return userphone;
    }

    public void setUserphone(String userphone) {
        this.userphone = userphone;
    }

    public String getFoodname() {
        return foodname;
    }

    public void setFoodname(String foodname) {
        this.foodname = foodname;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
