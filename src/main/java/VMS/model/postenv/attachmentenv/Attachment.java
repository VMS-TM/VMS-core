package VMS.model.postenv.attachmentenv;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Attachment implements Serializable
{
  private String type;

  public String getType() { return this.type; }

  public void setType(String type) { this.type = type; }

  @JsonProperty("photo")
  private Photo photo;

  public Photo getPhoto() { return this.photo; }

  public void setPhoto(Photo photo) { this.photo = photo; }

  @JsonProperty("album")
  private Album album;

  public Album getAlbum() { return this.album; }

  public void setAlbum(Album album) { this.album = album; }
}