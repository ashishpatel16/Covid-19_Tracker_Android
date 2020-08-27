package com.example.covid_19tracker2020.CovidAPI;

import com.google.gson.annotations.SerializedName;

public class CasesTimeSeriesItem{

	@SerializedName("date")
	private String date;

	@SerializedName("dailyrecovered")
	private String dailyrecovered;

	@SerializedName("totalconfirmed")
	private String totalconfirmed;

	@SerializedName("totaldeceased")
	private String totaldeceased;

	@SerializedName("dailydeceased")
	private String dailydeceased;

	@SerializedName("totalrecovered")
	private String totalrecovered;

	@SerializedName("dailyconfirmed")
	private String dailyconfirmed;

	public String getDate(){
		return date;
	}

	public String getDailyrecovered(){
		return dailyrecovered;
	}

	public String getTotalconfirmed(){
		return totalconfirmed;
	}

	public String getTotaldeceased(){
		return totaldeceased;
	}

	public String getDailydeceased(){
		return dailydeceased;
	}

	public String getTotalrecovered(){
		return totalrecovered;
	}

	public String getDailyconfirmed(){
		return dailyconfirmed;
	}
}