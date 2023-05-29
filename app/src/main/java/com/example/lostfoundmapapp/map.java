package com.example.lostfoundmapapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.database.Cursor;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;

public class map extends AppCompatActivity implements OnMapReadyCallback {

    //Declare variables
    private GoogleMap map;
    FusedLocationProviderClient fusedLocationProviderClient;
    MyDatabase myDatabase;
    ArrayList<String> id,status,name,item,location,lat,lng;
    ArrayList<LatLng> latLngs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        //Match IDs
        myDatabase = new MyDatabase(getApplicationContext());
        id = new ArrayList<>();
        status = new ArrayList<>();
        name = new ArrayList<>();
        item = new ArrayList<>();
        location = new ArrayList<>();
        lat = new ArrayList<>();
        lng = new ArrayList<>();
        latLngs = new ArrayList<LatLng>();



        ArrayData();

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);
        mapFragment.getMapAsync(this);

    }
    //Add multiple marker
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;

        for (int i = 0; i < id.size(); i++){
            LatLng marker = new LatLng(Double.parseDouble(lat.get(i)),Double.parseDouble(lng.get(i)));
            map.addMarker(new MarkerOptions().position(marker).title("Lost/Found Item"));
            map.animateCamera(CameraUpdateFactory.zoomTo(30.0f));
            map.moveCamera(CameraUpdateFactory.newLatLng(marker));
        }


    }
    void ArrayData() {

        //Add information to arrays
        Cursor cursor = myDatabase.readAllData();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
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