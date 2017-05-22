package vms.models.rawpost;

import org.codehaus.jackson.annotate.JsonProperty;

public class Reposts
{
    @JsonProperty("count")
    private int count;

    public int getCount() { return this.count; }

    public void setCount(int count) { this.count = count; }
    @JsonProperty("user_reposted")
    private int user_reposted;

    public int getUserReposted() { return this.user_reposted; }

    public void setUserReposted(int user_reposted) { this.user_reposted = user_reposted; }
}
