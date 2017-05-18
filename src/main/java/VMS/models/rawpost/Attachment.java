package vms.models.rawpost;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by magic on 04.05.2017.
 */
@JsonIgnoreProperties("link")
public class Attachment
{
    @JsonProperty("type")
    private String type;

    public String getType() { return this.type; }

    public void setType(String type) { this.type = type; }
    @JsonProperty("photo")
    private Photo photo;

    public Photo getPhoto() { return this.photo; }

    public void setPhoto(Photo photo) { this.photo = photo; }
}