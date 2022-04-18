package com.example.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.Model.AgentModel;
import com.example.Model.DataModel;
import com.example.nonarinternationaltravel.R;
import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddNewAgent extends AppCompatActivity {

    EditText name,email,password;
    ImageView back;
    SpinKitView spinKitView;
            LinearLayout create;
            FirebaseAuth firebaseAuth;
            FirebaseDatabase firebaseDatabase;
            DatabaseReference databaseReference;
            AgentModel newAgentmodel =new AgentModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_agent);
        Initilization();
        Sprite doubleBounce = new DoubleBounce();
        spinKitView.setIndeterminateDrawable(doubleBounce);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Validation())
                spinKitView.setVisibility(View.VISIBLE);
                create.setVisibility(View.GONE);
                {
                    String email1=email.getText().toString().trim();
                    String pas=password.getText().toString().trim();
                    firebaseAuth.createUserWithEmailAndPassword(email1,pas).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(!task.isSuccessful())
                            {
                                Toast.makeText(AddNewAgent.this, "Error", Toast.LENGTH_SHORT).show();
                            }

                            else {
                                String name1=name.getText().toString();
                                String emaill=email.getText().toString();
                                String pasword=password.getText().toString();
                                String cradt="0";
                                String debt="0";
                                String balanc="0";

                                newAgentmodel.setName(name1);
                                newAgentmodel.setEmail(emaill);
                                newAgentmodel.setPassword(pasword);
                                newAgentmodel.setCradit(0);
                                newAgentmodel.setDebit(0);
                                newAgentmodel.setBalance(0);

                                savedatatofirebase();

                                spinKitView.setVisibility(View.GONE);
                                create.setVisibility(View.VISIBLE);

                            }
                        }
                    });

                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AddNewAgent.this,DeperatureDate.class);
                startActivity(intent);
            }
        });


    }

    public void Initilization()
    {
        name=findViewById(R.id.addnewagentname);
        email=findViewById(R.id.agentnewemail);
        password=findViewById(R.id.agentnewpassword);
        create=findViewById(R.id.create_btn);
        spinKitView=findViewById(R.id.spin_kitnew);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference().child("Agent2");
        back=findViewById(R.id.back_btn);

    }
    public boolean Validation()
    {
        if(name.getText().toString().isEmpty())
        {
            name.setError("enter your name");
            return false;
        }

        if (email.getText().toString().isEmpty())
        {
            email.setError("enter your email");
            return false;
    }
        if(password.getText().toString().isEmpty())
        {
            password.setError("enter your password");
        }
    return true;
    }
    public void savedatatofirebase()
    {
        String id=firebaseAuth.getUid();
        newAgentmodel.setUid(id);


        databaseReference.child(id).setValue(newAgentmodel).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(AddNewAgent.this, "successfully add", Toast.LENGTH_SHORT).show();

                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddNewAgent.this, "error", Toast.LENGTH_SHORT).show();


            }
        });
    }
}