package vms.services;

import vms.models.rawgroup.Group;


public interface GroupService {

    Iterable<Group> listAllVkGroups();

    Group getGroupById(String id);

    void saveGroup(Group user);

    void deleteGroupById(String id);
}
