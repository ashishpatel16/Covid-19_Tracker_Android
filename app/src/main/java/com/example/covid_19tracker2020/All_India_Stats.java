package com.example.covid_19tracker2020;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class All_India_Stats extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all__india__stats);
        Toast.makeText(this, "Hola!", Toast.LENGTH_SHORT).show();
    }
}