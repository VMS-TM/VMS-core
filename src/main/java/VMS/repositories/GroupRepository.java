package vms.repositories;

import org.springframework.data.repository.CrudRepository;
import vms.models.rawgroup.Group;


/**
 * Created by magic on 17.05.2017.
 */
public interface GroupRepository extends CrudRepository<Group, String> {
}
