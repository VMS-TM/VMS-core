package vms.models.rawgroup;

import org.codehaus.jackson.annotate.JsonProperty;


import javax.persistence.*;
import java.beans.Transient;
import java.util.ArrayList;

/**
 * Created by magic on 13.05.2017.
 */
@Entity
@Table(name = "groups")
public class Group
{

    @JsonProperty("id")
    @Id
    private String id;

    public String  getId() { return this.id; }

    public void setId(String  id) { this.id = id; }

    @JsonProperty("name")
    @Column(name = "name")
    private String name;

    public String getName() { return this.name; }

    public void setName(String name) { this.name = name; }

    @JsonProperty("screen_name")
    @Column(name = "screen_name")
    public String screen_name;

    public String getScreenName() { return this.screen_name; }

    public void setScreenName(String screen_name) { this.screen_name = screen_name; }

    @JsonProperty("is_closed")
    @javax.persistence.Transient  private int is_closed;

     public int getIsClosed() { return this.is_closed; }

    public void setIsClosed(int is_closed) { this.is_closed = is_closed; }

    @JsonProperty("type")
    @javax.persistence.Transient  private String type;

    public String getType() { return this.type; }

    public void setType(String type) { this.type = type; }


    @JsonProperty("members_count")
    @Column(name = "members_count")
    public int members_count;

    public int getMembersCount() { return this.members_count; }

    public void setMembersCount(int members_count) { this.members_count = members_count; }

    @JsonProperty("photo_50")
    @javax.persistence.Transient  private String photo_50;

    public String getPhoto50() { return this.photo_50; }

    public void setPhoto50(String photo_50) { this.photo_50 = photo_50; }

    @JsonProperty("photo_100")
    @javax.persistence.Transient  private String photo_100;

    public String getPhoto100() { return this.photo_100; }

    public void setPhoto100(String photo_100) { this.photo_100 = photo_100; }

    @JsonProperty("photo_200")
    @javax.persistence.Transient  private String photo_200;

    public String getPhoto200() { return this.photo_200; }

    public void setPhoto200(String photo_200) { this.photo_200 = photo_200; }
}