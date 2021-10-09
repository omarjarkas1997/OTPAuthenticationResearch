package com.moderna.attempt4otpauthentication;

import com.google.gson.annotations.SerializedName;

public class MessageResponse {

    private String msg;
    private int status;
    private int optToken;
    private String phoneNumber;

    public MessageResponse(int optToken, String phoneNumber) {
        this.optToken = optToken;
        this.phoneNumber = phoneNumber;
    }

    public MessageResponse(int optToken) {
        this.optToken = optToken;
    }

    public MessageResponse(String msg, int status, int optToken) {
        this.msg = msg;
        this.status = status;
        this.optToken = optToken;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getOptToken() {
        return optToken;
    }

    public void setOptToken(int optToken) {
        this.optToken = optToken;
    }
}
