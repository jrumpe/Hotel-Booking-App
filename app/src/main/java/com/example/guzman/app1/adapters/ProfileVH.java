package com.example.guzman.app1.adapters;

import android.widget.TextView;

public class ProfileVH {
    public TextView txtUserEmail;
    public TextView txtUserPhone;

    public ProfileVH() {
    }

    public ProfileVH(TextView txtUserEmail, TextView txtUserPhone) {
        this.txtUserEmail = txtUserEmail;
        this.txtUserPhone = txtUserPhone;
    }

    public TextView getTxtUserEmail() {
        return txtUserEmail;
    }

    public void setTxtUserEmail(TextView txtUserEmail) {
        this.txtUserEmail = txtUserEmail;
    }

    public TextView getTxtUserPhone() {
        return txtUserPhone;
    }

    public void setTxtUserPhone(TextView txtUserPhone) {
        this.txtUserPhone = txtUserPhone;
    }
}
