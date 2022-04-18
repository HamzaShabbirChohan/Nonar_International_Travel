
package com.example.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Model.AgentModel;
import com.example.Model.DataModel;
import com.example.nonarinternationaltravel.R;
import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class AddFlightActivity extends AppCompatActivity {
    EditText name,refno,airline,sector,description,debit,cradit;
    LinearLayout enter ,logout;
    TextView date,balance;
    Spinner spinner;
    DataModel dataModel;
    FirebaseDatabase firebaseDatabase;
    FirebaseDatabase firebaseDatabase2;
    DatabaseReference databaseReference2;
    FirebaseAuth firebaseAuth2;
    FirebaseAuth firebaseAuth;
    AgentModel newAgentmodel;
    SpinKitView spinKitView;

    DatabaseReference databaseReference;
    ArrayList<AgentModel> newAgentmodelArrayList;
    boolean isUpdate = false;
    int position;
    final Calendar myCalendar= Calendar.getInstance();

    private CardView toolBar;
    private ImageView backBtn;
    private AgentModel selectedAgentModel;

    private Double updatedCredit=0.0,updatedDebit=0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agentdataenter);
        Initilization();
        Sprite doubleBounce = new DoubleBounce();
        spinKitView.setIndeterminateDrawable(doubleBounce);
      //  Validation();

      //  String[] items = new String[]{"Active", "Absent", "Halfday"};


        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds:snapshot.getChildren())
                {
                    newAgentmodel =new AgentModel();
                    newAgentmodel =ds.getValue(AgentModel.class);
                    newAgentmodelArrayList.add(newAgentmodel);
                    Log.d("agentmodelist",newAgentmodel.toString());

                    initSpinner();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AddFlightActivity.this, FlightsMainActivity.class);
                startActivity(intent);
            }
        });
        DatePickerDialog.OnDateSetListener date1 =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel();
            }
        };
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddFlightActivity.this,date1,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        if(getIntent().getSerializableExtra("loadd")!=null){
            dataModel= (DataModel) getIntent().getSerializableExtra("loadd");
            date.setText(dataModel.getDate());
            //transaction,refno,airline,sector,debit,cradit;
            name.setText(dataModel.getName());
            refno.setText(dataModel.getRefno());
            airline.setText(dataModel.getAirline());
            sector.setText(dataModel.getSector());
            description.setText(dataModel.getDescription());
            debit.setText(String.valueOf(dataModel.getDebit()));
            cradit.setText(String.valueOf(dataModel.getCradit()));
            isUpdate = true;
            position = getIntent().getIntExtra("poss",0);
        }

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (firebaseAuth.getCurrentUser() != null)
                    firebaseAuth.signOut();
                Intent intent = new Intent(AddFlightActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        });

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinKitView.setVisibility(View.VISIBLE);
                if(Validation())
                {
                    String date1=date.getText().toString();
                    String trans= name.getText().toString();
                    String ref=refno.getText().toString();
                    String airl=airline.getText().toString();
                    String sectr=sector.getText().toString();
                    String desc=description.getText().toString();
                    String debitValue=debit.getText().toString();
                    String craditValue=cradit.getText().toString();

                    dataModel.setDate(date1);
                    dataModel.setName(trans);
                    dataModel.setRefno(ref);
                    dataModel.setAirline(airl);
                    dataModel.setSector(sectr);
                    dataModel.setDescription(desc);



                    //   Double blnc=Double.parseDouble(blanceValue);

                    if (isUpdate){



                         updatedDebit=Double.parseDouble(debitValue);

                         updatedCredit =Double.parseDouble(craditValue);

                         updatedDebit = updatedDebit-dataModel.getDebit();
                         updatedCredit = updatedCredit-dataModel.getCradit();

                        Double dbt=Double.parseDouble(debitValue);
                        dataModel.setDebit(dbt);
                        Double cradt=Double.parseDouble(craditValue);
                        dataModel.setCradit(cradt);
                        Double blanceValue=(dbt-cradt);
                        dataModel.setBalance(blanceValue);


                        updateDatatoFirebase();

                    }else {
                        Double dbt=Double.parseDouble(debitValue);
                        dataModel.setDebit(dbt);
                        Double cradt=Double.parseDouble(craditValue);
                        dataModel.setCradit(cradt);
                        Double blanceValue=(dbt-cradt);
                        dataModel.setBalance(blanceValue);

                        savedatatofirebase();
                    }

                    spinKitView.setVisibility(View.GONE);

                }
                else {
                    Toast.makeText(AddFlightActivity.this, "Data cannot be uploaded on Real time DataBase", Toast.LENGTH_SHORT).show();
                }
            }
        });







    }

    private void initSpinner() {
        ArrayList<String> agentNames = new ArrayList<String>();

        for(AgentModel newAgentmodel : newAgentmodelArrayList){
            agentNames.add(newAgentmodel.getName());
        }
        Log.d("agentsname",agentNames.toString());


        
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, agentNames);
            spinner.setAdapter(adapter);

            if(isUpdate){
                for(int i=0;i<newAgentmodelArrayList.size();i++){
                    if(newAgentmodelArrayList.get(i).getUid().equals(dataModel.getAgentId())){
                        spinner.setSelection(i);
                    }
                }

            }

    }

    public void Initilization()
    {
        spinKitView=findViewById(R.id.spin_kitflight);
        toolBar = findViewById(R.id.toolbar);
        backBtn = findViewById(R.id.back_btn);
        spinner=findViewById(R.id.spinner1);
        toolBar.setBackgroundResource(R.drawable.bottom_corer_round);
        date=findViewById(R.id.datadate);
        name =findViewById(R.id.datatransaction);
        refno=findViewById(R.id.datarefno);
        airline=findViewById(R.id.dataairline);
        sector=findViewById(R.id.datasector);
        description=findViewById(R.id.datasescription);
        debit=findViewById(R.id.datadebit);
        cradit=findViewById(R.id.datacradit);
        balance=findViewById(R.id.databalance);
        enter=findViewById(R.id.upload_btn);
        logout=findViewById(R.id.logout_btn);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference().child("AgentData");
        dataModel=new DataModel();
        newAgentmodelArrayList =new ArrayList<>();
        firebaseAuth2=FirebaseAuth.getInstance();
        firebaseDatabase2=FirebaseDatabase.getInstance();
        databaseReference2=firebaseDatabase.getReference().child("Agent2");

        selectedAgentModel = new AgentModel();

        Log.d("testde","workinh");


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Log.d("datamodela","chjamginf");
                // Get the value selected by the user
                // e.g. to store it as a field or immediately call a method
                dataModel.setAgentName(newAgentmodelArrayList.get(position).getName());
                dataModel.setAgentId(newAgentmodelArrayList.get(position).getUid());
         //       if(dataModel.getUid().equals(dataModel.getAgentId()))

                selectedAgentModel = newAgentmodelArrayList.get(position);

                Log.d("datamodela",dataModel.toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void updateDatatoFirebase() {

        databaseReference.child(dataModel.getUid()).setValue(dataModel).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

                selectedAgentModel.setCradit((int) (selectedAgentModel.getCradit()+updatedCredit));
                selectedAgentModel.setDebit((int) (selectedAgentModel.getDebit()+updatedDebit));

                databaseReference2.child(selectedAgentModel.getUid()).setValue(selectedAgentModel);


                Toast.makeText(AddFlightActivity.this, "Successfully Updated", Toast.LENGTH_SHORT).show();
                finish();

            }
        }).addOnFailureListener(new OnFailureListener() {

            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }


    public void savedatatofirebase()
    {
        String upload=firebaseAuth.getCurrentUser().getUid();
        String push=databaseReference.push().getKey();
        dataModel.setUplodedby(upload);
        dataModel.setUid(push);
        databaseReference.child(push).setValue(dataModel).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

                selectedAgentModel.setCradit((int) (selectedAgentModel.getCradit()+dataModel.getCradit()));
                selectedAgentModel.setDebit((int) (selectedAgentModel.getDebit()+dataModel.getDebit()));

                databaseReference2.child(selectedAgentModel.getUid()).setValue(selectedAgentModel);


                Toast.makeText(AddFlightActivity.this, "Successfully Added", Toast.LENGTH_SHORT).show();
                finish();

            }
        }).addOnFailureListener(new OnFailureListener() {

            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
    public boolean Validation()
    {
        if (date.getText().toString().isEmpty())
        {
            date.setError("Enter your date");
            return false;
        }
        if (name.getText().toString().isEmpty())
        {
            name.setError("Enter your date");
            return false;
        }
        if (refno.getText().toString().isEmpty())
        {
            refno.setError("Enter your date");
            return false;
        }
        if (airline.getText().toString().isEmpty())
        {
            airline.setError("Enter your date");
            return false;
        }
        if (description.getText().toString().isEmpty())
        {
            description.setError("Enter your description");
            return false;
        }
        if (sector.getText().toString().isEmpty())
        {
            sector.setError("Enter your date");
            return false;
        }
        if (debit.getText().toString().isEmpty())
        {
            debit.setError("Enter your date");
            return false;
        }
        if (cradit.getText().toString().isEmpty())
        {
            cradit.setError("Enter your date");
            return false;
        }

        return true;
    }

    private void updateLabel(){
        String myFormat="dd-MM-yy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        date.setText(dateFormat.format(myCalendar.getTime()));
    }
}

