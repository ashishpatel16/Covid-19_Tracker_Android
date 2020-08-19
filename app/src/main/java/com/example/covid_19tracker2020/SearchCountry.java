package com.example.covid_19tracker2020;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SearchCountry extends AppCompatActivity {

    EditText country;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_country);
        country = findViewById(R.id.searchCountryEditText);
    }

    public void onButtonClick(View view){
        Toast.makeText(this, "Country Entered : "+ country.getText().toString(), Toast.LENGTH_SHORT).show();
    }
}