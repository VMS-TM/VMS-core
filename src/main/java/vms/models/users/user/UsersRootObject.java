package vms.models.users.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UsersRootObject {

	@JsonProperty("response")
	private UsersResponse response;

	public UsersResponse getResponse() {
		return this.response;
	}

	public void setResponse(UsersResponse response) {
		this.response = response;
	}
}
