package com.example.covid_19tracker2020;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.covid_19tracker2020.CovidAPI.APIClient;
import com.example.covid_19tracker2020.CovidAPI.CasesTimeSeriesItem;
import com.example.covid_19tracker2020.CovidAPI.Response;
import com.example.covid_19tracker2020.CovidAPI.StatewiseItem;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchCountry extends AppCompatActivity {

    private final String baseURL = "https://api.covid19india.org/";
    private EditText country;
    private List<CasesTimeSeriesItem> timeWise;
    private List<StatewiseItem> stateWise;
    private TextView stateInfo;
    private Intent allStateStats;
    LottieAnimationView loadingAnimation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_country);


        country = findViewById(R.id.searchCountryEditText);
        stateInfo = findViewById(R.id.stateTextView);
        loadingAnimation = findViewById(R.id.loadingAnim);

        loadingAnimation.setVisibility(View.VISIBLE);
        loadingAnimation.playAnimation();

        // Deploying retrofit to fetch data for us.
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create()).build();

        APIClient client = retrofit.create(APIClient.class);
        Call<Response> call = client.getStats();

        // Response is js object containing 3 js Arrays - CasesTimeSeries, Statewise data and testing data.

        call.enqueue(new Callback<Response>() {

            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if(!response.isSuccessful()){

                    Log.i("ashish","Something went wrong "+response.code());
                    Toast.makeText(SearchCountry.this, "Something Went Wrong!", Toast.LENGTH_SHORT).show();

                }else {

                    timeWise = response.body().getCasesTimeSeries();
                    stateWise = response.body().getStatewise();

                    loadingAnimation.pauseAnimation();
                    loadingAnimation.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Log.i("ashish", "Something went Wrong");
            }
        });
    }

    public void stateStats(View view) {
        String stateName = country.getText().toString();
        boolean stateFound = false;


        // Making android keyboard disappear upon hitting button.
        try {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }


        // Handling the result data as per the requested state.
        String res = "State : ";
        for(StatewiseItem obj : stateWise) {
            if(obj.getState().equalsIgnoreCase(stateName)){
                res += obj.getState()+ "\n"+ "Total confirmed: "+obj.getConfirmed() +"\n"+"Total Recovered: "+obj.getRecovered() +"\n"+
                        "Total Deaths: "+obj.getDeaths();
                stateFound = true;
            }
        }

        if(stateFound) {
            stateInfo.setText(res);
            Log.i("ashish",""+res);
        }else {
            Toast.makeText(this, "Invalid State, Please enter a valid State", Toast.LENGTH_SHORT).show();
        }

    }

    public void goToAllIndiaStats(View view) {

        allStateStats = new Intent(this,All_India_Stats.class);
        allStateStats.putExtra("STATE_LIST",(Serializable)stateWise);

        startActivity(allStateStats);
    }
}