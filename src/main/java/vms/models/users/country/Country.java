package vms.models.users.country;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Country {

	@JsonProperty("id")
	private int id;

	@JsonProperty("title")
	private String title;

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
