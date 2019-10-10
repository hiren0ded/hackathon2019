package com.example.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import android.view.View;
import android.view.View.OnClickListener;

public class Challenge extends AppCompatActivity {

    private Spinner spinner1;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.challenge);
        addListenerOnButton();
    }
    public void addListenerOnButton() {

        spinner1 = (Spinner) findViewById(R.id.spinner1);
        // spinner2 = (Spinner) findViewById(R.id.spinner2);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);


        btnSubmit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String Name=null;
                Name=spinner1.getSelectedItem().toString();
                if(Name != null) {
                    Toast.makeText(Challenge.this,
                            String.valueOf("Challenge Sent")
                            ,
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Challenge.this,
                            "OnClickListener : " +
                                    "\nPlease select friend from the list : "
                            ,
                            Toast.LENGTH_SHORT).show();

                }


            }

        });

    }
}