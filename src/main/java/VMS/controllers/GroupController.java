<<<<<<< HEAD
package vms.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import vms.models.Foo;
import vms.models.rawgroup.Group;
import vms.repositories.GroupRepository;
import vms.services.GroupsSearchService;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;


@Controller
public class GroupController {

    private static List<Group> resp;
    @Autowired
    GroupsSearchService groupsSearchService;
    @Autowired
    GroupRepository groupRepository;

    @RequestMapping(value = "/showgroups", method = RequestMethod.GET)
    public String findGroups(Model model){

        Foo foo = new Foo();
        List<String> checkedItems = new ArrayList<String>();
        foo.setCheckedItems(checkedItems);
        model.addAttribute("foo", foo);
        return "findgroups";
    }

    @RequestMapping(value = "findgroups", method = RequestMethod.POST)
    public String saveUser(Model model, @RequestParam("memberscount") String count,@RequestParam("first") String first, @RequestParam("second") String second) throws Exception {

        resp = groupsSearchService.getPostsByGroupName(first, second, Integer.parseInt(count));
        model.addAttribute("groups",resp );
        Foo foo = new Foo();
        model.addAttribute("foo", foo);
        return "findgroups";
    }

    @RequestMapping(value = "/processForm", method=RequestMethod.POST)
    public String processForm(@ModelAttribute(value="foo") Foo foo) {
        // Get value of checked item.

        List<String> checkedItems = foo.getCheckedItems();
        for(String s : checkedItems) {
            groupRepository.save(resp.get(Integer.parseInt(s)));
        }
 return "redirect:/main";
    }
=======
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


>>>>>>> postAndGroups
}
