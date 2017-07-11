package vms.repositories.users;

import org.springframework.data.repository.CrudRepository;
import vms.models.users.user.UserFromVK;

public interface UsersFromVkRepository extends CrudRepository<UserFromVK, Long> {

}
