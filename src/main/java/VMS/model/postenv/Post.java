package VMS.model.postenv;

import VMS.model.postenv.attachmentenv.Attachment;
//import VMS.model.postenv.attachmentenv.CopyHistory;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Кирилл on 25.04.2017.
 */
@Entity
@Table(name = "posts")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Post implements Serializable{

    @Id
    @GeneratedValue
    @Column(name = "id")
    @JsonProperty("id")
    private int id;

    public int getId() { return this.id; }

    public void setId(int id) { this.id = id; }

    //id того кто подал
    @Column(name = "from_id")
    @JsonProperty("from_id")
    private int from_id;

    public int getFromId() { return this.from_id; }

    public void setFromId(int from_id) { this.from_id = from_id; }

    //владелец группа
    @Column(name = "owner_id")
    @JsonProperty("owner_id")
    private int owner_id;

    public int getOwnerId() { return this.owner_id; }

    public void setOwnerId(int owner_id) { this.owner_id = owner_id; }

    @Column(name = "date")
    @JsonProperty("date")
    private Date date;

    public Date getDate() { return this.date; }

    public void setDate(int date) {
        this.date = Date.from(Instant.ofEpochSecond((date))) ;
    }

    @Column(name = "post_id")
    @JsonProperty("post_id")
    private int post_id;

    public int getPostId() { return this.post_id; }

    public void setPostId(int post_id) { this.post_id = post_id; }

    @Column(name = "text")
    @JsonProperty("text")
    private String text;

    public String getText() { return this.text; }

    public void setText(String text) { this.text = text; }

    @JsonProperty("attachments")
    private ArrayList<Attachment> attachments;

    public ArrayList<Attachment> getAttachments() { return this.attachments; }

    public void setAttachments(ArrayList<Attachment> attachments) { this.attachments = attachments; }


    @Override
    public String toString() {
        return "Post{" +
                "date=" + date +
                ", post_id=" + post_id +
                ", text='" + text + '\'' +
                '}';
    }

    public Post() {
    }

    public Post(int from_id, int owner_id, int Date, int post_id, String text) {
        this.from_id = from_id;
        this.owner_id = owner_id;
        this.date = date;
        this.post_id = post_id;
        this.text = text;
    }
}
