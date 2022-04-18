package com.example.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.nonarinternationaltravel.MainActivity;
import com.example.nonarinternationaltravel.R;
import com.example.nonarinternationaltravel.Selection;
import com.google.firebase.auth.FirebaseAuth;

import io.paperdb.Paper;

public class SplashScreen extends AppCompatActivity {

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        firebaseAuth = FirebaseAuth.getInstance();

        Paper.init(SplashScreen.this);
        final String temp = Paper.book().read("active");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (temp != null) {
                    if (temp.equals("user")) {
                        startActivity(new Intent(SplashScreen.this, SingelAgentsFlights.class));
                        finish();
                    }
                    if (temp.equals("admin")) {
                        startActivity(new Intent(SplashScreen.this, FlightsMainActivity.class));
                        finish();
                    }
                } else {
                    startActivity(new Intent(SplashScreen.this, LoginActivity.class));
                    finish();
                }

            }
        }, 2000); //time in milliseconds


    }
}