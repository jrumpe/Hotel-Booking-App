package com.example.guzman.app1;

import android.content.Context;

class Ip {

    public Ip(Context context) {

    }

    public String Hotels() {
//        return server Url
       return "";
    }

    public String bookroom() {

        String b = "http://192.168.43.142/AndroidApp1/bookroom.php";

        return b;
    }

    public String ordermeals() {

        String b = "http://192.168.43.142/AndroidApp1/orderfood.php";

        return b;
    }
}
