package vms.models.rawpost;

import org.codehaus.jackson.annotate.JsonProperty;


public class Comments
{
    @JsonProperty("count")
    private int count;

    public int getCount() { return this.count; }

    public void setCount(int count) { this.count = count; }

    @JsonProperty("can_post")
    private int can_post;

    public int getCanPost() { return this.can_post; }

    public void setCanPost(int can_post) { this.can_post = can_post; }
}
