package com.example.lostfoundmapapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class Add extends AppCompatActivity implements LocationListener {

    //Declare variables
    EditText nameinput, iteminput, locationinput;
    String status = "";
    private RadioGroup radioGroup;
    RadioButton radioButton;

    Button add;
    Button button_location;
    LocationManager locationManager;
    double lat;
    double lng;
    String strlat = "";
    String strlng = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Places.initialize(getApplicationContext(),"AIzaSyBsBExYowOYFbpleZlFzgV6S4i8FAB50-o");


        //Match IDs
        nameinput = findViewById(R.id.name);
        iteminput = findViewById(R.id.item);
        locationinput = findViewById(R.id.location);
        add = findViewById(R.id.addbtn);
        radioGroup = findViewById(R.id.radioGroup);
        button_location = findViewById(R.id.locationbutton);


        //Ask permission
        if (ContextCompat.checkSelfPermission(Add.this, android.Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(Add.this,new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION},100);
        }




        button_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLocation();
            }
        });

        locationinput.setFocusable(false);
        //Set on click to radio button
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.found:
                        status = "Found";
                        break;
                    case R.id.lost:
                        status = "Lost";
                        break;
                }
            }
        });
        //Set on click to edittext
        locationinput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS,Place.Field.LAT_LNG,Place.Field.NAME);

                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,fieldList).build(Add.this);
                startActivityForResult(intent,100);
            }
        });

        //Set on click to add button
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Convert address to Latitude and Longitude and convert them to string
                Geocoder geocoder = new Geocoder(getApplicationContext());
                List<Address> addressList;
                try {
                    addressList = geocoder.getFromLocationName(locationinput.getText().toString(),1);

                    if (addressList != null){
                        lat = addressList.get(0).getLatitude();
                        lng = addressList.get(0).getLongitude();

                        strlat = String.valueOf(lat);
                        strlng = String.valueOf(lng);
                    }

                }catch (IOException e){
                    e.printStackTrace();
                }


                //Add data to database
                MyDatabase myDatabase = new MyDatabase(Add.this);
                myDatabase.additem(status.trim(),
                        nameinput.getText().toString().trim(),
                        iteminput.getText().toString().trim(),
                        locationinput.getText().toString().trim(),
                        strlat.trim(),strlng.trim());

            }
        });
    }

    //get current location
    @SuppressLint("MissingPermission")
    private void getLocation() {
        try {
            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,5, Add.this);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //Place autocomplete
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK){
            Place place = Autocomplete.getPlaceFromIntent(data);

            locationinput.setText(place.getAddress());
        } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
            Status status = Autocomplete.getStatusFromIntent(data);
            Toast.makeText(this, status.getStatusMessage(), Toast.LENGTH_SHORT).show();

        }

    }

    //Add search bar to application
    @Override
    public void onLocationChanged(@NonNull Location location) {
        //Toast.makeText(this, ""+location.getLatitude()+","+location.getLongitude(), Toast.LENGTH_SHORT).show();
        try {
            Geocoder geocoder = new Geocoder(Add.this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            String address = addresses.get(0).getAddressLine(0);
            locationinput.setText(address);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        LocationListener.super.onStatusChanged(provider, status, extras);
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        LocationListener.super.onProviderEnabled(provider);
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        LocationListener.super.onProviderDisabled(provider);
    }

}
