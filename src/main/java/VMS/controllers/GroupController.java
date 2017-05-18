package VMS.controllers;

import VMS.model.Group;
import VMS.parsers.GroupVkValidator;
import VMS.services.abstr.VkGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequestMapping("/group")
public class GroupController {
	private static GroupController ourInstance = new GroupController();

	private static final String home = "redirect:/group/";

	@Autowired
	private VkGroupService groupService;

	@Autowired
	private GroupVkValidator groupVkValidator;

	@RequestMapping(value = { "/"}, method = RequestMethod.GET)
	public String getGroupsFromDb(Model model){
		model.addAttribute("groups",groupService.getAllGroupsFromDb());
		model.addAttribute("group", new Group());
		return "groups";
	}

	@RequestMapping(value = {"/add"}, method = RequestMethod.POST, headers = "content-type=application/x-www-form-urlencoded")
	public String addGroup(@Valid Group group, BindingResult bindingResult) {
		if (groupVkValidator.ifGroupExist(group)){
			groupService.addGroup(group);
		}
		return home;
	}

	@RequestMapping(value = {"/update"}, method = RequestMethod.POST)
	public String updateGroup(@ModelAttribute Group group) {
		groupService.updateGroup(group);
		return home;
	}

	@RequestMapping(value = {"/delete"}, method = RequestMethod.POST)
	public String deleteGroup(@ModelAttribute Group group) {
		groupService.deleteGroup(group);
		return home;
	}


}
