package com.example.schooldetect;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.schooldetect.jsonData.JsonDataGetter;
import com.example.schooldetect.jsonData.JsontoData;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static android.view.View.X;
import static android.view.View.Y;


public class Insert  extends AppCompatActivity {
    EditText et;
    EditText et4;
    EditText et5;
    EditText et6;
    EditText et7;
    EditText et8;
    Spinner sp3;
    DatabaseHandler db;

    double X = 0;
    double Y = 0;

    @SuppressLint("MissingPermission")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insert);
        et = (EditText)findViewById(R.id.editText);
        et4 = (EditText)findViewById(R.id.editText4);
        et5 = (EditText)findViewById(R.id.editText5);
        et6 = (EditText)findViewById(R.id.editText6);
        et7 = (EditText)findViewById(R.id.editText7);
        et8 = (EditText)findViewById(R.id.editText8);
        sp3 =  (Spinner)findViewById(R.id.spinner3);
        db = new DatabaseHandler(this);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item);
        ArrayList a = db.getType("eng");
        for (int i = 0; i < a.size(); i++) {
            adapter.add(a.get(i).toString());
        }
        sp3.setAdapter(adapter);
       // if (canGetLocation() == false) {
         //   et.setText("GPS is not open. Please open the location permission and GPS");

      //  } else {
            startGPS();
       // }
    }




    public void insert(View v) {

        String ename = et.getText().toString();
        String cname = et4.getText().toString();
        String eaddress = et5.getText().toString();
        String caddress = et6.getText().toString();
        String X = et7.getText().toString();
        String Y = et8.getText().toString();
        String type = sp3.getSelectedItem().toString();

        if (ename.equals("") || cname.equals("") ||eaddress.equals("") ||caddress.equals("") ||X.equals("") ||Y.equals("") ) {
            Toast.makeText(v.getContext(), "Can not be null",
                    Toast.LENGTH_LONG).show();
        } else {
            db.addSchoolData(ename,cname,eaddress,caddress,X,Y,type);
            Toast.makeText(v.getContext(), "Added",
                    Toast.LENGTH_LONG).show();

        }


    }




    public boolean canGetLocation() {
        boolean result = false;
        LocationManager lm = null;
        boolean gps_enabled = false;
        boolean network_enabled = false;
        if (lm == null)

            lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // exceptions will be thrown if provider is not permitted.
        try {
            network_enabled = lm
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }
        if (network_enabled == false) {
            result = false;
        } else {
            result = true;
        }

        return result;
    }

    @SuppressLint("MissingPermission")
    public void startGPS() {
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        // Define a listener that responds to location updates
        // Register the listener with the Location Manager to receive location updates
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 30*1000, 10, locationListener);
    }
    LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {

            Y = location.getLongitude();
            X = location.getLatitude();

            et7.setText(X + "");
            et8.setText(Y+ "");

            String engjson = getEngJson(X+","+Y);
            String cnjson = getcnJson(X+","+Y);
            String engjsonall = "";
            String cnjsonall = "";
            JsonDataGetter jdg = new JsonDataGetter(Insert.this,engjson);
            //Get json string full
            try{
             engjsonall = jdg.execute().get();
             } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
            JsontoData jdd = new JsontoData(engjsonall);
            et5.setText(jdd.getAddress());
        // end of get json string full (ENGLISH)

            //start get json address string full(Chinese)
             jdg = new JsonDataGetter(Insert.this,cnjson);
            try{
                cnjsonall = jdg.execute().get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            JsontoData jdd2 = new JsontoData(cnjsonall);
            et6.setText(jdd2.getAddress());

            //start get json address string full(Chinese)
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onProviderDisabled(String provider) {
        }
    };



    public String getEngJson(String latlon) {
       return "https://maps.googleapis.com/maps/api/geocode/json?latlng="+latlon+"&zoom=14&size=400x300&sensor=false&language=en&key=AIzaSyAM9WQCE5FM9vJpjUbXAEPq0iVafQyI0XM";
    }
    public String getcnJson(String latlon) {
        return "https://maps.googleapis.com/maps/api/geocode/json?latlng="+latlon+"&zoom=14&size=400x300&sensor=false&language=zh_tw&key=AIzaSyAM9WQCE5FM9vJpjUbXAEPq0iVafQyI0XM";
    }





}
