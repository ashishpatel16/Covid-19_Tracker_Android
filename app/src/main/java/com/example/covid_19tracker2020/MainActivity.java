package com.example.covid_19tracker2020;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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

public class MainActivity extends AppCompatActivity {

    LottieAnimationView anim;
    private final String baseURL = "https://api.covid19india.org/";
    private List<CasesTimeSeriesItem> timeWise;
    private List<StatewiseItem> stateWise;
    private Intent allStateStats;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        anim = findViewById(R.id.homeAnim);

    }


    public void goToStats(View view) {

        anim.setVisibility(View.VISIBLE);
        anim.playAnimation();
        fetchData();

    }

    public void startActivity(){
        allStateStats = new Intent(this, All_India_Stats.class);
        allStateStats.putExtra("STATE_LIST", (Serializable) stateWise);

        anim.pauseAnimation();
        anim.setVisibility(View.INVISIBLE);

        startActivity(allStateStats);
    }



    public void fetchData() {

        // Deploying retrofit to fetch data for us.
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create()).build();

        APIClient client = retrofit.create(APIClient.class);
        Call<Response> call = client.getStats();

        // Response is js object containing 3 js Arrays - CasesTimeSeries, Statewise data and testing data.

        call.enqueue(new Callback<Response>() {

            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if (!response.isSuccessful()) {

                    Log.i("test", "Something went wrong " + response.code());
                    Toast.makeText(MainActivity.this, "Something Went Wrong!", Toast.LENGTH_SHORT).show();

                } else {

                    timeWise = response.body().getCasesTimeSeries();
                    stateWise = response.body().getStatewise();

                    Log.i("test", "Success " + response.code());
                    startActivity();
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Log.i("test", "Something went Wrong");
            }
        });
    }
}