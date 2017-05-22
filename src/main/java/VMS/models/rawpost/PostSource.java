package vms.models.rawpost;

import org.codehaus.jackson.annotate.JsonProperty;


public class PostSource
{
    @JsonProperty("type")
    private String type;

    public String getType() { return this.type; }

    public void setType(String type) { this.type = type; }

    @JsonProperty("platform")
    private String platform;

    public String getPlatform() { return this.platform; }

    public void setPlatform(String platform) { this.platform = platform; }
}