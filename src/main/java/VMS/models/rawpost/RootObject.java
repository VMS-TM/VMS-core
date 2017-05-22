package vms.models.rawpost;

import org.codehaus.jackson.annotate.JsonProperty;


public class RootObject
{
    @JsonProperty("response")
    private Response response;

    public Response getResponse() { return this.response; }

    public void setResponse(Response response) { this.response = response; }
}
