package vms.models.groups;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import javax.persistence.*;

@Entity
@Table(name = "groups")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Group {

	@Id
	@JsonProperty("id")
	@Column(name = "id")
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JsonProperty("name")
	@Column(name = "name")
	private String name;

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty("screen_name")
	@Column(name = "screen_name")
	private String screen_name;

	public String getScreen_name() {
		return screen_name;
	}

	public void setScreen_name(String screen_name) {
		this.screen_name = screen_name;
	}

	@Transient
	@JsonProperty("is_closed")
	private int is_closed;

	public int getIsClosed() {
		return this.is_closed;
	}

	public void setIsClosed(int is_closed) {
		this.is_closed = is_closed;
	}

	@Transient
	@JsonProperty("type")
	private String type;

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Transient
	@JsonProperty("is_admin")
	private int is_admin;

	public int getIsAdmin() {
		return this.is_admin;
	}

	public void setIsAdmin(int is_admin) {
		this.is_admin = is_admin;
	}

	@Transient
	@JsonProperty("admin_level")
	private int admin_level;

	public int getAdminLevel() {
		return this.admin_level;
	}

	public void setAdminLevel(int admin_level) {
		this.admin_level = admin_level;
	}

	@Transient
	@JsonProperty("is_member")
	private int is_member;

	public int getIsMember() {
		return this.is_member;
	}

	public void setIsMember(int is_member) {
		this.is_member = is_member;
	}

	@JsonProperty("members_count")
	@Column(name = "members_count")
	private int members_count;

	public int getMembers_count() {
		return members_count;
	}

	public void setMembers_count(int members_count) {
		this.members_count = members_count;
	}

	@Transient
	@JsonProperty("photo_50")
	private String photo_50;

	public String getPhoto50() {
		return this.photo_50;
	}

	public void setPhoto50(String photo_50) {
		this.photo_50 = photo_50;
	}

	@Transient
	@JsonProperty("photo_100")
	private String photo_100;

	public String getPhoto100() {
		return this.photo_100;
	}

	public void setPhoto100(String photo_100) {
		this.photo_100 = photo_100;
	}

	@Transient
	@JsonProperty("photo_200")
	private String photo_200;

	public String getPhoto200() {
		return this.photo_200;
	}

	public void setPhoto200(String photo_200) {
		this.photo_200 = photo_200;
	}

	public Group() {
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Group group = (Group) o;

		if (name != null ? !name.equals(group.name) : group.name != null) return false;
		return screen_name != null ? screen_name.equals(group.screen_name) : group.screen_name == null;
	}

	@Override
	public int hashCode() {
		int result = name != null ? name.hashCode() : 0;
		result = 31 * result + (screen_name != null ? screen_name.hashCode() : 0);
		return result;
	}
}