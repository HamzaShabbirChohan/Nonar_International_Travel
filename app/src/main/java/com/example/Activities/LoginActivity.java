package com.example.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.Model.AgentModel;
import com.example.nonarinternationaltravel.R;
import com.example.nonarinternationaltravel.Selection;
import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

   EditText email,password;
    LinearLayout login;
    ImageView profile;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    SpinKitView spinKitView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email=findViewById(R.id.datadate);
        password=findViewById(R.id.password);
        login=findViewById(R.id.upload_btn);
        spinKitView=findViewById(R.id.spin_kit);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=FirebaseDatabase.getInstance().getReference();
      //  Validation();




        ProgressBar progressBar = (ProgressBar)findViewById(R.id.spin_kit);
        Sprite doubleBounce = new DoubleBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  spinKitView;

                if(Validation())
                {

                    progressBar.setVisibility(View.VISIBLE);
                    login.setVisibility(View.GONE);
                    String emaill= email.getText().toString().trim();
                  String pass=  password.getText().toString().trim();
                    firebaseAuth.signInWithEmailAndPassword(emaill,pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            progressBar.setVisibility(View.GONE);
                            login.setVisibility(View.VISIBLE);

                            if(emaill.toLowerCase().equals("azeem12@gmail.com")) {
                                Intent intent = new Intent(LoginActivity.this, FlightsMainActivity.class);
                                startActivity(intent);
                            }else{
                                databaseReference.child("Agent2").child(firebaseAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        AgentModel agentModel = new AgentModel();
                                        agentModel = snapshot.getValue(AgentModel.class);
                                        Intent intent = new Intent(LoginActivity.this, SingelAgentsFlights.class);
                                        intent.putExtra("singleagent",agentModel);
                                        startActivity(intent);
                                        finish();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressBar.setVisibility(View.GONE);
                            login.setVisibility(View.VISIBLE);
                            Toast.makeText(LoginActivity.this, "validation unsuccessful", Toast.LENGTH_SHORT).show();


                        }
                    });
                }

            }
        });



    }

   public boolean Validation()
    {

        if(email.getText().toString().isEmpty())
        {
            email.setError("please enter your email");
            return false;
        }
        if(password.getText().toString().isEmpty())
        {
            password.setError("please enter your password");
            return false;
        }

        return true;
    }
}