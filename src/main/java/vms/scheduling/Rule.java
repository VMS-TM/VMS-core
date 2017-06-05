package vms.scheduling;

import vms.models.rawgroup.Group;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@Entity
@Table(name = "rules")
public class Rule {

	@Id
	@GeneratedValue
	@Column(name = "id")
	Long id;

	@Column(name = "keyWords")
	String keyWords;

	@OneToMany(targetEntity=Group.class, fetch=FetchType.EAGER)
	List<Group> groups = new ArrayList<Group>();

	@Column(name = "cronExpr")
	String cronExpr;

	@Column(name = "name")
	String name;

	@Column
	TaskStatus status;

	public TaskStatus getStatus() {
		return status;
	}

	public void setStatus(TaskStatus status) {
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}

	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	public String getCronExpr() {
		return cronExpr;
	}

	public void setCronExpr(String cronExpr) {
		this.cronExpr = cronExpr;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
