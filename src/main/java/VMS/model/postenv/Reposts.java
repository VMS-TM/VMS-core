package VMS.model.postenv;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Reposts
{
  private int count;

  public int getCount() { return this.count; }

  public void setCount(int count) { this.count = count; }

  private int user_reposted;

  public int getUserReposted() { return this.user_reposted; }

  public void setUserReposted(int user_reposted) { this.user_reposted = user_reposted; }
}