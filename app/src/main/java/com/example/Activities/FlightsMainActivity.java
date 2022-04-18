package com.example.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.Adapter.AgentAdapter;
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

public class FlightsMainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DataModel dataModel;
    ArrayList<DataModel> dataModelArrayList;
    FloatingActionButton floatingActionButton,floatingActionButton2;
    AgentAdapter dataAdapter;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    SpinKitView spinKitView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agentmain);

        Intialization();
        spinKitView = findViewById(R.id.spin_kit);
        Sprite doubleBounce = new DoubleBounce();
        spinKitView.setIndeterminateDrawable(doubleBounce);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FlightsMainActivity.this, AddFlightActivity.class);
                startActivity(intent);
            }
        });

        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FlightsMainActivity.this, DeperatureDate.class);
                startActivity(intent);
            }
        });

        //getDateFromFirebase();
        Log.d("test","result");
    }

    public  void Intialization()
    {
        recyclerView=findViewById(R.id.mainrecycler);
        dataModel=new DataModel();
        floatingActionButton=findViewById(R.id.feb);
        floatingActionButton2=findViewById(R.id.feb2);

        firebaseDatabase=FirebaseDatabase.getInstance();
        dataModelArrayList=new ArrayList<>();
        databaseReference=firebaseDatabase.getReference().child("AgentData");
        firebaseAuth=FirebaseAuth.getInstance();
        recyclerView.setLayoutManager(new LinearLayoutManager(FlightsMainActivity.this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDateFromFirebase();
    }

    private void getDateFromFirebase(){

        spinKitView.setVisibility(View.VISIBLE);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!dataModelArrayList.isEmpty()){
                    dataModelArrayList.clear();
                }
                for(DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    dataModel=dataSnapshot.getValue(DataModel.class);
                  //  if (firebaseAuth.getCurrentUser().getUid().equals( dataModel.getUplodedby())) {
                        dataModelArrayList.add(dataModel);
                   // }

                    Log.d("testdebug",dataModel.toString());
                }
                dataAdapter=new AgentAdapter(dataModelArrayList, FlightsMainActivity.this);
                recyclerView.setLayoutManager(new LinearLayoutManager(FlightsMainActivity.this));
                recyclerView.setAdapter(dataAdapter);

                dataAdapter.notifyDataSetChanged();
                spinKitView.setVisibility(View.GONE);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}