package vms.models.postenvironment.attachmentenv;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AttachmentContainer implements Serializable {

  @JsonProperty("type")
  private String type;

  @JsonProperty("album")
  private Album album;

  @JsonProperty("photo")
  private Photo photo;

  public String getType() { return this.type; }

  public void setType(String type) { this.type = type; }

  public Photo getPhoto() { return this.photo; }

  public void setPhoto(Photo photo) { this.photo = photo; }

  public Album getAlbum() { return this.album; }

  public void setAlbum(Album album) { this.album = album; }
}