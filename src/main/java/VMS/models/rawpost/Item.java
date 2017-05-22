package vms.models.rawpost;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;


@JsonIgnoreProperties({"copy_history", "likes","views"})
public class Item
{
    @JsonProperty("id")
    private int id;

    public int getId() { return this.id; }

    public void setId(int id) { this.id = id; }
    @JsonProperty("from_id")
    private int from_id;

    public int getFromId() { return this.from_id; }

    public void setFromId(int from_id) { this.from_id = from_id; }
    @JsonProperty("owner_id")
    private int owner_id;

    public int getOwnerId() { return this.owner_id; }

    public void setOwnerId(int owner_id) { this.owner_id = owner_id; }
    @JsonProperty("date")
    private int date;

    public int getDate() { return this.date; }

    public void setDate(int date) { this.date = date; }
    @JsonProperty("marked_as_ads")
    private int marked_as_ads;

    public int getMarkedAsAds() { return this.marked_as_ads; }

    public void setMarkedAsAds(int marked_as_ads) { this.marked_as_ads = marked_as_ads; }
    @JsonProperty("post_type")
    private String post_type;

    public String getPostType() { return this.post_type; }

    public void setPostType(String post_type) { this.post_type = post_type; }
    @JsonProperty("text")
    private String text;

    public String getText() { return this.text; }

    public void setText(String text) { this.text = text; }

    @JsonProperty("attachments")
    private ArrayList<Attachment> attachments;

    public ArrayList<Attachment> getAttachments() { return this.attachments; }

    public void setAttachments(ArrayList<Attachment> attachments) { this.attachments = attachments; }
    @JsonProperty("post_source")
    private PostSource post_source;

    public PostSource getPostSource() { return this.post_source; }

    public void setPostSource(PostSource post_source) { this.post_source = post_source; }

    @JsonProperty("comments")
    private Comments comments;

    public Comments getComments() { return this.comments; }

    public void setComments(Comments comments) { this.comments = comments; }

   // @JsonProperty("likes")
   // private Likes likes;

   // public Likes getLikes() { return this.likes; }

   // public void setLikes(Likes likes) { this.likes = likes; }

    @JsonProperty("reposts")
    private Reposts reposts;

    public Reposts getReposts() { return this.reposts; }

    public void setReposts(Reposts reposts) { this.reposts = reposts; }
}
