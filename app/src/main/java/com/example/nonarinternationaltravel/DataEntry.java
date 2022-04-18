package com.example.nonarinternationaltravel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Activities.LoginActivity;
import com.example.Model.DataModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DataEntry extends AppCompatActivity {

    EditText name,refno,airline,sector,debit,cradit;
    LinearLayout enter ,logout;
    TextView date,balance;

    DataModel dataModel;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    boolean isUpdate = false;
    int position;
    final Calendar myCalendar= Calendar.getInstance();

    private CardView toolBar;
    private ImageView backBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_entry);

        Initilization();
        Validation();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DataEntry.this,MainActivity.class);
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
                new DatePickerDialog(DataEntry.this,date1,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        if(getIntent().getSerializableExtra("load")!=null){
            dataModel= (DataModel) getIntent().getSerializableExtra("load");
            date.setText(dataModel.getDate());
            //transaction,refno,airline,sector,debit,cradit;
            name.setText(dataModel.getName());
            refno.setText(dataModel.getRefno());
            airline.setText(dataModel.getAirline());
            sector.setText(dataModel.getSector());
            debit.setText(String.valueOf(dataModel.getDebit()));
            cradit.setText(String.valueOf(dataModel.getCradit()));
             isUpdate = true;
            position = getIntent().getIntExtra("pos",0);
        }

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (firebaseAuth.getCurrentUser() != null)
                    firebaseAuth.signOut();
                Intent intent = new Intent(DataEntry.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Validation())
                {
                    String date1=date.getText().toString();
                    String trans= name.getText().toString();
                    String ref=refno.getText().toString();
                    String airl=airline.getText().toString();
                    String sectr=sector.getText().toString();
                    String debitValue=debit.getText().toString();
                    String craditValue=cradit.getText().toString();

                    dataModel.setDate(date1);
                    dataModel.setName(trans);
                    dataModel.setRefno(ref);
                    dataModel.setAirline(airl);
                    dataModel.setSector(sectr);

                    Double dbt=Double.parseDouble(debitValue);
                    dataModel.setDebit(dbt);
                    Double cradt=Double.parseDouble(craditValue);
                    dataModel.setCradit(cradt);
                      Double blanceValue=(dbt-cradt);

                    //   Double blnc=Double.parseDouble(blanceValue);
                    dataModel.setBalance(blanceValue);
                    
                    if (isUpdate){
                        updateDatatoFirebase();
                    }else {
                        savedatatofirebase();
                    }


                }
                else {
                    Toast.makeText(DataEntry.this, "Data cannot be uploaded on Real time DataBase", Toast.LENGTH_SHORT).show();
                }
            }
        });




    }

    private void updateDatatoFirebase() {

        databaseReference.child(dataModel.getUid()).setValue(dataModel).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {


                Intent intent=new Intent(DataEntry.this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(DataEntry.this, "Successfully Updated", Toast.LENGTH_SHORT).show();
                finish();

            }
        }).addOnFailureListener(new OnFailureListener() {

            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    public void Initilization()
    {
        toolBar = findViewById(R.id.toolbar);
        backBtn = findViewById(R.id.back_btn);
        toolBar.setBackgroundResource(R.drawable.bottom_corer_round);
        date=findViewById(R.id.datadate);
        name =findViewById(R.id.datatransaction);
        refno=findViewById(R.id.datarefno);
        airline=findViewById(R.id.dataairline);
        sector=findViewById(R.id.datasector);
        debit=findViewById(R.id.datadebit);
        cradit=findViewById(R.id.datacradit);
        balance=findViewById(R.id.databalance);
        enter=findViewById(R.id.upload_btn);
        logout=findViewById(R.id.logout_btn);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference().child("UsersData");
        dataModel=new DataModel();
    }

    public void savedatatofirebase()
    {
        String push=databaseReference.push().getKey();
        String upload=firebaseAuth.getCurrentUser().getUid();
        dataModel.setUplodedby(upload);
        dataModel.setUid(push);
        databaseReference.child(push).setValue(dataModel).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {


                Intent intent=new Intent(DataEntry.this, MainActivity.class);
               startActivity(intent);
               Toast.makeText(DataEntry.this, "Successfully signup", Toast.LENGTH_SHORT).show();
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