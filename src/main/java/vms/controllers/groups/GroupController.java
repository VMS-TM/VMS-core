package vms.controllers.groups;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import vms.models.groups.Foo;
import vms.models.groups.Group;
import vms.repositories.groups.GroupRepository;
import vms.services.impl.groups.GroupsSearchService;

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
		return "groups/findgroups";
	}

	@RequestMapping(value = "findgroups", method = RequestMethod.POST)
	public String saveUser(Model model, @RequestParam("memberscount") String count, @RequestParam("first") String first, @RequestParam(value = "second", required = false) String second) throws Exception {

		resp = groupsSearchService.getGroupsByGroupName(first, second, Integer.parseInt(count));
		model.addAttribute("groups", resp);
		Foo foo = new Foo();
		model.addAttribute("foo", foo);
		return "groups/findgroups";
	}

	@RequestMapping(value = "/processForm", method = RequestMethod.POST)
	public String processForm(@ModelAttribute(value = "foo") Foo foo) {

		List<String> checkedItems = foo.getCheckedItems();
		for (String s : checkedItems) {
			groupRepository.save(resp.get(Integer.parseInt(s)));
		}
		return "redirect:/main";
	}
}
