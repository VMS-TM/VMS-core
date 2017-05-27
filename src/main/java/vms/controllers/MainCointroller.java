package vms.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import vms.models.rawgroup.Group;
import vms.services.absr.GroupService;
import vms.services.GroupsSearchService;

import javax.servlet.http.HttpServletRequest;


@Controller
public class MainCointroller {

    @Autowired
    GroupService groupService;

    @Autowired
    GroupsSearchService groupsSearchService;


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(){
        return "login";
    }

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String list(Model model, HttpServletRequest request){
        model.addAttribute("groups", groupService.listAllVkGroups());
        if(request.getSession().getAttribute("flag") != null && (Boolean) request.getSession().getAttribute("flag")==true ) {
            int counter = 1;
            model.addAttribute("counter", counter);
            request.getSession().setAttribute("flag", false);
        } else {
            int counter = 0;
            model.addAttribute("counter", counter);
        }
        return "main";
}


    @RequestMapping("groups/new")
    public String newProduct(Model model){
        model.addAttribute("group", new Group());
        return "groupform";
    }

    @RequestMapping("groups/{id}")
    public String showProduct(@PathVariable String id, Model model){
        model.addAttribute("group", groupService.getGroupById(id));
        return "showgroups";
    }


    @RequestMapping(value = "main/edit", method = RequestMethod.POST)
    public String saveUser(@RequestParam("id") String id, @RequestParam("name") String name,
                                 @RequestParam("screen_name") String screen_name) throws Exception {
        Group group = new Group();
        group.setId(id);
        group.setName(name);
        group.setScreenName(screen_name);

        groupService.saveGroup(group);

        return "redirect:/main";

    }

   @RequestMapping(value = "addGroup", method = RequestMethod.POST)
    public String saveProduct(Model model,@RequestParam("groupIdOrName") String groupIdOrName , HttpServletRequest request){


      if(groupsSearchService.validate(groupIdOrName)!= null) {
           groupService.saveGroup(groupsSearchService.validate(groupIdOrName));
       } else {
           request.getSession().setAttribute("flag", new Boolean(true));
       }
       return"redirect:/main";
    }



    @RequestMapping("main/delete/{id}")
    public String delete(@PathVariable String id){
        groupService.deleteGroupById(id);
        return "redirect:/main";
    }
    


}
