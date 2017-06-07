package vms.repositories;

import org.springframework.data.repository.CrudRepository;
import vms.models.usersenvironment.UserFromVK;

public interface UsersFromVkRepository extends CrudRepository<UserFromVK, Long> {

}
