package vms.services.impl.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vms.models.users.user.UserFromVK;
import vms.repositories.users.UsersFromVkRepository;
import vms.services.absr.users.UserFromVkService;

import java.util.List;

@Service
public class UserFromVkServiceImpl implements UserFromVkService {

	@Autowired
	private UsersFromVkRepository repository;

	@Override
	public void deleteTableOfVkUsers() {
		repository.deleteAll();
	}

	@Override
	public void addListOfUsersOfVK(List<UserFromVK> userFromVKList) {
		repository.save(userFromVKList);
	}

	@Override
	public UserFromVK getUserOfVkByID(Long id) {
		return repository.findOne(id);
	}

	@Override
	public List<UserFromVK> getAllUsersOfVk() {
		return (List<UserFromVK>) repository.findAll();
	}
}
