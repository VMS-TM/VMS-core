package vms.controllers;

import org.springframework.web.bind.annotation.RequestParam;
import vms.models.postenvironment.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import vms.models.postenvironment.PostResponse;
import vms.services.absr.GroupService;
import vms.services.absr.PostSearchService;
import vms.services.absr.VkPostService;

import java.util.List;

@Controller
@RequestMapping(value = "/post")
public class PostController {

	private static final String home = "redirect:/post/";

	@Autowired
	private VkPostService postService;

	@Autowired
	private PostSearchService postSearchServiceImpl;

	@Autowired
	private GroupService groupService;

	@RequestMapping(value = {"/"}, method = RequestMethod.GET)
	public String getPostsFromDb(Model model){
		model.addAttribute("posts",postService.getAllPostFromDb());
		return "posts";
	}

	@RequestMapping(value = {"/add"}, method = RequestMethod.POST)
	public String addPosts(@ModelAttribute List<Post> posts) {
		postService.addPosts(posts);
		return home;
	}

	@RequestMapping(value = {"/update"}, method = RequestMethod.POST)
	public String updatePosts(@ModelAttribute Post post) {
		postService.update(post);
		return home;
	}

	@RequestMapping(value = {"/delete"}, method = RequestMethod.POST)
	public String deletePosts(@ModelAttribute Post post) {
		postService.delete(post);
		return home;
	}

	@RequestMapping(value = {"/req"}, method = RequestMethod.GET)
	public String getNewPosts (@ModelAttribute Post post, @RequestParam(value = "query", required=false) String query) {
		PostResponse postResponse= postSearchServiceImpl.getPostResponseByGroupsList(groupService.listAllVkGroups(),query);
		if (postResponse!=null){
			postService.addPosts(postResponse.getPosts());
		}
		return home;
	}


}
