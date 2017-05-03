package VMS.model.postenv;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Comments
{
  private int count;

  public int getCount() { return this.count; }

  public void setCount(int count) { this.count = count; }

  private int can_post;

  public int getCanPost() { return this.can_post; }

  public void setCanPost(int can_post) { this.can_post = can_post; }
}