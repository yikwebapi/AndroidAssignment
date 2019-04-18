package com.example.schooldetect;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    EditText account;
    EditText password;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        account = (EditText)findViewById(R.id.editText2);
        password = (EditText)findViewById(R.id.editText3);
    }

        public void Register(View v) {
            String ac = account.getText().toString();
            String pw = password.getText().toString();

            if (ac.equals("") || pw.equals("")) {
                Toast.makeText(v.getContext(), "Can not be null",
                        Toast.LENGTH_LONG).show();
            } else {

                if (ac.equals("yikyik")) {
                    Toast.makeText(v.getContext(), "Existed account",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(v.getContext(), "Register Successful",
                            Toast.LENGTH_LONG).show();
                }
            }
        }

        public void Login (View v ) {

            String ac = account.getText().toString();
            String pw = password.getText().toString();

            if (ac.equals("yikyik") && pw.equals("123456")) {
                Toast.makeText(v.getContext(), "Successful",
                        Toast.LENGTH_LONG).show();
                Intent i = new Intent(this, Control.class);
                startActivity(i);
            } else {
                Toast.makeText(v.getContext(), "Wrong",
                        Toast.LENGTH_LONG).show();
            }
        }
}
