package vms.onbootloader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import vms.models.ProxyServer;
import vms.models.Role;
import vms.models.User;
import vms.models.rawgroup.Group;
import org.apache.log4j.Logger;
import vms.models.usersenvironment.UserFromVK;
import vms.services.absr.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Component
public class OnBootLoader implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private GroupService groupService;
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;

	@Autowired
	private ProxyServerService proxyServerService;

	@Autowired
	private UserFromVkService userFromVkService;

	private Logger log = Logger.getLogger(OnBootLoader.class);


	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		loadGroups();
		loadUsers();
		loadProxyServers();
		loadUsersFromVK();
	}

	public void loadGroups() {

		Group first = new Group();
		first.setId("57466174");
		first.setName("Уютное гнездышко / поиск жилья и соседей / СПБ");
		first.setScreenName("yuytnoe_gnezdishko");

		groupService.saveGroup(first);

		log.info("Saved second - id: " + first.getId());
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

	private void loadProxyServers() {
		ProxyServer firstProxyServer = new ProxyServer("mail@gmail.com", "D3i7&1488",
				"dead6a659bc53060c5f9100288b01c6534501ba9496f5d8f49e2a944ce910f915d4aa7a278c59871e68dd",
				"87.228.29.154", 	53281);

		ProxyServer secondProxyServer = new ProxyServer("+79858838221", "485?frR",
				"bb6211359dc3e3eb8f6fe190d36e8a42ad054dd2e146ec845072dcd19377d474caafd648b3712e3254e54",
				"93.187.152.190", 8080);

		proxyServerService.addProxyServer(firstProxyServer);
		log.info("Saved proxy server with ip:" + firstProxyServer.getIp());
		proxyServerService.addProxyServer(secondProxyServer);
		log.info("Saved proxy server with ip:" + secondProxyServer.getIp());
	}

	private void loadUsersFromVK(){
		List<UserFromVK> userFromVKList = new ArrayList<>();
		userFromVKList.add(new UserFromVK(115470965L, "Алексей", "Прогнозов"));
		userFromVKList.add(new UserFromVK(149406594L, "Иван", "Николаев"));
		userFromVKList.add(new UserFromVK(3420130L, "Иван", "Зверев"));
		userFromVKList.add(new UserFromVK(13545169L, "Александр", "Вербицкий"));
		userFromVKList.add(new UserFromVK(11728669L, "Павел", "Романович"));
		userFromVKList.add(new UserFromVK(28267947L, "Артём", "Корчмит"));
		userFromVKList.add(new UserFromVK(13651954L, "Виталий", "Секерин"));
		userFromVKList.add(new UserFromVK(21701619L, "Сергей", "Хилько"));
		userFromVKList.add(new UserFromVK(20904513L, "Владимир", "Жаринов"));
		userFromVKList.add(new UserFromVK(118811155L, "Макс", "Чернов"));

		userFromVkService.addListOfUsersOfVK(userFromVKList);
		log.info("Add 10 users of vk in DB for tests");
	}
}

