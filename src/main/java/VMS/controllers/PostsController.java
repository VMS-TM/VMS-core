package VMS.controllers;

import VMS.model.postenv.Post;
import VMS.services.abstr.VkPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by Кирилл on 30.04.2017.
 */
@Controller
@RequestMapping(value = "/post")
public class PostsController {

	private static final String home = "redirect:/post/";

	@Autowired
	VkPostService postService;

	@RequestMapping(value = { "/"}, method = RequestMethod.GET)
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




}
