package vms.services;


import vms.models.Role;

/**
 * Created by magic on 26.04.2017.
 */
public interface RoleService {
    Iterable<Role> listAllRoles();

    Role getRoleById(Integer id);

    void saveOrUpdateRole(Role user);

    void deleteRole(Integer id);
}
