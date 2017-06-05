package vms.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import vms.models.postenvironment.Post;
import vms.models.postenvironment.PostResponse;
import vms.scheduling.Rule;
import vms.scheduling.ScheduleTaskManager;
import vms.scheduling.TaskStatus;
import vms.services.absr.GroupService;
import vms.services.absr.PostSearchService;
import vms.services.absr.RuleService;
import vms.services.absr.VkPostService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/rule")
public class ScheduleController {

	private static String home = "redirect:/rule/";

	@Autowired
	ScheduleTaskManager scheduler;

	@Autowired
	RuleService ruleService;

	@Autowired
	PostSearchService postSearchService;

	@Autowired
	GroupService groupService;

	@Autowired
	VkPostService postService;

	@RequestMapping(value = {"/"}, method = RequestMethod.GET)
	public String scheduleTask(Model model){
		List<Rule> rules = ruleService.getAllRules();
		model.addAttribute("rules",rules);
		return "rules";
	}

	@RequestMapping(value = {"/start"}, method = RequestMethod.GET)
	public String startTask(@RequestParam(value = "id", required=false) long id){
		Rule rule = ruleService.getRuleById(id);
		scheduler.scheduleTask(() -> {
			PostResponse postResponse = postSearchService.getPostResponseByGroupsList(groupService.listAllVkGroups(), rule.getKeyWords());
			if (postResponse != null) {
				ArrayList<Post> listPosts = postResponse.getPosts();
				postService.addPosts(listPosts);
			}
		},//task
		rule.getName(),//name of task
		rule.getCronExpr());//date for task
		rule.setStatus(TaskStatus.WORK);
		ruleService.addRule(rule);
		return home;
	}

@RequestMapping(value = {"/stop"}, method = RequestMethod.GET)
	public String stopTask(@RequestParam(value = "id", required=false) long id){
		Rule curRule = ruleService.getRuleById(id);
		scheduler.stopTask(curRule.getName());
		curRule.setStatus(TaskStatus.STOP);
		ruleService.addRule(curRule);
		return home;
	}

}
