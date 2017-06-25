package vms.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "properties")
public class Property {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "name", unique = true)
	private String name;

	@NotNull
	@Column(name = "value")
	private String value;

	@NotNull
	@Pattern(regexp = "^[0-9]+$")
	@Column(name = "groupID")
	private String groupID;

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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getGroupID() {
		return groupID;
	}

	public void setGroupID(String groupID) {
		this.groupID = groupID;
	}

	public Property() {
	}

	public Property(String name, String value, String groupID) {
		this.name = name;
		this.value = value;
		this.groupID = groupID;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Property property = (Property) o;

		if (name != null ? !name.equals(property.name) : property.name != null) return false;
		if (value != null ? !value.equals(property.value) : property.value != null) return false;
		return groupID != null ? groupID.equals(property.groupID) : property.groupID == null;
	}

	@Override
	public int hashCode() {
		int result = name != null ? name.hashCode() : 0;
		result = 31 * result + (value != null ? value.hashCode() : 0);
		result = 31 * result + (groupID != null ? groupID.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "Property{" +
				"id=" + id +
				", name='" + name + '\'' +
				", value='" + value + '\'' +
				'}';
	}
}
