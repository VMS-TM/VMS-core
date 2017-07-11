package vms.services.absr.users;

import vms.models.users.user.UserFromVK;

import java.util.List;

public interface UserFromVkService {

	void deleteTableOfVkUsers();

	void addListOfUsersOfVK(List<UserFromVK> userFromVKList);

	UserFromVK getUserOfVkByID(Long id);

	List<UserFromVK> getAllUsersOfVk();
}
