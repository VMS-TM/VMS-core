package vms.models.postenvironment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import vms.models.postenvironment.attachmentenv.AttachmentContainer;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

@Entity
@Table(name = "posts")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Post implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id")
    @JsonProperty("id")
    public int id;

    //id of users
    @Column(name = "from_id")
    @JsonProperty("from_id")
    private int from_id;
    public Post() {
    }

    public int getId() { return this.id; }

    public void setId(int id) { this.id = id; }

    //id of groups(source)
    @Column(name = "owner_id")
    @JsonProperty("owner_id")
    private int owner_id;

    @Column(name = "date")
    @JsonProperty("date")
    private Date date;

    @Column(name = "post_id")
    @JsonProperty("post_id")
    private int post_id;

    @Column(name = "text",length=32536)
    @JsonProperty("text")
    private String text;

    @JsonProperty("attachmentContainers")
    private ArrayList<AttachmentContainer> attachmentContainers;

    public int getFromId() { return this.from_id; }

    public void setFromId(int from_id) { this.from_id = from_id; }

    public int getOwnerId() { return this.owner_id; }

    public void setOwnerId(int owner_id) { this.owner_id = owner_id; }

    public Date getDate() { return this.date; }

    public void setDate(int date) {
        this.date = Date.from(Instant.ofEpochSecond((date))) ;
    }

    public int getPostId() { return this.post_id; }

    public void setPostId(int post_id) { this.post_id = post_id; }

    public String getText() { return this.text; }

    public void setText(String text) { this.text = text; }

    public ArrayList<AttachmentContainer> getAttachmentContainers() { return this.attachmentContainers; }

    public void setAttachmentContainers(ArrayList<AttachmentContainer> attachmentContainers) { this.attachmentContainers = attachmentContainers; }

    @Override
    public String toString() {
        return "Post{" +
                "date=" + date +
                ", post_id=" + post_id +
                ", text='" + text + '\'' +
                '}';
    }

    public Post(int from_id, int owner_id, int Date, int post_id, String text) {
        this.from_id = from_id;
        this.owner_id = owner_id;
        this.date = date;
        this.post_id = post_id;
        this.text = text;
    }
}