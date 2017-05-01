package VMS.model;

/**
 * Created by Skrezhet on 25.04.2017.
 */
public class Group {
    private Long id;

    private String name;

    private String screen_name;

    private Long is_closed;

    private String type;

    private Long is_admin;

    private Long is_member;

    private String photo_50;

    private String photo_100;

    private String photo_200;

    private Long can_post = 0L;

    private String deactivated = "";

    public Group() {
    }

    public Group(Long id, String name, String screen_name, Long is_closed, String type, Long is_admin, Long is_member, String photo_50, String photo_100, String photo_200) {
        this.id = id;
        this.name = name;
        this.screen_name = screen_name;
        this.is_closed = is_closed;
        this.type = type;
        this.is_admin = is_admin;
        this.is_member = is_member;
        this.photo_50 = photo_50;
        this.photo_100 = photo_100;
        this.photo_200 = photo_200;
    }

    public Group(Long id, String name, String screen_name, Long is_closed, String type, Long is_admin, Long is_member, String photo_50, String photo_100, String photo_200, Long can_post) {
        this.id = id;
        this.name = name;
        this.screen_name = screen_name;
        this.is_closed = is_closed;
        this.type = type;
        this.is_admin = is_admin;
        this.is_member = is_member;
        this.photo_50 = photo_50;
        this.photo_100 = photo_100;
        this.photo_200 = photo_200;
        this.can_post = can_post;
    }

    public Group(Long id, String name, String screen_name, Long is_closed, String type, Long is_admin, Long is_member, String photo_50, String photo_100, String photo_200, Long can_post, String deactivated) {
        this.id = id;
        this.name = name;
        this.screen_name = screen_name;
        this.is_closed = is_closed;
        this.type = type;
        this.is_admin = is_admin;
        this.is_member = is_member;
        this.photo_50 = photo_50;
        this.photo_100 = photo_100;
        this.photo_200 = photo_200;
        this.can_post = can_post;
        this.deactivated = deactivated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Group group = (Group) o;

        if (id != null ? !id.equals(group.id) : group.id != null) return false;
        if (name != null ? !name.equals(group.name) : group.name != null) return false;
        if (screen_name != null ? !screen_name.equals(group.screen_name) : group.screen_name != null) return false;
        if (is_closed != null ? !is_closed.equals(group.is_closed) : group.is_closed != null) return false;
        if (type != null ? !type.equals(group.type) : group.type != null) return false;
        if (is_admin != null ? !is_admin.equals(group.is_admin) : group.is_admin != null) return false;
        if (is_member != null ? !is_member.equals(group.is_member) : group.is_member != null) return false;
        if (photo_50 != null ? !photo_50.equals(group.photo_50) : group.photo_50 != null) return false;
        if (photo_100 != null ? !photo_100.equals(group.photo_100) : group.photo_100 != null) return false;
        if (photo_200 != null ? !photo_200.equals(group.photo_200) : group.photo_200 != null) return false;
        if (can_post != null ? !can_post.equals(group.can_post) : group.can_post != null) return false;
        return deactivated != null ? deactivated.equals(group.deactivated) : group.deactivated == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (screen_name != null ? screen_name.hashCode() : 0);
        result = 31 * result + (is_closed != null ? is_closed.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (is_admin != null ? is_admin.hashCode() : 0);
        result = 31 * result + (is_member != null ? is_member.hashCode() : 0);
        result = 31 * result + (photo_50 != null ? photo_50.hashCode() : 0);
        result = 31 * result + (photo_100 != null ? photo_100.hashCode() : 0);
        result = 31 * result + (photo_200 != null ? photo_200.hashCode() : 0);
        result = 31 * result + (can_post != null ? can_post.hashCode() : 0);
        result = 31 * result + (deactivated != null ? deactivated.hashCode() : 0);
        return result;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getIs_closed() {
        return is_closed;
    }

    public void setIs_closed(Long is_closed) {
        this.is_closed = is_closed;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getIs_admin() {
        return is_admin;
    }

    public void setIs_admin(Long is_admin) {
        this.is_admin = is_admin;
    }

    public Long getIs_member() {
        return is_member;
    }

    public void setIs_member(Long is_member) {
        this.is_member = is_member;
    }

    public String getPhoto_50() {
        return photo_50;
    }

    public void setPhoto_50(String photo_50) {
        this.photo_50 = photo_50;
    }

    public String getPhoto_100() {
        return photo_100;
    }

    public void setPhoto_100(String photo_100) {
        this.photo_100 = photo_100;
    }

    public String getPhoto_200() {
        return photo_200;
    }

    public void setPhoto_200(String photo_150) {
        this.photo_200 = photo_150;
    }

    public Long getCan_post() {
        return can_post;
    }

    public void setCan_post(Long can_post) {
        this.can_post = can_post;
    }

    public String getDeactivated() {
        return deactivated;
    }

    public void setDeactivated(String deactivated) {
        this.deactivated = deactivated;
    }
}
