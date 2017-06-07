package vms.models.cityenvironment;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CityRootObject {

	@JsonProperty("response")
	private CityResponse response;

	public CityResponse getResponse() {
		return this.response;
	}

	public void setResponse(CityResponse response) {
		this.response = response;
	}
}
