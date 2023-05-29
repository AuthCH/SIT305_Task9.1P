package com.example.lostfoundmapapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    //Declare variables
    Context context;
    ArrayList id;
    ArrayList name;
    ArrayList item;
    ArrayList status;
    Activity activity;
    ArrayList location;
    ArrayList lat;
    ArrayList lng;

    public MyAdapter(Activity activity,Context context, ArrayList id,ArrayList status, ArrayList name, ArrayList item, ArrayList location,ArrayList lat,ArrayList lng) {
        this.activity = activity;
        this.context = context;
        this.id = id;
        this.status = status;
        this.name = name;
        this.item = item;
        this.location = location;
        this.lat = lat;
        this.lng = lng;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recvlayout,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder,final int position) {

        //Set text in recyclerview
        holder.status.setText(String.valueOf(status.get(position)));
        holder.disitem.setText(String.valueOf(item.get(position)));
        //Set on click for recyclerview
        holder.mainlayout.setOnClickListener(new View.OnClickListener() {
            //Go to detail page and pass information
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, detailpage.class);
                intent.putExtra("id", String.valueOf(id.get(position)));
                intent.putExtra("status", String.valueOf(status.get(position)));
                intent.putExtra("name", String.valueOf(name.get(position)));
                intent.putExtra("item", String.valueOf(item.get(position)));
                intent.putExtra("location", String.valueOf(location.get(position)));
                intent.putExtra("lat", String.valueOf(lat.get(position)));
                intent.putExtra("lng", String.valueOf(lng.get(position)));

                activity.startActivityForResult(intent,1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        //Declare variables
        TextView disitem,status;
        LinearLayout mainlayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            //Match IDs
            status = itemView.findViewById(R.id.disstatus);
            disitem = itemView.findViewById(R.id.disitem);
            mainlayout = itemView.findViewById(R.id.recvlayout);

        }
    }
}
