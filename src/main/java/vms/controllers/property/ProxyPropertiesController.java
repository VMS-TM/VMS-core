package vms.controllers.property;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import vms.models.property.ProxyServer;
import vms.services.absr.property.ProxyServerService;


@Controller
public class ProxyPropertiesController {

	@Autowired
	private ProxyServerService proxyServerService;

	private static final Logger logger = LoggerFactory.getLogger(ProxyPropertiesController.class);

	@RequestMapping(value = "/properties/proxy", method = RequestMethod.GET)
	public String propertiesProxyServers(ModelMap modelMap) {
		modelMap.addAttribute("servers", proxyServerService.proxyServerList());

		return "property/proxyProperties";
	}

	@RequestMapping(value = "/properties/proxy/group", method = RequestMethod.GET)
	public String propertiesProxyServersForGroup(ModelMap modelMap) {
		modelMap.addAttribute("servers", proxyServerService.proxyServerList());

		return "property/proxypropertiesforgroup";
	}

	@RequestMapping(value = "/properties/proxy", method = RequestMethod.POST)
	public String delProxyServers(@RequestParam(value = "id") Long id) {
		proxyServerService.deleteProxyServer(id);
		return "redirect:/properties/proxy";
	}

	@RequestMapping(value = "/properties/proxy/group", method = RequestMethod.POST)
	public String delProxyServersForGroup(@RequestParam(value = "id") Long id) {
		proxyServerService.deleteProxyServer(id);

		return "redirect:/properties/proxy/groups";
	}

	@RequestMapping(value = "/properties/proxy/group/add", method = RequestMethod.POST)
	public String addProxyServersForGroup(@Validated ProxyServer proxyServer,
										  BindingResult bindingResult,
										  @RequestParam(value = "login") String login,
										  @RequestParam(value = "password") String password,
										  @RequestParam(value = "token") String token,
										  @RequestParam(value = "ip") String ip,
										  @RequestParam(value = "port") Integer port,
										  @RequestParam(value = "destiny") String destiny) {

		if (bindingResult.hasErrors()) {
			return "redirect:/properties/proxy/groups?hasNull";
		}

		ProxyServer server = new ProxyServer(login, password, token, ip, port, destiny);

		try {
			proxyServerService.addProxyServer(server);
		} catch (DataIntegrityViolationException exp) {
			return "redirect:/properties/proxy/groups?duplicate";
		}

		return "redirect:/properties/proxy/groups";
	}

	@RequestMapping(value = "/properties/proxy/add", method = RequestMethod.POST)
	public String addProxyServers(@Validated ProxyServer proxyServer,
								  BindingResult bindingResult,
								  @RequestParam(value = "login") String login,
								  @RequestParam(value = "password") String password,
								  @RequestParam(value = "token") String token,
								  @RequestParam(value = "ip") String ip,
								  @RequestParam(value = "port") Integer port,
								  @RequestParam(value = "destiny") String destiny) {

		if (bindingResult.hasErrors()) {
			return "redirect:/properties/proxy?hasNull";
		}

		ProxyServer server = new ProxyServer(login, password, token, ip, port, destiny);

		try {
			proxyServerService.addProxyServer(server);
		} catch (DataIntegrityViolationException exp) {
			return "redirect:/properties/proxy?duplicate";
		}

		return "redirect:/properties/proxy";
	}

	@RequestMapping(value = "/properties/proxy/edit", method = RequestMethod.POST)
	public String editProxyServers(@Validated ProxyServer proxyServer,
								   BindingResult bindingResult,
								   @RequestParam(value = "id") Long id,
								   @RequestParam(value = "login") String login,
								   @RequestParam(value = "password") String password,
								   @RequestParam(value = "token") String token,
								   @RequestParam(value = "ip") String ip,
								   @RequestParam(value = "port") Integer port,
								   @RequestParam(value = "destiny") String destiny) {

		if (bindingResult.hasErrors()) {
			return "redirect:/properties/proxy?hasNull";
		}

		ProxyServer server = new ProxyServer(id, login, password, token, ip, port, destiny);

		try {
			proxyServerService.editProxyServer(server);
		} catch (DataIntegrityViolationException exp) {
			return "redirect:/properties/proxy?duplicate";
		}

		return "redirect:/properties/proxy";
	}

	@RequestMapping(value = "/properties/proxy/group/edit", method = RequestMethod.POST)
	public String editProxyServersForGroups(@Validated ProxyServer proxyServer,
											BindingResult bindingResult,
											@RequestParam(value = "id") Long id,
											@RequestParam(value = "login") String login,
											@RequestParam(value = "password") String password,
											@RequestParam(value = "token") String token,
											@RequestParam(value = "ip") String ip,
											@RequestParam(value = "port") Integer port,
											@RequestParam(value = "destiny") String destiny) {

		if (bindingResult.hasErrors()) {
			return "redirect:/properties/proxy/groups?hasNull";
		}

		ProxyServer server = new ProxyServer(id, login, password, token, ip, port, destiny);

		try {
			proxyServerService.editProxyServer(server);
		} catch (DataIntegrityViolationException exp) {
			return "redirect:/properties/proxy/groups?duplicate";
		}

		return "redirect:/properties/proxy/groups";
	}
}
