package vms.repositories.security;

import org.springframework.data.repository.CrudRepository;
import vms.models.security.User;

public interface UserRepository extends CrudRepository<User, Integer> {
	User findByUsername(String username);
}
