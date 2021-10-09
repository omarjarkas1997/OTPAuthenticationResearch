package com.moderna.attempt4otpauthentication;

import com.google.gson.annotations.SerializedName;

public class UsersDataModel {

    private String email;
    private String password;
    private String phoneNumber;
    private int authStatus;

    public UsersDataModel(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public UsersDataModel(String email, String password, String phoneNumber) {
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(int authStatus) {
        this.authStatus = authStatus;
    }
}
