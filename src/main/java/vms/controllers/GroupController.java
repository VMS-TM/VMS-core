package vms.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import vms.models.Foo;
import vms.models.rawgroup.Group;
import vms.repositories.GroupRepository;
import vms.services.impl.GroupsSearchService;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;


@Controller
public class GroupController {

	@Autowired
	GroupsSearchService groupsSearchService;

	@Autowired
	GroupRepository groupRepository;

	private static List<Group> resp;

	private static final Logger logger = LoggerFactory.getLogger(GroupController.class);

	@RequestMapping(value = "/showgroups", method = RequestMethod.GET)
	public String findGroups(Model model) {

		Foo foo = new Foo();
		List<String> checkedItems = new ArrayList<String>();
		foo.setCheckedItems(checkedItems);
		model.addAttribute("foo", foo);
		return "findgroups";
	}

	@RequestMapping(value = "findgroups", method = RequestMethod.POST)
	public String saveUser(Model model, @RequestParam("memberscount") String count, @RequestParam("first") String first, @RequestParam("second") String second) throws Exception {

		resp = groupsSearchService.getGroupsByGroupName(first, second, Integer.parseInt(count));
		model.addAttribute("groups", resp);
		Foo foo = new Foo();
		model.addAttribute("foo", foo);
		return "findgroups";
	}

	@RequestMapping(value = "/processForm", method = RequestMethod.POST)
	public String processForm(@ModelAttribute(value = "foo") Foo foo) {
		// Get value of checked item.

		List<String> checkedItems = foo.getCheckedItems();
		for (String s : checkedItems) {
			groupRepository.save(resp.get(Integer.parseInt(s)));
		}
		return "redirect:/main";
	}
}
