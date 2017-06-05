package vms.onbootloader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import vms.models.Property;
import vms.models.ProxyServer;
import vms.models.Role;
import vms.models.User;
import vms.models.rawgroup.Group;
import org.apache.log4j.Logger;
import vms.scheduling.Rule;
import vms.scheduling.TaskStatus;
import vms.services.absr.*;

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
    private PropertyService propertyService;
    @Autowired
    private RuleService ruleService;

    @Autowired
    private ProxyServerService proxyServerService;

    private Logger log = Logger.getLogger(OnBootLoader  .class);



    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        loadGroups();
        loadUsers();
        loadProxyServers();
        loadProperty();
        loadRules();
    }

    private void loadProperty() {
        Property RateProperty = new Property();
        RateProperty.setName("rate");
        RateProperty.setValue("30000");
        propertyService.addProperty(RateProperty);

        Property CronProperty = new Property();
        CronProperty.setName("CronProperty");
        CronProperty.setValue("*/3 * * * * *");
        propertyService.addProperty(CronProperty);
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

    private void loadRules(){
        Rule rule = new Rule();

        rule.setName("1rule");
        rule.setCronExpr("*/11 * * * * *");
        rule.setKeyWords("снять");
        rule.setGroups((List<Group>) groupService.listAllVkGroups());
        rule.setStatus(TaskStatus.STOP);

        ruleService.addRule(rule);
    }

    private void loadProxyServers(){
        ProxyServer firstProxyServer = new ProxyServer("mail@gmail.com", "D3i7&1488",
                "808bcfd51bd94b4d0593a2dda57037fc4fdc46cac46e20d1b260c1a90d88b4c23023dd977e9639f7f8279",
                "49.86.143.1", 8090L);

        ProxyServer secondProxyServer = new ProxyServer("+79858838221", "485?frR",
                "5b9f338a526931a178f79a13c11bd0595b183532e4ad2da2e26caba388012ce21387c8322b715348e6df8",
                "55.204.100.108", 13801L);

        proxyServerService.addProxyServer(firstProxyServer);
        log.info("Saved proxy server with ip:" + firstProxyServer.getIp());
        proxyServerService.addProxyServer(secondProxyServer);
        log.info("Saved proxy server with ip:" + secondProxyServer.getIp());
    }
}

