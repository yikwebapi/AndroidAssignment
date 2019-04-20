package com.example.schooldetect;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.schooldetect.jsonData.JsonDataGetter;
import com.example.schooldetect.jsonData.DownloadImage;
import com.example.schooldetect.jsonData.JsontoData;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class SearchDetail extends AppCompatActivity {

    TextView tv6;
    TextView tv9;
    TextView tv15;
    Spinner sp2;
    Button bt4;
    DatabaseHandler db;
    SchoolDetailData sdd;
    String path = "https://www.googleapis.com/customsearch/v1?";
    String key = "key=AIzaSyA14hmwao9l-eyzNGzFhzD7GFZUPlyS2wI";
    String cx = "cx=001734592082236324715:sob9rqk49yg";
    String searchType = "searchType=image";
    String q = "q=";
    String imagepath = "";
String ac = "";
String id = "";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchdetail);
        db = new DatabaseHandler(this);
        id = getIntent().getStringExtra("schoolid");
        ac =getIntent().getStringExtra("ac");
        sdd = db.getSchoolbyId(id);
        q = q + sdd.getcname();

        String url = path + key + "&" + cx + "&" + searchType + "&" +q;

        JsonDataGetter jdg = new JsonDataGetter(SearchDetail.this,url);

        try {

        String json =  jdg.execute().get();
            JsontoData jtd = new JsontoData(json);
            imagepath = jtd.getimagepath();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (!imagepath.equals("")) {
            new DownloadImage((ImageView) findViewById(R.id.imageView)).execute(imagepath);
        }



        tv6 = (TextView)findViewById(R.id.textView6);
        tv9 = (TextView)findViewById(R.id.textView9);
        tv15 = (TextView)findViewById(R.id.textView15);

        tv6.setText(sdd.getcname());
        tv9.setText(sdd.getcaddress());
        tv15.setText(sdd.getmark().toString());



            //invisible if have marked
            bt4 = (Button)findViewById(R.id.button4);
            sp2 = (Spinner)findViewById(R.id.spinner2);
        if (db.marked(id,ac)) {
            sp2 = (Spinner) findViewById(R.id.spinner2);
            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item);
            for (int i = 0; i < 10; i++) {
                adapter.add(i);
            }
            sp2.setAdapter(adapter);
        } else {
            bt4.setVisibility(View.INVISIBLE);
            sp2.setVisibility(View.INVISIBLE);
        }



    }


    public void addmark(View v ) {
        String mark = sp2.getSelectedItem().toString();
        if (db.marked(id,ac)) {
            db.addMark(id, ac, mark);
            Toast.makeText(v.getContext(), "Done",
                    Toast.LENGTH_LONG).show();
        }
    }






}
