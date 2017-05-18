package vms.configs.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vms.models.User;
import vms.services.UserService;


/**
 * Created by SKYNET on 07.04.2017.
 */
@Service
public class AuthenticationService implements UserDetailsService {
    @Autowired
    private UserService service;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = service.getByUsername(username);

        if(user == null) {
            throw  new UsernameNotFoundException("Username " + username + " not found");
        }
        return user;
    }
}
