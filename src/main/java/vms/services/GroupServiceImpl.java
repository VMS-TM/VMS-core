package vms.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vms.models.rawgroup.Group;
import vms.repositories.GroupRepository;


@Service
@Transactional
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupRepository GroupRepository;

    @Override
    public Iterable<Group> listAllVkGroups() {
        return GroupRepository.findAll();
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
}
