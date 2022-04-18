package com.example.nonarinternationaltravel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.Activities.AddNewAgent;
import com.example.Activities.FlightsMainActivity;

public class Selection extends AppCompatActivity {
    LinearLayout agent,user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);
        agent=findViewById(R.id.agent_btn);
        user=findViewById(R.id.user_btn);

        agent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Selection.this, FlightsMainActivity.class);
                startActivity(intent);
            }
        });

        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Selection.this, AddNewAgent.class);
                startActivity(intent);

            }
        });
    }
}