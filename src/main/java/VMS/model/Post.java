package VMS.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Кирилл on 25.04.2017.
 */
@Entity
@Table(name = "posts")
public class Post implements Serializable{

    @Id
    @GeneratedValue
    @Column(name = "id")

    private Long id;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "from_id")
    private int from_id;

    public int getFromId() { return this.from_id; }

    public void setFromId(int from_id) { this.from_id = from_id; }

    @Column(name = "owner_id")
    private int owner_id;

    public int getOwnerId() { return this.owner_id; }

    public void setOwnerId(int owner_id) { this.owner_id = owner_id; }

    @Column(name = "date")
    private int date;

    public int getDate() { return this.date; }

    public void setDate(int date) { this.date = date; }

    @Column(name = "post_id")
    private int post_id;

    public int getPostId() { return this.post_id; }

    public void setPostId(int post_id) { this.post_id = post_id; }

    @Column(name = "text")
    private String text;

    public String getText() { return this.text; }

    public void setText(String text) { this.text = text; }

    public Post(int from_id, int owner_id, int date, int post_id, String text) {
        this.from_id = from_id;
        this.owner_id = owner_id;
        this.date = date;
        this.post_id = post_id;
        this.text = text;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", from_id=" + from_id +
                ", owner_id=" + owner_id +
                ", date=" + date +
                ", post_id=" + post_id +
                ", text='" + text + '\'' +
                '}';
    }

    public Post() {
    }
}
