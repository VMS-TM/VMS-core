package vms.services.absr;

import vms.models.usersenvironment.UserFromVK;

import java.util.List;

public interface UserFromVkService {

	void deleteTableOfVkUsers();

	void addListOfUsersOfVK(List<UserFromVK> userFromVKList);

	UserFromVK getUserOfVkByID(Long id);

	List<UserFromVK> getAllUsersOfVk();
}
