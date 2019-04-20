package com.example.schooldetect;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.HashMap;

public class Search extends AppCompatActivity {

    DatabaseHandler db;
    ListView listView;
    Spinner spinner;
    String ac;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        db = new DatabaseHandler(this);
        listView = (ListView)findViewById(R.id.listView);
         spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item);
        ArrayList a = db.getType("eng");
        ac = getIntent().getStringExtra("ac");
        for (int i = 0; i < a.size(); i++) {
            adapter.add(a.get(i).toString());
        }
        spinner.setAdapter(adapter);
    }

    public void SearchSchoolbyType(View v) {

        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1);
        final ArrayList a = db.getSchool(spinner.getSelectedItem().toString());
        for (int i = 0; i < a.size(); i++) {
            String id = a.get(i).toString().split("@")[0];
            String cname = a.get(i).toString().split("@")[1];
            String ename = a.get(i).toString().split("@")[2];
            adapter.add(cname);
        }
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
            String data =  a.get((int)arg3).toString().split("@")[0];
                Intent i = new Intent(arg0.getContext(), SearchDetail.class);
                i.putExtra("schoolid",data);
                i.putExtra("ac",ac);
                startActivity(i);
            }
        });




    }
}