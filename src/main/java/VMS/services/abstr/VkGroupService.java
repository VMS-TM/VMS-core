package VMS.services.abstr;

import VMS.model.Group;

import java.util.List;

/**
 * Created by Кирилл on 09.05.2017.
 */
public interface VkGroupService {
	void addGroup(Group group);

	void deleteGroup(Group group);

	void updateGroup(Group group);

	Group getGroupById(Long id);

	List<Group> getAllGroupsFromDb();

	Group getUserByName(String name);

	Boolean isGroupExist(Group group);

	List<Group> GroupsFromVkApi();
}
