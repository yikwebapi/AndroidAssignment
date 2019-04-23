package com.example.schooldetect;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.schooldetect.jsonData.LanguageDefiner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class logDetail extends AppCompatActivity {

    ListView listView;
    SharedPreferences sharedpreferences;
    String mypreference = "mypref";
    String ac;
    DatabaseHandler db;
    ArrayList a;
    LanguageDefiner ld;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logdetail);
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        ac = getIntent().getStringExtra("ac");
        ld = new LanguageDefiner();
        listView = (ListView)findViewById(R.id.listview2);
        if (sharedpreferences.contains("scdetail")) {
            Set<String> sidarray = sharedpreferences.getStringSet("scdetail",new HashSet<String>());
            ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1);
            Iterator value = sidarray.iterator();
             a = new ArrayList();
            while (value.hasNext()) {
                a.add(value.next());
            }
            db = new DatabaseHandler(this);
            ArrayList schoolname = db.getSchoolbyid1(sidarray,ld.definelanguage());
            for (int i = 0; i < schoolname.size(); i++) {
                adapter.add(schoolname.get(i));
            }
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
                    String data =  a.get((int)arg3).toString();
                    Intent i = new Intent(arg0.getContext(), SearchDetail.class);
                    i.putExtra("schoolid",data);
                    i.putExtra("ac",ac);
                    startActivity(i);
                }
            });




            }


    }
}
