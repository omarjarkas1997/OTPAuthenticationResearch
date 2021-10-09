package com.moderna.attempt4otpauthentication;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface MyAPICall {

    // 'https://jsonplaceholder.typicode.com'

    @GET("/todos/1")
    Call<MessageResponse> getData();

    @GET("/api/all-users")
    Call<List<UsersDataModel>> getAllUsers();

    @POST("/api/register")
    Call<UsersDataModel> registerUser(@Body UsersDataModel newUser);


    @POST("/api/login")
    Call<UsersDataModel> logInUser(@Body UsersDataModel newUser);


    @POST("/api/verifyOTP")
    Call<MessageResponse> verifyOTP(@Body MessageResponse otpToken);

}
