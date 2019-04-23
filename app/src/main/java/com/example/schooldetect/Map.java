package com.example.schooldetect;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.schooldetect.jsonData.JsonDataGetter;
import com.example.schooldetect.jsonData.JsontoData;
import com.example.schooldetect.jsonData.LanguageDefiner;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class Map  extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    DatabaseHandler db;
    String ac;
    Spinner spinner;
    private GoogleMap mMap;
    private MapView mapView;
    double X = 22.3101;  //defauly X
    double Y = 114.19;  //  defaul Y
    LocationManager locationManager;
    ArrayList al;
    LanguageDefiner ld;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        db = new DatabaseHandler(this);
        ac = getIntent().getStringExtra("ac");
        spinner = (Spinner) findViewById(R.id.spinner4);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item);
        ld = new LanguageDefiner();
        ArrayList a = db.getType(ld.definelanguage());
        ac = getIntent().getStringExtra("ac");
        for (int i = 0; i < a.size(); i++) {
            adapter.add(a.get(i).toString());
        }
        spinner.setAdapter(adapter);

        al = new ArrayList();
        al =  db.getSchoolByMap("Primary");

        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync( this);


        startGPS();

    }

    @SuppressLint("MissingPermission")
    public void startGPS() {
         locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        // Define a listener that responds to location updates
        // Register the listener with the Location Manager to receive location updates
        //mobile = network provider
        //android studio virtual devices = gps provider
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 30*1000, 10, locationListener);
    }

    LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {

            Y = location.getLongitude();
            X = location.getLatitude();
            setMap();
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onProviderDisabled(String provider) {
        }


    };

        public void Search(View v) {
            startGPS();
            al = db.getSchoolByMap(spinner.getSelectedItem().toString());
            setMap();
        }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (!marker.getTitle().equals("here")) {
            Intent i = new Intent(this, SearchDetail.class);
            i.putExtra("ac", ac);
            i.putExtra("schoolid", marker.getTitle());
            startActivity(i);
            return false;
        }
        return false;
    }



    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }
    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }
    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }



    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

    }

    public void setMap() {
        if (Y != 0 && X != 0) {
            mMap.clear();
            mMap.setMinZoomPreference(12);
            mMap.setIndoorEnabled(true);
            UiSettings uiSettings = mMap.getUiSettings();
            uiSettings.setIndoorLevelPickerEnabled(true);
            uiSettings.setMyLocationButtonEnabled(true);
            uiSettings.setMapToolbarEnabled(true);
            uiSettings.setCompassEnabled(true);
            uiSettings.setZoomControlsEnabled(true);
            LatLng XY = new LatLng(X, Y);

            mMap.addMarker(new MarkerOptions().position(XY).title("here"));
            float zoomLevel = 16.0f; //This goes up to 21

            for (int i =0; i < al.size(); i++)  {
                LatLng fXY = new LatLng(Double.parseDouble(al.get(i).toString().split("@")[1]),Double.parseDouble(al.get(i).toString().split("@")[2]));
                mMap.setOnMarkerClickListener(this);
                mMap.addMarker(new MarkerOptions().position(fXY).title(al.get(i).toString().split("@")[0]).icon(BitmapDescriptorFactory.fromResource(R.drawable.school)));
            }
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(XY, zoomLevel));
        }
    }
}
