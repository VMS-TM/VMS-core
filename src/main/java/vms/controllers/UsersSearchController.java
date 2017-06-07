package vms.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import vms.models.cityenvironment.City;
import vms.services.absr.SearchUsersService;

import java.util.List;

@Controller
public class UsersSearchController {

	@Autowired
	private SearchUsersService searchUsersService;

	@RequestMapping(value = "/citysearch", method = RequestMethod.GET)
	public String searchCity(ModelMap modelMap) {
		modelMap.addAttribute("countries", searchUsersService.getCountries());

		return "searchusers";
	}

	@RequestMapping(value = "/citysearch", method = RequestMethod.POST)
	public String choiceCity(ModelMap modelMap, @RequestParam(value = "county") Long id,
							 @RequestParam(value = "q") String q) {
		if(q.equals("")){
			return "redirect:/citysearch?isEmpty";
		}

		List<City> cities = searchUsersService.getCities(id, q);
		modelMap.addAttribute("cities", cities);

		return "cityselection";
	}

	@RequestMapping(value = "/userssearch", method = RequestMethod.POST)
	public String searchUsers(ModelMap modelMap, @RequestParam(value = "id") Long id) {
		searchUsersService.getUsersInSelectedCity(id);

		return "redirect:/citysearch";
	}
}
