package com.example.schooldetect;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Control extends AppCompatActivity {

    String mypreference = "mypref";
    String ac;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.control);
        ac = getIntent().getStringExtra("ac");
        TextView tv = (TextView)findViewById(R.id.textView3);
        tv.setText(ac);
    }


    public void add(View v) {
        Intent i = new Intent(this, Insert.class);
        startActivity(i);
    }
    public void search(View v) {
        Intent i = new Intent(this, Search.class);
        i.putExtra("ac",ac);
        startActivity(i);
    }
    public void map(View v) {
        Intent i = new Intent(this, Map.class);
        i.putExtra("ac",ac);
        startActivity(i);

    }
    public void rank (View v) {
        Intent i = new Intent(this, Rank.class);
        i.putExtra("ac",ac);
        startActivity(i);
    }
    public void log(View v ) {
        Intent i = new Intent(this, logDetail.class);
        i.putExtra("ac",ac);
        startActivity(i);
    }
    public void logout(View v) {
        getSharedPreferences(mypreference, 0).edit().clear().commit();
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);

        finish();
    }

    public void soundcontrol(View v) {
        Intent i = new Intent(this, mapsound.class);
        i.putExtra("ac",ac);
        startActivity(i);
    }

}
