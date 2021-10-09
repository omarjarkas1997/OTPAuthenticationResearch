package com.moderna.attempt4otpauthentication;

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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LogInActivity extends AppCompatActivity {
    private static final String TAG = "LogInActivity";


    public CardView registerButton;
    private TextInputEditText email;
    private TextInputEditText password;

    TextView test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);


        // Getting data from the user's input
        registerButton = findViewById(R.id.registerButton);
        email = findViewById(R.id.emailInput);
        password = findViewById(R.id.passwordInput);

        test = findViewById(R.id.sub_name);
//
        Intent intent = getIntent();
        String serverURL = intent.getStringExtra("serverURL");


        if(isNetworkAvailable()){
            Toast.makeText(this, "The internet is available!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "The internet is not available!", Toast.LENGTH_LONG).show();
        }

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUserPost(serverURL, email.getText().toString(), password.getText().toString());
            }
        });


    }

    public void loginUserPost(String serverURL, String email, String password) {

        // Retrofit builder
        Retrofit retrofit = new Retrofit.Builder().baseUrl(serverURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // instance for interface
        MyAPICall myAPICall = retrofit.create(MyAPICall.class);

        UsersDataModel newUser = new UsersDataModel(email,password);

        Call<UsersDataModel> call = myAPICall.logInUser(newUser);

        call.enqueue(new Callback<UsersDataModel>() {
            @Override
            public void onResponse(Call<UsersDataModel> call, Response<UsersDataModel> response) {
//                goToOTPActivity(serverURL);
                Log.d(TAG,"Congrats, you're authenticated!!");
                test.setText("Congrats, you're authenticated!!" + response.body().getAuthStatus()+" and "+response.body().getPhoneNumber());
                if(response.body().getAuthStatus() == 201){
                    goToOTPActivity(serverURL, response.body().getPhoneNumber());
                } else if(response.body().getAuthStatus() == 401){
                    test.setText("Authentication Failed!");
                }
            }

            @Override
            public void onFailure(Call<UsersDataModel> call, Throwable t) {
                Log.d(TAG, "omar Failure");
                Log.d(TAG,"check the connection2"+ t.toString());
                test.setText("check the connection2"+ t.toString());
            }
        });
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

    private void goToOTPActivity(String serverURL, String phoneNumber) {
        Intent intent = new Intent(LogInActivity.this, OTPTokenActivity.class);
        intent.putExtra("serverURL", serverURL);
        intent.putExtra("phoneNumber",phoneNumber);
        startActivity(intent);
    }
}