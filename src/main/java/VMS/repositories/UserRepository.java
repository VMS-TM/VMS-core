package vms.repositories;

import org.springframework.data.repository.CrudRepository;
import vms.models.User;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findByUsername(String username);
}
