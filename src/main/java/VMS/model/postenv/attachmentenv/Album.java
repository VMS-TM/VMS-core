package VMS.model.postenv.attachmentenv;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Album implements Serializable
{
  private String id;

  public String getId() { return this.id; }

  public void setId(String id) { this.id = id; }

  @JsonProperty("thumb")
  private Thumb thumb;

  public Thumb getThumb() { return this.thumb; }

  public void setThumb(Thumb thumb) { this.thumb = thumb; }

  private int owner_id;

  public int getOwnerId() { return this.owner_id; }

  public void setOwnerId(int owner_id) { this.owner_id = owner_id; }

  private String title;

  public String getTitle() { return this.title; }

  public void setTitle(String title) { this.title = title; }

  private String description;

  public String getDescription() { return this.description; }

  public void setDescription(String description) { this.description = description; }

  private int created;

  public int getCreated() { return this.created; }

  public void setCreated(int created) { this.created = created; }

  private int updated;

  public int getUpdated() { return this.updated; }

  public void setUpdated(int updated) { this.updated = updated; }

  private int size;

  public int getSize() { return this.size; }

  public void setSize(int size) { this.size = size; }
}
