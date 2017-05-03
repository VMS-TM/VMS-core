package VMS.model.postenv;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Likes
{
  private int count;

  public int getCount() { return this.count; }

  public void setCount(int count) { this.count = count; }

  private int user_likes;

  public int getUserLikes() { return this.user_likes; }

  public void setUserLikes(int user_likes) { this.user_likes = user_likes; }

  private int can_like;

  public int getCanLike() { return this.can_like; }

  public void setCanLike(int can_like) { this.can_like = can_like; }

  private int can_publish;

  public int getCanPublish() { return this.can_publish; }

  public void setCanPublish(int can_publish) { this.can_publish = can_publish; }
}