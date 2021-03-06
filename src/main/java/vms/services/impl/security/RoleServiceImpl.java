package vms.services.impl.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vms.models.security.Role;
import vms.repositories.security.RoleRepository;
import vms.services.absr.security.RoleService;

import java.util.Set;


@Service
@Transactional
public class RoleServiceImpl implements RoleService {

	@Autowired
	RoleRepository roleRepository;

	@Override
	public Set<Role> listAllRoles() {
		return (Set<Role>) roleRepository.findAll();
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
