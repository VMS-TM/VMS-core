package vms.services.impl.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vms.models.security.User;
import vms.repositories.security.UserRepository;
import vms.services.absr.security.UserService;

import java.util.List;


@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Override
	public List<User> listAllUsers() {
		return (List<User>) userRepository.findAll();
	}

	@Override
	public User getUserById(Integer id) {
		return userRepository.findOne(id);
	}

	@Override
	public void saveOrUpdateUser(User user) {
		userRepository.save(user);
	}

	@Override
	public void deleteUser(Integer id) {
		userRepository.delete(id);
	}

	@Override
	public User getByUsername(String username) {
		return userRepository.findByUsername(username);
	}
}
