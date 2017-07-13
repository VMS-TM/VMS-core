package vms.models.groups;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;

public class Response {

	@JsonProperty("count")
	private int count;

	public int getCount() { return this.count; }

	public void setCount(int count) { this.count = count; }

	@JsonProperty("items")
	private ArrayList<Group> items;

	public ArrayList<Group> getItems() { return this.items; }

	public void setItems(ArrayList<Group> items) { this.items = items; }
}
