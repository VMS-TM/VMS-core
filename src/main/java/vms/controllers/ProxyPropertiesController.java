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

	@RequestMapping(value = "/properties/proxy", method = RequestMethod.POST)
	public String delProxyServers(@RequestParam(value = "id") Long id) {
		proxyServerService.deleteProxyServer(id);

		return "redirect:/properties/proxy";
	}

	@RequestMapping(value = "/properties/proxy/add", method = RequestMethod.GET)
	public String pageProxyServersAdd() {
		return "addProxy";
	}

	@RequestMapping(value = "/properties/proxy/add", method = RequestMethod.POST)
	public String addProxyServers(ModelMap modelMap,
								  @Validated ProxyServer proxyServer,
								  BindingResult bindingResult,
								  @RequestParam(value = "login") String login,
								  @RequestParam(value = "password") String password,
								  @RequestParam(value = "token") String token,
								  @RequestParam(value = "ip") String ip,
								  @RequestParam(value = "port") Long port) {
		if(bindingResult.hasErrors()){
			return "redirect:/properties/proxy?hasNull";
		}

		ProxyServer server = new ProxyServer(login, password, token, ip, port);

		try {
			proxyServerService.addProxyServer(server);
		}catch (Exception exp){
			return "redirect:/properties/proxy?duplicate";
		}

		return "redirect:/properties/proxy";
	}

	@RequestMapping(value = "/properties/proxy/edit", method = RequestMethod.POST)
	public String editProxyServers(ModelMap modelMap,
								   @RequestParam(value = "id") Long id,
								   @RequestParam(value = "login") String login,
								   @RequestParam(value = "password") String password,
								   @RequestParam(value = "token") String token,
								   @RequestParam(value = "ip") String ip,
								   @RequestParam(value = "port") Long port) {

		ProxyServer server = new ProxyServer(id, login, password, token, ip, port);

		try {
			proxyServerService.editProxyServer(server);
		}catch (Exception exp){
			return "redirect:/properties/proxy?duplicate";
		}

		return "redirect:/properties/proxy";
	}
}
