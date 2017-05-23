package VMS.model.postenv;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class PostResponse {

  @JsonProperty("count")
  private int count;

  public int getCount() { return this.count; }

  public void setCount(int count) { this.count = count; }

  @JsonProperty("items")
  private ArrayList<Post> posts;

  public ArrayList<Post> getPosts() { return this.posts; }

  public void setPosts(ArrayList<Post> posts) { this.posts = posts; }
}