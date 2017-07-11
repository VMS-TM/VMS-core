package vms.services.absr.security;


import vms.models.security.Role;

import java.util.Set;


public interface RoleService {
	Set<Role> listAllRoles();

	Role getRoleById(Integer id);

	void saveOrUpdateRole(Role user);

	void deleteRole(Integer id);
}
