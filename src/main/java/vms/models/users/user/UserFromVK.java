package vms.models.users.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@Table(name = "UserFromVK")
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserFromVK {

	@Id
	@Column(name = "id")
	@JsonProperty("id")
	private Long idOfUser;

	@Transient
	@JsonProperty("first_name")
	private String firstName;

	@Transient
	@JsonProperty("last_name")
	private String lastName;

	public UserFromVK() {
	}

	public UserFromVK(Long idOfUser) {
		this.idOfUser = idOfUser;
	}

	public Long getIdOfUser() {
		return idOfUser;
	}

	public void setIdOfUser(Long idOfUser) {
		this.idOfUser = idOfUser;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		UserFromVK that = (UserFromVK) o;

		if (idOfUser != that.idOfUser) return false;
		if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
		return lastName != null ? lastName.equals(that.lastName) : that.lastName == null;
	}

	@Override
	public int hashCode() {
		int result = (int) (idOfUser ^ (idOfUser >>> 32));
		result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
		result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
		return result;
	}
}
