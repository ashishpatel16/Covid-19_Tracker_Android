package com.example.covid_19tracker2020;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.covid_19tracker2020.CovidAPI.StatewiseItem;

import java.util.List;

public class State_Info extends AppCompatActivity {

    TextView name,deaths,rec,recDelta,active,conf,confDelta,deltaDeath,desc,lastUpdated;
    Bundle extras;
    List<StatewiseItem> stateList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state__info);

        extras = getIntent().getExtras();
        int pos = extras.getInt("State_Index");
        stateList = (List<StatewiseItem>) extras.getSerializable("State_List");
        StatewiseItem st = stateList.get(pos);

        name = findViewById(R.id.st_name);
        name.setText(st.getState());

        desc = findViewById(R.id.st_desc);
        if(!st.getStatenotes().equalsIgnoreCase("")){
            desc.setText("NOTE : "+st.getStatenotes());
        }else {
            desc.setText(st.getStatenotes());
        }

        lastUpdated = findViewById(R.id.st_lastUpdated);
        lastUpdated.setText("Last Updated : "+st.getLastupdatedtime());

        deaths = findViewById(R.id.st_deaths);
        deaths.setText(st.getDeaths());

        deltaDeath = findViewById(R.id.st_delta_deaths);
        deltaDeath.setText("+"+st.getDeltadeaths());

        rec = findViewById(R.id.st_rec);
        rec.setText(st.getRecovered());

        recDelta = findViewById(R.id.st_delta_rec);
        recDelta.setText("+"+st.getDeltarecovered());

        active = findViewById(R.id.st_active);
        active.setText(st.getActive());

        conf= findViewById(R.id.st_cnfd);
        conf.setText(st.getConfirmed());

        confDelta = findViewById(R.id.st_delta_cnfd);
        confDelta.setText("+"+st.getDeltaconfirmed());
    }
}