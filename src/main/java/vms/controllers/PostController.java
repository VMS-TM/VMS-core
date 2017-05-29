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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
		List<Post> posts = postService.getAllPostFromDb();
		model.addAttribute("posts",posts);
		model.addAttribute("AllPosts",posts.size());
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
	public String getNewPosts (Model model, @ModelAttribute Post post, @RequestParam(value = "query", required=false) String query) {
		PostResponse postResponse= postSearchServiceImpl.getPostResponseByGroupsList(groupService.listAllVkGroups(),query);
		if (postResponse!=null){
			ArrayList listPosts = postResponse.getPosts();
			Collections.sort(listPosts, Comparator.comparing(Post::getDate).reversed());
			postService.addPosts(listPosts);
			List<Post> posts = postService.getAllPostFromDb();
			model.addAttribute("posts", posts);
			model.addAttribute("AllPosts",posts.size());
			model.addAttribute("FoundCount",postResponse.getCount());
			model.addAttribute("GettingCount",postResponse.getPosts().size());
			return "posts";
		}
		return home;
	}


}
