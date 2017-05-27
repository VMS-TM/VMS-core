package vms.services.absr;

import vms.models.User;


public interface UserService {
    Iterable<User> listAllUsers();

    User getUserById(Integer id);

    void saveOrUpdateUser(User user);

    void deleteUser(Integer id);

    User getByUsername (String usrename);
}
