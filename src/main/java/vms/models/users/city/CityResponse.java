package vms.models.users.city;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class CityResponse {

	@JsonProperty("count")
	private int count;

	@JsonProperty("items")
	private ArrayList<City> cities;

	public int getCount() {
		return this.count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public ArrayList<City> getItems() {
		return this.cities;
	}

	public void setItems(ArrayList<City> cities) {
		this.cities = cities;
	}
}
