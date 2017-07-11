package vms.models.users.user;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class UsersResponse {

	@JsonProperty("count")
	private int count;

	@JsonProperty("items")
	private ArrayList<UserFromVK> userFromVKS;

	public int getCount() {
		return this.count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public ArrayList<UserFromVK> getItems() {
		return this.userFromVKS;
	}

	public void setItems(ArrayList<UserFromVK> userFromVKS) {
		this.userFromVKS = userFromVKS;
	}
}
