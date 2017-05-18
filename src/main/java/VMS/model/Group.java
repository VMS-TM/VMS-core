package VMS.model;

import javax.persistence.*;

@Entity
@Table(name = "groups")
public class Group {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "id_vk", unique = true)
    private Long id_vk;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "screen_name",  unique = true)
    private String screen_name;

    @Column(name = "is_closed")
    private short is_closed;

    @Column(name = "deactivated")
    private String deactivated;

    @Column(name = "type")
    private String type;

    @Column(name = "photo_50")
    private String photo_50;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @Column(name = "description")
    private String description;

    public Group() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId_vk() {
        return id_vk;
    }

    public void setId_vk(Long id_vk) {
        this.id_vk = id_vk;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScreen_name() {
        return screen_name;
    }

    public void setScreen_name(String screen_name) {
        this.screen_name = screen_name;
    }

    public short getIs_closed() {
        return is_closed;
    }

    public void setIs_closed(short is_closed) {
        this.is_closed = is_closed;
    }

    public String getDeactivated() {
        return deactivated;
    }

    public void setDeactivated(String deactivated) {
        this.deactivated = deactivated;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhoto_50() {
        return photo_50;
    }

    public void setPhoto_50(String photo_50) {
        this.photo_50 = photo_50;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
