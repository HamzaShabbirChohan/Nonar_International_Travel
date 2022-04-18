package com.example.nonarinternationaltravel;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Model.DataModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    DataModel dataModel=new DataModel();
    ArrayList<DataModel>dataModelArrayList;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    Context context;

    public DataAdapter(ArrayList<DataModel> dataModelArrayList, Context context) {
        this.dataModelArrayList = dataModelArrayList;
        this.context = context;
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("UsersData");
    }





    @NonNull
    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.singelitemlayout, parent, false);
        return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull DataAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        dataModel=dataModelArrayList.get(position);
        holder.date.setText(dataModel.getDate());
        holder.name.setText(dataModel.getName());
        holder.refno.setText(dataModel.getRefno());
        holder.airline.setText(dataModel.getAirline());
        holder.sector.setText(dataModel.getSector());
        holder.debit.setText(String.valueOf(dataModel.getDebit()));
        holder.cradit.setText(String.valueOf(dataModel.getCradit()));
        holder.balance.setText(String.valueOf(dataModel.getBalance()));



        holder.editdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DataEntry.class);
                intent.putExtra("load",  dataModelArrayList.get(position));
                intent.putExtra("pos",position);
                context.startActivity(intent);

            }
        });
        holder.delate.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View view) {
                deletLoad(position);
                notifyDataSetChanged();
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
    public int getItemCount()
    {
        return dataModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView date, name,refno,airline, sector,debit, cradit, balance;
        ImageView editdata,delate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            date=itemView.findViewById(R.id.datetextview);
            name =itemView.findViewById(R.id.transectiontextview);
            refno=itemView.findViewById(R.id.refnotextview);
            airline=itemView.findViewById(R.id.airlinetextview);
            sector=itemView.findViewById(R.id.sectortextview);
            debit=itemView.findViewById(R.id.debittextview);
            cradit=itemView.findViewById(R.id.cradittextview);
            balance=itemView.findViewById(R.id.balancetextview);
            editdata=itemView.findViewById(R.id.editdata);
            delate=itemView.findViewById(R.id.deletedata);
        }
    }
}
