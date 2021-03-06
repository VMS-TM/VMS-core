package vms.startdata;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import vms.models.property.Property;
import vms.models.property.ProxyServer;
import vms.models.security.Role;
import vms.models.security.User;
import vms.models.users.user.UserFromVK;
import vms.services.absr.groups.GroupService;
import vms.services.absr.property.PropertyService;
import vms.services.absr.property.ProxyServerService;
import vms.services.absr.security.RoleService;
import vms.services.absr.security.UserService;
import vms.services.absr.users.UserFromVkService;

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

	@Autowired
	private PropertyService propertyService;

	private Logger log = Logger.getLogger(OnBootLoader.class);


	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
//		loadGroups();
		loadUsers();
//		loadProxyServers();
//		loadUsersFromVK();
		loadProperty();
	}

	private void loadGroups() {

//		Group first = new Group();
//		first.setId(57466174L);
//		first.setName("Уютное гнездышко / поиск жилья и соседей / СПБ");
//		first.setScreenName("yuytnoe_gnezdishko");
//
//		Group second = new Group();
//		second.setId(10298741L);
//		second.setName("Аренда");
//		second.setScreenName("rentalproperty");
//
//		Group third = new Group();
//		third.setId("1850339");
//		third.setName("СНЯТЬ АРЕНДА БЕЗ ПОСРЕДНИКОВ КВАРТИР КОМНАТ СПБ");
//		third.setScreenName("bezco");
//
//		Group fourth = new Group();
//		fourth.setId("156782");
//		fourth.setName("Аренда Сдам Сниму квартиру Продам Недвижимость");
//		fourth.setScreenName("sgrealty");
//
//		Group fifth = new Group();
//		fifth.setId("78083213");
//		fifth.setName("Аренда Воронеж");
//		fifth.setScreenName("vrn_d");
//
//		Group sixth = new Group();
//		sixth.setId("31300139");
//		sixth.setName("Аренда Воронеж");
//		sixth.setScreenName("vrn_v136");
//
//		Group seventh = new Group();
//		seventh.setId("47196429");
//		seventh.setName("Аренда квартир в Новосибирске");
//		seventh.setScreenName("arenda_kvartir_lidernsk");
//
//		Group eighth = new Group();
//		eighth.setId("22139213");
//		eighth.setName("Аренда без посредников (квартиры, комнаты) КИЕВ");
//		eighth.setScreenName("arenda_bez_posrednikov");
//
//		Group ninth = new Group();
//		ninth.setId("39171044");
//		ninth.setName("Аренда квартир в Новосибирске");
//		ninth.setScreenName("r0yalh0use");
//
//		Group tenth = new Group();
//		tenth.setId("43752758");
//		tenth.setName("СНЯТЬ КВАРТИРУ БЕЗ АГЕНТОВ АРЕНДА КОМНАТ МОСКВА");
//		tenth.setScreenName("bezco_ru");
//
//		Group eleventh = new Group();
//		eleventh.setId("51271270");
//		eleventh.setName("Аренда квартир без посредников — Екатеринбург");
//		eleventh.setScreenName("apartments_ekb");
//
//		Group twelfth = new Group();
//		twelfth.setId("2986749");
//		twelfth.setName("СДАМ, СНИМУ, АРЕНДА");
//		twelfth.setScreenName("sdam.snimy.arenda");
//
//		Group thirteenth = new Group();
//		thirteenth.setId("50961614");
//		thirteenth.setName("Аренда без посредников (квартиры, комнаты) Киев");
//		thirteenth.setScreenName("arenda_bez_brokera");
//
//		Group fourteenth = new Group();
//		fourteenth.setId("89353072");
//		fourteenth.setName("АРЕНДА, ПРОДАЖА КВАРТИР, ДОМОВ, КОТТЕДЖЕЙ");
//		fourteenth.setScreenName("aspnedvizhimost");
//
//		Group fifteenth = new Group();
//		fifteenth.setId("105543780");
//		fifteenth.setName("Аренда Красноярск");
//		fifteenth.setScreenName("club105543780");
//
//		Group sixteenth = new Group();
//		sixteenth.setId("62069824");
//		sixteenth.setName("ЖИЛЬЕ в Москве БЕЗ ПОСРЕДНИКОВ Аренда Сдам Снять");
//		sixteenth.setScreenName("rentm");
//
//		Group seventeenth = new Group();
//		seventeenth.setId("28618880");
//		seventeenth.setName("Аренда квартир без комиссии в Санкт-Петербурге");
//		seventeenth.setScreenName("help17");
//
//		Group eighteenth = new Group();
//		eighteenth.setId("95008079");
//		eighteenth.setName("Киев Аренда Квартир");
//		eighteenth.setScreenName("arenda_044");
//
//		Group nineteenth = new Group();
//		nineteenth.setId("10957202");
//		nineteenth.setName("Продажа и аренда недвижимости в Санкт-Петербурге");
//		nineteenth.setScreenName("an_arin");
//
//		Group twentieth = new Group();
//		twentieth.setId("13779725");
//		twentieth.setName("Посуточная аренда квартир в Санкт-Петербурге");
//		twentieth.setScreenName("arendaposutochno");
//
//		Group twetyFirst = new Group();
//		twetyFirst.setId("76867861");
//		twetyFirst.setName("АРЕНДА Красноярск");
//		twetyFirst.setScreenName("club76867861");
//
//		Group twetySecond = new Group();
//		twetySecond.setId("60082269");
//		twetySecond.setName("Аренда Снять Сниму Сдать квартиру комнату в СПб");
//		twetySecond.setScreenName("club60082269");
//
//		Group twetyThird = new Group();
//		twetyThird.setId("49428949");
//		twetyThird.setName("Снять квартиру | Аренда без посредников Москва");
//		twetyThird.setScreenName("novoseliye");
//
//		Group twetyFourth = new Group();
//		twetyFourth.setId("694012");
//		twetyFourth.setName("Аренда квартир без посредников в Новосибирске");
//		twetyFourth.setScreenName("dom54");
//
//		Group twetyFifth = new Group();
//		twetyFifth.setId("22298919");
//		twetyFifth.setName("Сдам, сниму, аренда, квартиру, комнату в СПб");
//		twetyFifth.setScreenName("rent_sankt_peterburg");


//		groupService.saveGroup(first);
//		groupService.saveGroup(second);
//		groupService.saveGroup(third);
//		groupService.saveGroup(fourth);
//		groupService.saveGroup(fifth);
//		groupService.saveGroup(sixth);
//		groupService.saveGroup(seventh);
//		groupService.saveGroup(eighth);
//		groupService.saveGroup(ninth);
//		groupService.saveGroup(tenth);
//		groupService.saveGroup(eleventh);
//		groupService.saveGroup(twelfth);
//		groupService.saveGroup(thirteenth);
//		groupService.saveGroup(fourteenth);
//		groupService.saveGroup(fifteenth);
//		groupService.saveGroup(sixteenth);
//		groupService.saveGroup(seventeenth);
//		groupService.saveGroup(eighteenth);
//		groupService.saveGroup(nineteenth);
//		groupService.saveGroup(twelfth);
//		groupService.saveGroup(twetyFirst);
//		groupService.saveGroup(twetySecond);
//		groupService.saveGroup(twetyThird);
//		groupService.saveGroup(twetyFourth);
//		groupService.saveGroup(twetyFifth);
//
//		log.info("Saved second - id: " + first.getId());
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
				"bb6211359dc3e3eb8f6fe190d36e8a42ad054dd2e146ec845072dcd19377d474caafd648b3712e3254e54",
				"185.104.14.188", 8000, "user");

		ProxyServer secondProxyServer = new ProxyServer("+79858838221", "485?frR",
				"dead6a659bc53060c5f9100288b01c6534501ba9496f5d8f49e2a944ce910f915d4aa7a278c59871e68dd",
				"185.104.15.165", 8000, "user");

		ProxyServer thirdProxyServer = new ProxyServer("mail@gmail.ru", "32167666",
				"53c2207ef83c37171713a6e97560c819af52b44537dbc15c0451b661d5316432384e95fc24f343a88c134",
				"185.108.215.247", 53281, "groups");

		ProxyServer fourthProxyServer = new ProxyServer("+79858838212", "23523423",
				"5d866b1300b52bd30eacd405e05a3ed90673f31f349072cf16a616ee02e8ad1a22c3eccdfe21e1f40dc88",
				"77.73.64.12", 8080, "groups");

		ProxyServer fifthProxyServer = new ProxyServer("+7354223438212", "27533423",
				"f0874a39a169ec6e0b35749c71cdcecc7da034205785e5d622c173454ff95b4532cbf6bf20bf924f365e4",
				"91.218.84.4", 3128, "groups");


		proxyServerService.addProxyServer(firstProxyServer);
		log.info("Saved proxy server with ip:" + firstProxyServer.getIp());
		proxyServerService.addProxyServer(secondProxyServer);
		log.info("Saved proxy server with ip:" + secondProxyServer.getIp());
		proxyServerService.addProxyServer(thirdProxyServer);
		log.info("Saved proxy server with ip:" + thirdProxyServer.getIp());
		proxyServerService.addProxyServer(fourthProxyServer);
		log.info("Saved proxy server with ip:" + fourthProxyServer.getIp());
		proxyServerService.addProxyServer(fifthProxyServer);
		log.info("Saved proxy server with ip:" + fifthProxyServer.getIp());
	}

	private void loadUsersFromVK() {
		List<UserFromVK> userFromVKList = new ArrayList<>();
		userFromVKList.add(new UserFromVK(115470965L));
		userFromVKList.add(new UserFromVK(149406594L));
		userFromVKList.add(new UserFromVK(3420130L));
		userFromVKList.add(new UserFromVK(13545169L));
		userFromVKList.add(new UserFromVK(11728669L));
		userFromVKList.add(new UserFromVK(28267947L));
		userFromVKList.add(new UserFromVK(13651954L));
		userFromVKList.add(new UserFromVK(21701619L));
		userFromVKList.add(new UserFromVK(20904513L));
		userFromVKList.add(new UserFromVK(118811155L));
		userFromVKList.add(new UserFromVK(272354645L));
		userFromVKList.add(new UserFromVK(3842659L));
		userFromVKList.add(new UserFromVK(7617438L));
		userFromVKList.add(new UserFromVK(21035641L));
		userFromVKList.add(new UserFromVK(189954639L));
		userFromVKList.add(new UserFromVK(184206338L));
		userFromVKList.add(new UserFromVK(66074530L));
		userFromVKList.add(new UserFromVK(201351511L));
		userFromVKList.add(new UserFromVK(3248243L));
		userFromVKList.add(new UserFromVK(74574027L));
		userFromVKList.add(new UserFromVK(201351511L));
		userFromVKList.add(new UserFromVK(419790750L));
		userFromVKList.add(new UserFromVK(7666105L));
		userFromVKList.add(new UserFromVK(331542473L));
		userFromVKList.add(new UserFromVK(381624104L));
		userFromVKList.add(new UserFromVK(19638L));

		userFromVkService.addListOfUsersOfVK(userFromVKList);
		log.info("Add 25 users of vk in DB for tests");
	}

	private void loadProperty() {

		Property property = new Property();
		property.setName("1");
		property.setValue("5677d81dc28dc7a62301f4545f716cfba838793c02d57785d1128783522d434ccb883c653425e2ac3b9da");
		property.setGroupID("148705229");
		propertyService.addProperty(property);
		log.info("Add 1 default property");

//		Property propertySecond = new Property();
//		propertySecond.setName("hey");
//		propertySecond.setValue("f0874a39a169ec6e0b35749c71cdcecc7da034205785e5d622c173454ff95b4532cbf6bf20bf924f365e4");
//		propertySecond.setGroupID("148705229");
//
//		propertyService.addProperty(propertySecond);
//		log.info("Add 2  properties");


	}
}

