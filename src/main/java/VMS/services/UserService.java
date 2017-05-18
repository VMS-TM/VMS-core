package vms.services;

import vms.models.User;

/**
 * Created by magic on 26.04.2017.
 */
public interface UserService {
    Iterable<User> listAllUsers();

    User getUserById(Integer id);

    void saveOrUpdateUser(User user);

    void deleteUser(Integer id);

    User getByUsername (String usrename);
}
