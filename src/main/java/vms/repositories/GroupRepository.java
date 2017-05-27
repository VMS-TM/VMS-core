package vms.repositories;

import org.springframework.data.repository.CrudRepository;
import vms.models.rawgroup.Group;



public interface GroupRepository extends CrudRepository<Group, String> {
}
