package vms.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vms.models.usersenvironment.UserFromVK;
import vms.repositories.UsersFromVkRepository;
import vms.services.absr.UserFromVkService;

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
