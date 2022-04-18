package com.example.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Activities.AddFlightActivity;
import com.example.Activities.SingelAgentsFlights;
import com.example.Model.DataModel;
import com.example.nonarinternationaltravel.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AgentAdapter extends RecyclerView.Adapter<AgentAdapter.ViewHolder> {

    DataModel dataModel=new DataModel();
    ArrayList<DataModel> dataModelArrayList;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    Context context;
    LinearLayout layout;

    boolean isSingleAgent = false;

    public AgentAdapter(ArrayList<DataModel> dataModelArrayList, Context context) {
        this.dataModelArrayList = dataModelArrayList;
        this.context = context;
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("AgentData");
    }

    public AgentAdapter(ArrayList<DataModel> dataModelArrayList, Context context,boolean isSingleAgent) {
        this.dataModelArrayList = dataModelArrayList;
        this.context = context;
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("AgentData");
        this.isSingleAgent = isSingleAgent;
    }



    @NonNull
    @Override
    public AgentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.singelitemlayout, parent, false);
        return new AgentAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull AgentAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        dataModel=dataModelArrayList.get(position);
        holder.date.setText(dataModel.getDate());
        holder.name.setText(dataModel.getName());
        holder.refno.setText(dataModel.getRefno());
        holder.airline.setText(dataModel.getAirline());
        holder.sector.setText(dataModel.getSector());
        holder.debit.setText(String.valueOf(dataModel.getDebit()));
        holder.cradit.setText(String.valueOf(dataModel.getCradit()));
        holder.balance.setText(String.valueOf(dataModel.getBalance()));
        holder.agentname.setText(dataModel.getAgentName());
        holder.descripton.setText(dataModel.getDescription());

        //holder.agentid.setText(dataModel.getAgentId());

/*
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, SingelAgentsFlights.class);
                context.startActivity(intent);
            }
        });
*/
        if(isSingleAgent){
            holder.editdata.setVisibility(View.INVISIBLE);
            holder.delate.setVisibility(View.INVISIBLE);
        }


        holder.editdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddFlightActivity.class);
                intent.putExtra("loadd", dataModelArrayList.get(position));
                intent.putExtra("poss",position);
                context.startActivity(intent);
            }
        });
        holder.delate.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View view) {
                deletLoad(position);

            }
        });

    }
    private void deletLoad(int position)
    {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(context)
                // set message, title, and icon
                .setTitle("Delete")
                .setMessage("Do you want to Delete this load")

                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        //your deleting code

                        databaseReference.child(dataModelArrayList.get(position).getUid()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                               // dataModelArrayList.remove(position);

                                Toast.makeText(context, "Delete Successfully !!!", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        });

                        notifyDataSetChanged();

                    }

                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();

        myQuittingDialogBox.show();
    }

    @Override
    public int getItemCount() {
        return dataModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView date, name,refno,airline, sector,debit, cradit, balance,agentname,agentid,descripton;
        ImageView editdata,delate;
        LinearLayout layout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            date=itemView.findViewById(R.id.datetextview);
            layout=itemView.findViewById(R.id.linera1);
            name =itemView.findViewById(R.id.transectiontextview);
            refno=itemView.findViewById(R.id.refnotextview);
            airline=itemView.findViewById(R.id.airlinetextview);
            sector=itemView.findViewById(R.id.sectortextview);
            debit=itemView.findViewById(R.id.debittextview);
            cradit=itemView.findViewById(R.id.cradittextview);
            balance=itemView.findViewById(R.id.balancetextview);
            editdata=itemView.findViewById(R.id.editdata);
            delate=itemView.findViewById(R.id.deletedata);
            agentname=itemView.findViewById(R.id.agentnametextview);
            agentid=itemView.findViewById(R.id.agentidtextview);
            descripton=itemView.findViewById(R.id.descriptiontextview);

        }
    }
}
