package vms.repositories.groups;

import org.springframework.data.repository.CrudRepository;
import vms.models.groups.Group;


public interface GroupRepository extends CrudRepository<Group, Long> {
}
