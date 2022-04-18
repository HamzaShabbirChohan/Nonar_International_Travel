package com.example.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.Adapter.DepartureDateAdpter;
import com.example.Model.AgentModel;
import com.example.Model.DataModel;
import com.example.nonarinternationaltravel.R;
import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DeperatureDate extends AppCompatActivity {
    RecyclerView recyclerView;
   AgentModel dataNewAgentmodel;
    ArrayList<AgentModel> dataNewAgentmodelArrayList;
    FloatingActionButton floatingActionButton;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    SpinKitView spinKitView;
    DepartureDateAdpter departureDateAdpter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deperature_date);

        spinKitView = findViewById(R.id.spin_kitdeparture);
        Sprite doubleBounce = new DoubleBounce();
        spinKitView.setIndeterminateDrawable(doubleBounce);

        Initilization();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DeperatureDate.this, AddNewAgent.class);
                startActivity(intent);
            }
        });

        getDateFromFirebase();





    }
    public void Initilization()
    {
        recyclerView=findViewById(R.id.mainrecycler);
        dataNewAgentmodel =new AgentModel();
        floatingActionButton=findViewById(R.id.feb);
        firebaseDatabase=FirebaseDatabase.getInstance();
        dataNewAgentmodelArrayList =new ArrayList<>();
        databaseReference=firebaseDatabase.getReference().child("Agent2");
        firebaseAuth=FirebaseAuth.getInstance();
        recyclerView.setLayoutManager(new LinearLayoutManager(DeperatureDate.this));
    }
    private void getDateFromFirebase(){


        spinKitView.setVisibility(View.VISIBLE);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                if(!dataNewAgentmodelArrayList.isEmpty()){
                    dataNewAgentmodelArrayList.clear();
                }
                for(DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    dataNewAgentmodel =dataSnapshot.getValue(AgentModel.class);
                    dataNewAgentmodelArrayList.add(dataNewAgentmodel);
                }
                departureDateAdpter=new DepartureDateAdpter(DeperatureDate.this, dataNewAgentmodelArrayList);
                recyclerView.setLayoutManager(new LinearLayoutManager(DeperatureDate.this));
                recyclerView.setAdapter(departureDateAdpter);
                departureDateAdpter.notifyDataSetChanged();
                spinKitView.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DeperatureDate.this, "Error", Toast.LENGTH_SHORT).show();


            }

        });
    }

}