package com.moderna.attempt4otpauthentication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    public CardView registerButton;
    public CardView signInButton;
    public CardView phoneAuthButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        registerButton = findViewById(R.id.register);
        signInButton = findViewById(R.id.signIn);
//        String serverURL = "http://192.168.0.8:3000/";
        String serverURL = "https://otp-authentication1.herokuapp.com/";

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToRegisterActivity(serverURL);
            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToLogInActivity(serverURL);
            }
        });
    }

    private void goToLogInActivity(String serverURL) {
        Intent intent = new Intent(MainActivity.this, LogInActivity.class);
        intent.putExtra("serverURL", serverURL);
        startActivity(intent);
    }

    public void goToRegisterActivity(String serverURL) {
        Intent intent = new Intent(MainActivity.this, RegisterUserActivity.class);
        intent.putExtra("serverURL", serverURL);
        startActivity(intent);
    }
}