package vms.models.countryenvironment;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CountryRootObject {
	@JsonProperty("response")
	private CountryResponse response;

	public CountryResponse getResponse() {
		return this.response;
	}

	public void setResponse(CountryResponse response) {
		this.response = response;
	}
}
