package com.example.schooldetect;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

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
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class mapsound  extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, RecognitionListener, TextToSpeech.OnInitListener {

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

    // start speech to text and text to speech part
    Button bt8;
    Button bt7;
     String type = "";
    TextToSpeech tts;
    final Handler handler = new Handler();
    static final int REQUEST_PERMISSION_KEY = 1;
    private Intent recognizerIntent;
    private SpeechRecognizer speech = null;
    // end








    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.mapsound);
        db = new DatabaseHandler(this);
        ac = getIntent().getStringExtra("ac");
        spinner = (Spinner) findViewById(R.id.spinner5);
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

        mapView = (MapView) findViewById(R.id.mapView2);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync( this);
        startGPS();


        //start speech to text and text to speech
        tts = new TextToSpeech(this, this);
        AudioManager mAudioManager =(AudioManager)getSystemService(Context.AUDIO_SERVICE);
        mAudioManager.setStreamMute(AudioManager.STREAM_MUSIC, false);
        bt7 = (Button)findViewById(R.id.button7);
        bt8 = (Button)findViewById(R.id.button8);
        String[] PERMISSIONS = {Manifest.permission.RECORD_AUDIO};
        if (!Function.hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST_PERMISSION_KEY);
        }
        speech = SpeechRecognizer.createSpeechRecognizer(this);
        speech.setRecognitionListener(this);
        recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE,
                "en");
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
                this.getPackageName());
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, 100000);
        recognizerIntent.putExtra("android.speech.extra.DICTATION_MODE", true);

        bt8.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View p1) {
                speech.startListening(recognizerIntent);
                bt8.setEnabled(false);

        //end
            }
        });
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

    public void Search1(View v) {
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
        if (speech != null) {
            speech.destroy();
            Log.d("Log", "destroy");
        }
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



    //start new method

    @Override
    public void onReadyForSpeech(Bundle params) {
        Log.d("Log", "onReadyForSpeech");
    }

    @Override
    public void onBeginningOfSpeech() {
        Log.d("Log", "onBeginningOfSpeech");
    }

    @Override
    public void onRmsChanged(float rmsdB) {
        Log.d("Log", "onRmsChanged: " + rmsdB);
    }

    @Override
    public void onBufferReceived(byte[] buffer) {
        Log.d("Log", "onBufferReceived: " + buffer);
    }

    @Override
    public void onEndOfSpeech() {
        Log.d("Log", "onEndOfSpeech");
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms

                Log.d("RECOGNIZER", "done");
                speech.startListening(recognizerIntent);
            }
        }, 1000);
    }

    @Override
    public void onError(int error) {
        String errorMessage = getErrorText(error);
        Log.d("Log", "FAILED " + errorMessage);
        if (errorMessage.equals("a") || errorMessage.equals("b") ) {
            speech.cancel();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    Log.d("RECOGNIZER", "done");
                    speech.startListening(recognizerIntent);
                }
            }, 1000);
        } else {
        }
    }

    @Override
    public void onResults(Bundle results) {
        Log.d("Log", "onResults");
    }

    @Override
    public void onPartialResults(Bundle partialResults) {
        Log.d("Log", "onPartialResults");

        ArrayList<String> matches = partialResults.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        String text = "";
        /* To get all close matchs        */
        for (String result : matches) {
            text += result + "\n";
        }

        //       text = matches.get(0); //  Remove this line while uncommenting above    codes

        boolean checker = false;

        for (int i = 0; i < matches.size(); i++) {
            if (matches.get(i).toLowerCase().matches("(.*)primary(.*)")) {
                checker = true;
                type = "Primary";
                break;
            }

            if (matches.get(i).toLowerCase().matches("(.*)secondary(.*)")) {
                checker = true;
                type = "Secondary";
                break;
            }

            if (matches.get(i).toLowerCase().matches("(.*)university(.*)")) {
                checker = true;
                type = "University";
                break;
            }
            if (matches.get(i).toLowerCase().matches("(.*)target(.*)") || matches.get(i).toLowerCase().matches("(.*)school(.*)") ) {
                checker = true;
                type = "target";
                break;
            }


        }
        if (checker == true && !type.equals("target")) {
            speech.cancel();
            speakOut(type);
            Search2();
            bt8.setEnabled(true);
        } else if (checker == true && type.equals("target")) {
            speech.cancel();
            bt8.setEnabled(true);
            String XandY = gotshortestpathschool();
            setMapBySound(Double.parseDouble(XandY.split("@")[0]),Double.parseDouble(XandY.split("@")[1]));
            speakOut("The closest school has been found. Please view newest location on the map");
        }
    }
        //search by sound control
    public void Search2() {
        startGPS();
        al = db.getSchoolByMap(type);
        if (type.equals("Primary")){
        spinner.setSelection(0);
        } else if (type.equals("Secondary")) {
            spinner.setSelection(1);
        } else if (type.equals("University")) {
            spinner.setSelection(2);
        }
        setMap();
    }

    @Override
    public void onEvent(int eventType, Bundle params) {
        Log.d("Log", "onEvent");
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            // Setting speech language
            int result = tts.setLanguage(Locale.US);
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                // Cook simple toast message with message
                Toast.makeText(getApplicationContext(), "Language not supported",
                        Toast.LENGTH_LONG).show();
                Log.e("TTS", "Language is not supported");
            }
        }
    }

    public static String getErrorText(int errorCode) {
        String message;
        switch (errorCode) {
            case SpeechRecognizer.ERROR_AUDIO:
                message = "Audio recording error";
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                message = "Client side error";
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                message = "Insufficient permissions";
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                message = "Network error";
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                message = "Network timeout";
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                message = "b";
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                message = "RecognitionService busy";
                break;
            case SpeechRecognizer.ERROR_SERVER:
                message = "error from server";
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                message = "a";
                break;
            default:
                message = "Didn't understand, please try again.";
                break;
        }
        return message;
    }
    private void speakOut(String s) {
        tts.speak(s, TextToSpeech.QUEUE_FLUSH, null);
    }


    public String gotshortestpathschool() {
        String XANDY = Double.parseDouble(al.get(0).toString().split("@")[1]) + "@" + Double.parseDouble(al.get(0).toString().split("@")[2]);
        double absoulatenumber = Math.abs(X-Double.parseDouble(al.get(0).toString().split("@")[1])) + Math.abs(Y-Double.parseDouble(al.get(0).toString().split("@")[2]));
        for (int i =0; i < al.size(); i++) {
            double absnumber = Math.abs(X-Double.parseDouble(al.get(i).toString().split("@")[1])) + Math.abs(Y-Double.parseDouble(al.get(i).toString().split("@")[2]));
            if (absoulatenumber > absnumber) {
                absoulatenumber=absnumber;
                XANDY=Double.parseDouble(al.get(i).toString().split("@")[1]) + "@" + Double.parseDouble(al.get(i).toString().split("@")[2]);
            }
        }
        return XANDY;
    }

    public void setMapBySound(double X, double Y) {
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

class Function {

    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
}
