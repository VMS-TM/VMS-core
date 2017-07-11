package vms.services.absr;

import vms.models.rawgroup.Group;

import java.util.List;

public interface GroupService {

	List<Group> listAllVkGroups();

	Group getGroupById(String id);

	void saveGroup(Group user);

	void saveGroups(List<Group> groups);

	void deleteGroupById(String id);
}
