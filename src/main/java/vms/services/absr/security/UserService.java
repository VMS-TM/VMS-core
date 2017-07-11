package vms.services.absr.security;

import vms.models.security.User;


public interface UserService {
	Iterable<User> listAllUsers();

	User getUserById(Integer id);

	void saveOrUpdateUser(User user);

	void deleteUser(Integer id);

	User getByUsername(String username);
}
