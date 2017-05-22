package vms.services;


import vms.models.Role;


public interface RoleService {
    Iterable<Role> listAllRoles();

    Role getRoleById(Integer id);

    void saveOrUpdateRole(Role user);

    void deleteRole(Integer id);
}
