package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.StrictMode;
import android.text.method.MovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

    private Displayer displayer;

    private Spinner areaSpinner;
    private EditText startingTimeInput;
    private EditText endTimeInput;
    private EditText enterDate;
    private RecyclerView recyclerView;
    private EditText searchMovieInput;
    private TextView searchMovieText;
    private TextView infoText;


    /*
    Lähteet RecyclerView:n käyttämiselle. Lähteistä saatuja tietoja käytetty Layout Managerin asettelussa RecyclerView:lle sekä Displayer-luokassa:
    Android developers: https://developer.android.com/guide/topics/ui/layout/recyclerview
    Ben O'Brien:n 7.4.2020 video aiheesta https://www.youtube.com/watch?v=__OMnFR-wZU
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        this.displayer = new Displayer();
        this.startingTimeInput = (EditText) findViewById(R.id.startingTimeInput);
        this.endTimeInput = (EditText) findViewById(R.id.endTimeInput);
        this.enterDate = (EditText) findViewById(R.id.enterDate);
        this.searchMovieInput = (EditText) findViewById(R.id.searchMovieInput);
        this.searchMovieText = (TextView) findViewById(R.id.searchMovieText);
        this.infoText = (TextView) findViewById(R.id.infoText);


        this.recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(displayer);


        this.areaSpinner = (Spinner) findViewById(R.id.areaSpinner);
        displayer.setSpinnerAdapter(areaSpinner, MainActivity.this);
        areaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                signalUpdate();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}
        });

    }

    public void refreshButton(View v) {
        signalUpdate();
    }

    public void signalUpdate() {
        String date = new SimpleDateFormat("dd.MM.yyyy").format(new Date());
        if (!enterDate.getText().toString().isEmpty()) {
            date = enterDate.getText().toString();
        }
        displayer.updateContentList(areaSpinner, date, searchMovieInput.getText().toString(), infoText);
        String startingTime = "00:00";
        String endingTime = "23:59";

        if (!startingTimeInput.getText().toString().isEmpty()) {
            startingTime = startingTimeInput.getText().toString();
        }

        if (!endTimeInput.getText().toString().isEmpty()) {
            endingTime = endTimeInput.getText().toString();
        }

        displayer.filterByTime(startingTime, endingTime);
    }

}