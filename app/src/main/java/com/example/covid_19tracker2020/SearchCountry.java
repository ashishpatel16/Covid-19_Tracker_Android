package com.example.covid_19tracker2020;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.covid_19tracker2020.CovidAPI.APIClient;
import com.example.covid_19tracker2020.CovidAPI.CasesTimeSeriesItem;
import com.example.covid_19tracker2020.CovidAPI.Response;
import com.example.covid_19tracker2020.CovidAPI.StatewiseItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchCountry extends AppCompatActivity {

    EditText country;
    List<CasesTimeSeriesItem> timeWise;
    List<StatewiseItem> stateWise;
    TextView stateInfo;


    private final String baseURL = "https://api.covid19india.org/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_country);
        country = findViewById(R.id.searchCountryEditText);
        stateInfo = findViewById(R.id.stateTextView);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create()).build();

        APIClient client = retrofit.create(APIClient.class);
        Call<Response> call = client.getStats();

        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if(!response.isSuccessful()){
                    Log.i("ashish","Something went wrong "+response.code());
                }else {

                    timeWise = response.body().getCasesTimeSeries();
                    stateWise = response.body().getStatewise();

                    for(CasesTimeSeriesItem obj : timeWise) {
                        Log.i("ashish","Dated "+obj.getDate()+" Confirmed: "+obj.getDailyconfirmed()
                                +" recovered: "+obj.getDailyrecovered()+" decreased: "+obj.getDailydeceased());
                    }
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
        String res = "State : ";
        for(StatewiseItem obj : stateWise) {
            if(obj.getState().equalsIgnoreCase(stateName)){
                res += obj.getState()+ "\n"+ "Total confirmed: "+obj.getConfirmed() +"\n"+"Total Recovered: "+obj.getRecovered() +"\n"+
                        "Total Deaths: "+obj.getDeaths();
            }
        }
        stateInfo.setText(res);
        Log.i("ashish",""+res);
    }
}