package com.example.lostfoundmapapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    //Declare variables
    Button add,list,map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Match IDs
        add = findViewById(R.id.add);
        list = findViewById(R.id.list);
        map = findViewById(R.id.mapbtn);

        //set on click to add button
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Go to Add page
                Intent intent = new Intent(MainActivity.this,Add.class);
                startActivity(intent);
            }
        });
        //set on click to list button
        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Go to List page
                Intent intent = new Intent(MainActivity.this,List.class);
                startActivity(intent);
            }
        });
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,map.class);
                startActivity(intent);
            }
        });
    }
}