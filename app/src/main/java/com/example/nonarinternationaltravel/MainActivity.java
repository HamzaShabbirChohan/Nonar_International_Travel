package com.example.nonarinternationaltravel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.Model.DataModel;
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

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DataModel dataModel;
    ArrayList<DataModel>dataModelArrayList;
    FloatingActionButton floatingActionButton;
    DataAdapter dataAdapter;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    SpinKitView spinKitView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intialization();
        spinKitView = findViewById(R.id.spin_kit);
        Sprite doubleBounce = new DoubleBounce();
        spinKitView.setIndeterminateDrawable(doubleBounce);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,DataEntry.class);
                startActivity(intent);
            }
        });

        getDateFromFirebase();
    }
    public  void Intialization()
    {
        recyclerView=findViewById(R.id.mainrecycler);
        dataModel=new DataModel();
        floatingActionButton=findViewById(R.id.feb);
        firebaseDatabase=FirebaseDatabase.getInstance();
        spinKitView=findViewById(R.id.spin_kit);
        dataModelArrayList=new ArrayList<>();
        firebaseAuth=FirebaseAuth.getInstance();
        databaseReference=firebaseDatabase.getReference().child("UsersData");
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

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
                    if (firebaseAuth.getCurrentUser().getUid().equals( dataModel.getUplodedby())) {
                        dataModelArrayList.add(dataModel);
                    }

                }

                dataAdapter=new DataAdapter(dataModelArrayList, MainActivity.this);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
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