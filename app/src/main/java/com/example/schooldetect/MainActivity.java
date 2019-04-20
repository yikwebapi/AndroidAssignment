package com.example.schooldetect;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    EditText account;
    EditText password;
    DatabaseHandler db;
    //sharepreference
    SharedPreferences sharedpreferences;
    String mypreference = "mypref";
    //end of share

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        account = (EditText)findViewById(R.id.editText2);
        password = (EditText)findViewById(R.id.editText3);
        db = new DatabaseHandler(this);

        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        if (sharedpreferences.contains("ackey")) {
            String account= sharedpreferences.getString("ackey","");
            Intent i = new Intent(this, Control.class);
            i.putExtra("ac",account);
            startActivity(i);
            finish();
        }




    }

        public void Register(View v) {
            String ac = account.getText().toString();
            String pw = password.getText().toString();
            Account newac = new Account(ac,pw);
            if (ac.equals("") || pw.equals("")) {
               Toast.makeText(v.getContext(), "Can not be null",
                        Toast.LENGTH_LONG).show();
           } else {
                if (db.checkAccount(newac)) {
                    db.addAccount(newac);
                    Toast.makeText(v.getContext(), "Register Successful",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(v.getContext(), "Account existed",
                            Toast.LENGTH_LONG).show();
                }

           }
        }

        public void Login (View v ) {

            String ac = account.getText().toString();
            String pw = password.getText().toString();
            Account newac = new Account(ac,pw);

            if (db.checkLogin(newac)) {
                Save(ac);
                Intent i = new Intent(this, Control.class);
                i.putExtra("ac",ac);
                startActivity(i);
                finish();
            } else {
                Toast.makeText(v.getContext(), "Wrong",
                        Toast.LENGTH_LONG).show();
            }
        }



    public void Save(String acc) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("ackey", acc);
        editor.commit();
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            MainActivity.super.onBackPressed();
            quit();
        }
        return super.onKeyDown(keyCode, event);
    }

    public void quit() {
        Intent start = new Intent(Intent.ACTION_MAIN);
        start.addCategory(Intent.CATEGORY_HOME);
        start.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        start.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(start);
    }
}
