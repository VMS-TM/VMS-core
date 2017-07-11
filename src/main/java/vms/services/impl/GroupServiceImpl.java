package vms.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vms.models.rawgroup.Group;
import vms.repositories.GroupRepository;
import vms.services.absr.GroupService;

import java.util.List;


@Service
@Transactional
public class GroupServiceImpl implements GroupService {

	@Autowired
	private GroupRepository GroupRepository;

	@Override
	public List<Group> listAllVkGroups() {
		return (List<Group>) GroupRepository.findAll();
	}

	@Override
	public Group getGroupById(String id) {
		return GroupRepository.findOne(id);
	}

	@Override
	public void saveGroup(Group group) {
		GroupRepository.save(group);
	}

	@Override
	public void deleteGroupById(String id) {
		GroupRepository.delete(id);
	}

	@Override
	public void saveGroups(List<Group> groups) {
		GroupRepository.save(groups);
	}
}
