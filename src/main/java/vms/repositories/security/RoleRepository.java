package vms.repositories.security;

import org.springframework.data.repository.CrudRepository;
import vms.models.security.Role;

public interface RoleRepository extends CrudRepository<Role, Integer> {

}
