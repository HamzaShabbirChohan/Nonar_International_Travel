package com.example.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.Adapter.AgentAdapter;
import com.example.Model.AgentModel;
import com.example.Model.DataModel;
import com.example.Model.Utlis;
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

public class SingelAgentsFlights extends AppCompatActivity {

    RecyclerView recyclerView;
    DataModel dataModel;
    ArrayList<DataModel> dataModelArrayList;

    AgentAdapter dataAdapter;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    SpinKitView spinKitView;
    TextView cradit,debit,balance;
    AgentModel agentModel=new AgentModel();

    boolean isSingleAgent = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singel_agents_flights);

        Intialization();
        spinKitView = findViewById(R.id.spin_kit);
        Sprite doubleBounce = new DoubleBounce();
        spinKitView.setIndeterminateDrawable(doubleBounce);


        if(getIntent().getSerializableExtra("agent")!=null){
            agentModel = (AgentModel) getIntent().getSerializableExtra("agent");

        }

        if(getIntent().getSerializableExtra("singleagent")!=null){
            agentModel = (AgentModel) getIntent().getSerializableExtra("singleagent");

            isSingleAgent =true;
        }

        cradit.setText(String.valueOf(agentModel.getCradit()));
        debit.setText(String.valueOf(agentModel.getDebit()));
        balance.setText(String.valueOf(Utlis.getBalane(agentModel.getDebit(),agentModel.getCradit())));

        Log.d("agentdebug",agentModel.toString());

/*
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SingelAgentsFlights.this, AddFlightActivity.class);
                startActivity(intent);
            }
        });
*/

/*
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SingelAgentsFlights.this, DeperatureDate.class);
                startActivity(intent);
            }
        });
*/

        getDateFromFirebase();
    }

    public  void Intialization()
    {
        recyclerView=findViewById(R.id.mainrecycler);
        dataModel=new DataModel();

        cradit=findViewById(R.id.singelitem2cradittextview);
        debit=findViewById(R.id.singelitem2debittextview);
        balance=findViewById(R.id.singelitem2balancetextview);
        firebaseDatabase=FirebaseDatabase.getInstance();
        dataModelArrayList=new ArrayList<>();
        databaseReference=firebaseDatabase.getReference().child("AgentData");
        firebaseAuth=FirebaseAuth.getInstance();
        recyclerView.setLayoutManager(new LinearLayoutManager(SingelAgentsFlights.this));
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
                    Log.d("datadebug",dataModel.toString());
                    if (agentModel.getUid().equals(dataModel.getAgentId())) {
                        dataModelArrayList.add(dataModel);
                    }
                }

               // Log.d("datadebug",dataModelArrayList.toString());
                dataAdapter=new AgentAdapter(dataModelArrayList, SingelAgentsFlights.this,isSingleAgent);
                recyclerView.setLayoutManager(new LinearLayoutManager(SingelAgentsFlights.this));
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