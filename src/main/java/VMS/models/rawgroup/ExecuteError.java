package vms.models.rawgroup;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by magic on 16.05.2017.
 */
public class ExecuteError
{
    @JsonProperty("method")
    private String method;

    public String getMethod() { return this.method; }

    public void setMethod(String method) { this.method = method; }

    @JsonProperty("error_code")
    private int error_code;

    public int getErrorCode() { return this.error_code; }

    public void setErrorCode(int error_code) { this.error_code = error_code; }

    @JsonProperty("error_msg")
    private String error_msg;

    public String getErrorMsg() { return this.error_msg; }

    public void setErrorMsg(String error_msg) { this.error_msg = error_msg; }
}
