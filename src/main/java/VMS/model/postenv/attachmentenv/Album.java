package VMS.model.postenv.attachmentenv;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Album implements Serializable {
  private String id;

  @JsonProperty("thumb")
  private Thumb thumb;

  @JsonProperty("owner_id")
  private int owner_id;

  @JsonProperty("title")
  private String title;

  @JsonProperty("description")
  private String description;

  @JsonProperty("created")
  private int created;

  @JsonProperty("updated")
  private int updated;

  @JsonProperty("size")
  private int size;

  public String getId() { return this.id; }

  public void setId(String id) { this.id = id; }

  public Thumb getThumb() { return this.thumb; }

  public void setThumb(Thumb thumb) { this.thumb = thumb; }

  public int getOwnerId() { return this.owner_id; }

  public void setOwnerId(int owner_id) { this.owner_id = owner_id; }

  public String getTitle() { return this.title; }

  public void setTitle(String title) { this.title = title; }

  public String getDescription() { return this.description; }

  public void setDescription(String description) { this.description = description; }

  public int getCreated() { return this.created; }

  public void setCreated(int created) { this.created = created; }

  public int getUpdated() { return this.updated; }

  public void setUpdated(int updated) { this.updated = updated; }

  public int getSize() { return this.size; }

  public void setSize(int size) { this.size = size; }
}
