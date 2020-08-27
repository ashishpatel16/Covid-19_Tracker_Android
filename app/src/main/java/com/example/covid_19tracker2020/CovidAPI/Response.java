package com.example.covid_19tracker2020.CovidAPI;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Response{

	@SerializedName("cases_time_series")
	private List<CasesTimeSeriesItem> casesTimeSeries;

	@SerializedName("statewise")
	private List<StatewiseItem> statewise;

	public List<CasesTimeSeriesItem> getCasesTimeSeries(){
		return casesTimeSeries;
	}

	public List<StatewiseItem> getStatewise(){
		return statewise;
	}
}