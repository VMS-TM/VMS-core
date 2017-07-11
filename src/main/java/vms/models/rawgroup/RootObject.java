package vms.models.rawgroup;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;


public class RootObject {

	@JsonProperty("response")
	private ArrayList<Group> group;

	@JsonProperty("execute_errors")
	private ArrayList<ExecuteError> execute_errors;

	public ArrayList<Group> getGroup() {
		return this.group;
	}

	public void setGroup(ArrayList<Group> group) {
		this.group = group;
	}

	public ArrayList<ExecuteError> getExecuteErrors() {
		return this.execute_errors;
	}

	public void setExecuteErrors(ArrayList<ExecuteError> execute_errors) {
		this.execute_errors = execute_errors;
	}

}