package vms.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import vms.models.ProxyServer;
import vms.services.absr.ProxyServerService;


@Controller
public class ProxyPropertiesController {

	@Autowired
	private ProxyServerService proxyServerService;

	@RequestMapping(value = "/properties/proxy", method = RequestMethod.GET)
	public String propertiesProxyServers(ModelMap modelMap) {
		modelMap.addAttribute("servers", proxyServerService.proxyServerList());

		return "proxyProperties";
	}

	@RequestMapping(value = "/properties/proxy/group", method = RequestMethod.GET)
	public String propertiesProxyServersForGroup(ModelMap modelMap) {
		modelMap.addAttribute("servers", proxyServerService.proxyServerList());

		return "proxypropertiesforgroup";
	}

	@RequestMapping(value = "/properties/proxy", method = RequestMethod.POST)
	public String delProxyServers(@RequestParam(value = "id") Long id) {
		proxyServerService.deleteProxyServer(id);

		return "redirect:/properties/proxy";
	}

	@RequestMapping(value = "/properties/proxy/group", method = RequestMethod.POST)
	public String delProxyServersForGroup(@RequestParam(value = "id") Long id) {
		proxyServerService.deleteProxyServer(id);

		return "redirect:/properties/proxy/group";
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
			return "redirect:/properties/proxy/group?hasNull";
		}

		ProxyServer server = new ProxyServer(login, password, token, ip, port, destiny);

		try {
			proxyServerService.addProxyServer(server);
		} catch (Exception exp) {
			return "redirect:/properties/proxy/group?duplicate";
		}

		return "redirect:/properties/proxy/group";
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
		} catch (Exception exp) {
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
		} catch (Exception exp) {
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
			return "redirect:/properties/proxy/group?hasNull";
		}

		ProxyServer server = new ProxyServer(id, login, password, token, ip, port, destiny);

		try {
			proxyServerService.editProxyServer(server);
		} catch (Exception exp) {
			return "redirect:/properties/proxy/group?duplicate";
		}

		return "redirect:/properties/proxy/group";
	}
}
