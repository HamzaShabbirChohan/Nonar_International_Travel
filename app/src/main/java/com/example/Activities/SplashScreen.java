package com.example.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.nonarinternationaltravel.MainActivity;
import com.example.nonarinternationaltravel.R;
import com.example.nonarinternationaltravel.Selection;
import com.google.firebase.auth.FirebaseAuth;

public class SplashScreen extends AppCompatActivity {

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        firebaseAuth=FirebaseAuth.getInstance();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(firebaseAuth.getCurrentUser()!=null)
                {
                    startActivity(new Intent(SplashScreen.this, FlightsMainActivity.class));
                }else {
                    startActivity(new Intent(SplashScreen.this, LoginActivity.class));

                }
                finish();
            }
        }, 2000); //time in milliseconds





    }
}