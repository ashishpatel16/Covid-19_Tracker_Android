package com.example.covid_19tracker2020;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.covid_19tracker2020.Adapter.MyAdapter;
import com.example.covid_19tracker2020.CovidAPI.StatewiseItem;

import java.io.Serializable;
import java.util.List;

public class All_India_Stats extends AppCompatActivity {

    List<StatewiseItem> stateWise;
    RecyclerView recyclerView;
    Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all__india__stats);

        extras = getIntent().getExtras();

        stateWise = (List<StatewiseItem>) extras.getSerializable("STATE_LIST");

        recyclerView = findViewById(R.id.recyclerView);
        MyAdapter adapter = new MyAdapter(this,stateWise);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}