package vms.models.rawgroup;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;

/**
 * Created by magic on 13.05.2017.
 */
public class RootObject
{
    @JsonProperty("response")
    private ArrayList<Group> group;

    public ArrayList<Group> getGroup() { return this.group; }

    public void setGroup(ArrayList<Group> group) { this.group = group; }

    @JsonProperty("execute_errors")
    private ArrayList<ExecuteError> execute_errors;

    public ArrayList<ExecuteError> getExecuteErrors() { return this.execute_errors; }

    public void setExecuteErrors(ArrayList<ExecuteError> execute_errors) { this.execute_errors = execute_errors; }
}