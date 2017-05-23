package VMS.services.impl;

import VMS.dao.abstr.GroupRepo;
import VMS.model.Group;
import VMS.services.abstr.VkGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VkGroupServiceImpl implements VkGroupService{

	@Autowired
	private GroupRepo groupRepo;

	@Override
	public void addGroup(Group group) {
		groupRepo.save(group);
	}

	@Override
	public void deleteGroup(Group group) {
		groupRepo.delete(group);
	}

	@Override
	public void updateGroup(Group group) {
		groupRepo.save(group);
	}

	@Override
	public Group getGroupById(Long id) {
		return groupRepo.findOne(id);
	}

	@Override
	public List<Group> getAllGroupsFromDb() {
		return (List<Group>) groupRepo.findAll();
	}

	@Override
	public Group getUserByName(String name) {
		return groupRepo.getGroupByName(name);
	}

	@Override
	public Boolean isGroupExist(Group group) {
		return null;
	}

	@Override
	public List<Group> GroupsFromVkApi() {
		return null;
	}
}
