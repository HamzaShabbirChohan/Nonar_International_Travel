package com.example.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Activities.SingelAgentsFlights;
import com.example.Model.AgentModel;
import com.example.Model.Utlis;
import com.example.nonarinternationaltravel.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class DepartureDateAdpter extends RecyclerView.Adapter<DepartureDateAdpter.ViewHolder> {
    Context context;
    ArrayList<AgentModel> newAgentmodelArrayList;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    LinearLayout layout;
  AgentModel newAgentmodel =new AgentModel();

    public DepartureDateAdpter(Context context, ArrayList<AgentModel> newAgentmodelArrayList) {
        this.context = context;
        this.newAgentmodelArrayList = newAgentmodelArrayList;
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference().child("Agent2");
    }

    @NonNull
    @Override
    public DepartureDateAdpter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.singelitem2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DepartureDateAdpter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        newAgentmodel = newAgentmodelArrayList.get(position);

        holder.name.setText(newAgentmodel.getName());
        holder.email.setText(newAgentmodel.getEmail());
        holder.password.setText(newAgentmodel.getPassword());
        holder.debit.setText(String.valueOf(newAgentmodel.getDebit()));
        holder.cradit.setText(String.valueOf(newAgentmodel.getCradit()));
        holder.balance.setText(String.valueOf(Utlis.getBalane(newAgentmodel.getDebit(),newAgentmodel.getCradit())));

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, SingelAgentsFlights.class);
                intent.putExtra("agent",newAgentmodelArrayList.get(position));
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return newAgentmodelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,email,password,cradit,debit,balance;
        LinearLayout layout;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            name=itemView.findViewById(R.id.singelitemname);
            email=itemView.findViewById(R.id.singelitemail);
            layout=itemView.findViewById(R.id.linear11);
            password=itemView.findViewById(R.id.singelitempssword);
            cradit=itemView.findViewById(R.id.singelitem2cradittextview);
            debit=itemView.findViewById(R.id.singelitem2debittextview);
            balance=itemView.findViewById(R.id.singelitem2balancetextview);
        }

    }
}
