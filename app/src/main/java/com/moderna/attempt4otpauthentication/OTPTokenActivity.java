package com.moderna.attempt4otpauthentication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OTPTokenActivity extends AppCompatActivity {

    private static final String TAG = "OTPTokenActivity";

    private TextInputEditText otpInput;
    public CardView sendOTPButton;
    private TextView test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otptoken);


        otpInput = findViewById(R.id.otpInput);
        sendOTPButton = findViewById(R.id.sendOTPButton);
        test = findViewById(R.id.sub_name);
        Intent intent = getIntent();
        String serverURL = intent.getStringExtra("serverURL");
        String phoneNumber = intent.getStringExtra("phoneNumber");

        sendOTPButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                test.setText(otpInput.getText().toString());
                verifyOTPToken(serverURL, otpInput.getText().toString(), phoneNumber);
            }
        });
    }

    private void verifyOTPToken(String serverURL, String otpInput, String phoneNumber) {
        // Retrofit builder
        Retrofit retrofit = new Retrofit.Builder().baseUrl(serverURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // instance for interface
        MyAPICall myAPICall = retrofit.create(MyAPICall.class);
        MessageResponse otpToken = new MessageResponse(Integer.parseInt(otpInput), phoneNumber);
        Call<MessageResponse> optCall = myAPICall.verifyOTP(otpToken);

        optCall.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if(response.body() != null) {
                    // Get the data in text view
                    if(response.body().getStatus() == 200){
                        Toast.makeText(OTPTokenActivity.this, "Successful Authentication", Toast.LENGTH_SHORT).show();
                        test.setText(""+response.body().getMsg());
                        goToHomePage();

                    } else {
                        Toast.makeText(OTPTokenActivity.this, "UnSuccessful Authentication "+response.body(), Toast.LENGTH_SHORT).show();
                        test.setText(""+response.body().getMsg());
                    }

                } else {
                    test.setText("check the connection4");
                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                Log.d(TAG, "omar Failure");
                Log.d(TAG,"check the connection5"+ t.toString());
                test.setText("check the connection5"+ t.toString());
            }
        });
    }

    private void goToHomePage() {
        Intent intent = new Intent(OTPTokenActivity.this, HomePage.class);
        startActivity(intent);
    }

}