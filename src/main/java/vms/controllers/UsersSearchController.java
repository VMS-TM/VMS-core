package vms.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UsersSearchController {

	@RequestMapping(value = "/userssearch", method = RequestMethod.GET)
	public String searchUsers(ModelMap modelMap) {
		return "searchusers";
	}

	@RequestMapping(value = "/userssearch", method = RequestMethod.POST)
	public String choiceCity(ModelMap modelMap, @RequestParam(value = "county") Long id) {
		return "redirect:/userssearch";
	}
}
