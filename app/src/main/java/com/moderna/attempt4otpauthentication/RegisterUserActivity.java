package com.moderna.attempt4otpauthentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterUserActivity extends AppCompatActivity {

    private static final String TAG = "RegisterUserActivity";



    public CardView registerButton;
    private TextInputEditText email;
    private TextInputEditText password;
    private TextInputEditText phoneNumber;

    TextView test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);


        // Getting data from the user's input
        registerButton = findViewById(R.id.registerButton);
        email = findViewById(R.id.emailInput);
        password = findViewById(R.id.passwordInput);
        phoneNumber = findViewById(R.id.phoneNumber);


        test = findViewById(R.id.sub_name);
//
        Intent intent = getIntent();
        String serverURL = intent.getStringExtra("serverURL");


        if(isNetworkAvailable()){
            Toast.makeText(this, "The internet is available!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "The internet is not available!", Toast.LENGTH_LONG).show();
        }



    }
    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;


        if(networkInfo != null && networkInfo.isConnected()){
            isAvailable = true;
        } else {
            Toast.makeText(this, getString(R.string.network_unavailable_message), Toast.LENGTH_LONG).show();
        }
        return isAvailable;
    }

    public void createPost(String serverURL, String email, String password, String phoneNumber) {

        // Retrofit builder
        Retrofit retrofit = new Retrofit.Builder().baseUrl(serverURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // instance for interface
        MyAPICall myAPICall = retrofit.create(MyAPICall.class);

        UsersDataModel newUser = new UsersDataModel(email,password, phoneNumber);
        Call<UsersDataModel> call = myAPICall.registerUser(newUser);

        call.enqueue(new Callback<UsersDataModel>() {
            @Override
            public void onResponse(Call<UsersDataModel> call, Response<UsersDataModel> response) {
                goToOTPActivity(serverURL, phoneNumber);
            }

            @Override
            public void onFailure(Call<UsersDataModel> call, Throwable t) {
                Log.d(TAG, "omar Failure");
                Log.d(TAG,"check the connection2"+ t.toString());
                test.setText("check the connection2"+ t.toString());
            }
        });
    }

    private void goToOTPActivity(String serverURL, String phoneNumber) {
        Intent intent = new Intent(RegisterUserActivity.this, OTPTokenActivity.class);
        intent.putExtra("serverURL", serverURL);
        intent.putExtra("phoneNumber",phoneNumber);
        startActivity(intent);
    }

    public void getUsers(String serverURL){
        // Retrofit builder
        Retrofit retrofit = new Retrofit.Builder().baseUrl(serverURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // instance for interface
        MyAPICall myAPICall = retrofit.create(MyAPICall.class);

        Call<List<UsersDataModel>> call = myAPICall.getAllUsers();


        call.enqueue(new Callback<List<UsersDataModel>>() {
            @Override
            public void onResponse(Call<List<UsersDataModel>> call, Response<List<UsersDataModel>> response) {
                if(response.body() != null) {
                    // Get the data in text view
                    String json = "Title= "+response.body().get(0).getEmail();
                    Log.d(TAG, "omar"+json);
                    test.setText(json);

                } else {
                    test.setText("check the connection");
                }
            }

            @Override
            public void onFailure(Call<List<UsersDataModel>> call, Throwable t) {
                Log.d(TAG, "omar Failure");
                Log.d(TAG,"check the connection2"+ t.toString());
                test.setText("check the connection2"+ t.toString());
            }
        });
    }



}