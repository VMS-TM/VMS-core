package VMS.model.postenv;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RootObject {
  @JsonProperty("response")
  private PostResponse postResponse;

  public PostResponse getPostResponse() { return this.postResponse; }

  public void setPostResponse(PostResponse postResponse) { this.postResponse = postResponse; }
}