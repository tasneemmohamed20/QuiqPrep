package com.example.fragmentsbonus.models.countries;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CountriesResponse{
	@SerializedName("meals")

	private List<CountryItem> countries;

	public List<CountryItem> getCountries(){
		return countries;
	}
}