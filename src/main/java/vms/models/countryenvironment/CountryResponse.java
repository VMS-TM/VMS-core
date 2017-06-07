package vms.models.countryenvironment;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class CountryResponse {

	@JsonProperty("count")
	private int count;

	@JsonProperty("items")
	private ArrayList<Country> countries;

	public int getCount() {
		return this.count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public ArrayList<Country> getItems() {
		return this.countries;
	}

	public void setItems(ArrayList<Country> countries) {
		this.countries = countries;
	}
}
