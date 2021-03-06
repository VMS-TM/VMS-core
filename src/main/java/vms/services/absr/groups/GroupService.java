package vms.services.absr.groups;

import vms.models.groups.Group;

import java.util.List;

public interface GroupService {

	List<Group> listAllVkGroups();

	Group getGroupById(Long id);

	void saveGroup(Group user);

	void saveGroups(List<Group> groups);

	void deleteGroupById(Long id);
}
