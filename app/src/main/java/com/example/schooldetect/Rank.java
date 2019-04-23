package com.example.schooldetect;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.schooldetect.jsonData.LanguageDefiner;

import java.util.ArrayList;
import java.util.HashMap;

public class Rank extends AppCompatActivity {

    DatabaseHandler db;
    ListView listView;
    String ac;
    ArrayList sid;
    LanguageDefiner ld;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rank);
        db = new DatabaseHandler(this);
        listView = (ListView)findViewById(R.id.listView3);
        ac = getIntent().getStringExtra("ac");

        HashMap<String,Double> hm = db.SchoolRank();

         sid = new ArrayList();
        ArrayList mark = new ArrayList();
        int counter = 0;
        ld = new LanguageDefiner();
        for (String key : hm.keySet()) {
            sid.add(key);
            mark.add(hm.get(key));
            counter = counter + 1;
            if (counter >=10) {
                break;
            }
        }

            ArrayList schoolname = db.getSchoolbyid2(sid,ld.definelanguage());

        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1);
        for (int i = 0; i < schoolname.size(); i++) {
            adapter.add(schoolname.get(i)  + " Mark:" + mark.get(i));
        }
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
                String data =  sid.get((int)arg3).toString().split("@")[0];
                Intent i = new Intent(arg0.getContext(), SearchDetail.class);
                i.putExtra("schoolid",data);
                i.putExtra("ac",ac);
                startActivity(i);
            }
        });





    }

}
