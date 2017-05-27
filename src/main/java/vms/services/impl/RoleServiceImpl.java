package vms.services.impl;

        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Service;
        import org.springframework.transaction.annotation.Transactional;
        import vms.models.Role;
        import vms.repositories.RoleRepository;
        import vms.services.absr.RoleService;


@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository roleRepository;
    @Override
    public Iterable<Role> listAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role getRoleById(Integer id) {
        return roleRepository.findOne(id);
    }

    @Override
    public void saveOrUpdateRole(Role role) {
        roleRepository.save(role);
    }

    @Override
    public void deleteRole(Integer id) {
        roleRepository.delete(id);
    }
}
