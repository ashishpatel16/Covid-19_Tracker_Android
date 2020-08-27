package com.example.covid_19tracker2020.CovidAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIClient {

    @GET("data.json")
    Call<Response> getStats();
}
