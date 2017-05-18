package vms.services;

import vms.models.rawgroup.Group;

/**
 * Created by magic on 26.04.2017.
 */
public interface GroupService {

    Iterable<Group> listAllVkGroups();

    Group getGroupById(String id);

    void saveGroup(Group user);

    void deleteGroupById(String id);
}
