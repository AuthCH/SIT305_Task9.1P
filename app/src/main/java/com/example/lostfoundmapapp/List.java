package com.example.lostfoundmapapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class List extends AppCompatActivity {

    //Declare variables
    RecyclerView recyclerView;
    MyDatabase myDatabase;
    ArrayList<String> id,status,name,item,location,lat,lng;
    MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        //Match IDs
        recyclerView = findViewById(R.id.recv);

        myDatabase = new MyDatabase(getApplicationContext());
        id = new ArrayList<>();
        status = new ArrayList<>();
        name = new ArrayList<>();
        item = new ArrayList<>();
        location = new ArrayList<>();
        lat = new ArrayList<>();
        lng = new ArrayList<>();


        //Call ArrayData
        ArrayData();

        //Set adapter and layout
        myAdapter = new MyAdapter(List.this,getApplicationContext(),id,status,name,item,location,lat,lng);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

    }

    //Refesh app after click remove
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }

    void ArrayData(){

        //Add information to arrays
        Cursor cursor = myDatabase.readAllData();
        if(cursor.getCount() == 0){
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        }
        else {
            while (cursor.moveToNext()){
                id.add(cursor.getString(0));
                status.add(cursor.getString(1));
                name.add(cursor.getString(2));
                item.add(cursor.getString(3));
                location.add(cursor.getString(4));
                lat.add(cursor.getString(5));
                lng.add(cursor.getString(6));
            }
        }

    }

}