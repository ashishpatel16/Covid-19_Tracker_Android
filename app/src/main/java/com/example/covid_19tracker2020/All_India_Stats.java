package com.example.covid_19tracker2020;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.covid_19tracker2020.Adapter.MyAdapter;
import com.example.covid_19tracker2020.CovidAPI.APIClient;
import com.example.covid_19tracker2020.CovidAPI.Response;
import com.example.covid_19tracker2020.CovidAPI.StatewiseItem;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class All_India_Stats extends AppCompatActivity {

    List<StatewiseItem> stateWise;
    private final String baseURL = "https://api.covid19india.org/";
    RecyclerView recyclerView;
    Bundle extras;
    SwipeRefreshLayout refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all__india__stats);
        refresh = findViewById(R.id.swipeToRefresh);

        extras = getIntent().getExtras();

        stateWise = (List<StatewiseItem>) extras.getSerializable("STATE_LIST");

        recyclerView = findViewById(R.id.recyclerView);
        setAdapter();

        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
                refresh.setRefreshing(false);
                Toast.makeText(All_India_Stats.this, "Updated!", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void refresh() {
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
                    Toast.makeText(All_India_Stats.this, "Something Went Wrong!", Toast.LENGTH_SHORT).show();

                } else {

                    stateWise = response.body().getStatewise();
                    Log.i("test", "Success " + response.code());
                    for(int i=0;i<stateWise.size();i++) Log.i("ashish",i+" : "+stateWise.get(i).getState());
                    setAdapter();

                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Log.i("test", "Something went Wrong");
            }
        });
    }

    private void setAdapter() {
        MyAdapter adapter = new MyAdapter(this,stateWise);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}