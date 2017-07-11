package vms.models.groups;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;


public class RootObject {

	@JsonProperty("response")
	private ArrayList<Group> response;

	@JsonProperty("execute_errors")
	private ArrayList<ExecuteError> execute_errors;

	public ArrayList<Group> getGroup() {
		return response;
	}

	public void setGroup(ArrayList<Group> response) {
		this.response = response;
	}

	public ArrayList<ExecuteError> getExecuteErrors() {
		return this.execute_errors;
	}

	public void setExecuteErrors(ArrayList<ExecuteError> execute_errors) {
		this.execute_errors = execute_errors;
	}

	@JsonProperty("error")
	private Error error;

	public Error getError() { return this.error; }

	public void setError(Error error) { this.error = error; }

}