package vms.models;

import vms.models.rawpost.Photo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name="post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "text")
    private String text;
    @Column(name = "photos")
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Photo2> attachments;

    public List<Photo2> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Photo2> attachments) {
        this.attachments = attachments;
    }

    public Post() {


    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }



    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
