package vms.onbootloader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import vms.models.Role;
import vms.models.User;
import vms.models.rawgroup.Group;
import org.apache.log4j.Logger;
import vms.services.absr.GroupService;
import vms.services.absr.RoleService;
import vms.services.absr.UserService;

import java.util.HashSet;
import java.util.Set;


@Component
public class OnBootLoader implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private GroupService groupService;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;


    private Logger log = Logger.getLogger(OnBootLoader  .class);



    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        loadGroups();
        loadUsers();
    }

    public void loadGroups() {

        Group first = new Group();
        first.setId("1111111");
        first.setName("Тестовая Группа 1");
        first.setScreenName("testgroup1");

        groupService.saveGroup(first);

        log.info("Saved second - id: " + first.getId());

        Group second = new Group();
        second.setId("2222222");
        second.setName("Тестовая Группа 2");
        second.setScreenName("testgroup2");

        groupService.saveGroup(second);

        log.info("Saved second - id:" + second.getId());
    }

    private void loadUsers() {
        Role role = new Role();
        role.setName("USER");
        roleService.saveOrUpdateRole(role);
        log.info("Saved role" + role.getName());
        Role adminRole = new Role();
        adminRole.setName("ADMIN");
        roleService.saveOrUpdateRole(adminRole);
        log.info("Saved role" + adminRole.getName());
        Set<Role> roleSet = new HashSet<Role>();

        roleSet.add(role);

        User user1 = new User();
        user1.setUsername("user");
        user1.setPassword("user");
        user1.setRoles(roleSet);
        userService.saveOrUpdateUser(user1);

        roleSet.add(adminRole);

        User user2 = new User();
        user2.setUsername("admin");
        user2.setPassword("admin");
        user2.setRoles(roleSet);
        userService.saveOrUpdateUser(user2);


    }




}

