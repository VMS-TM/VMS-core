package vms.controllers;

import com.vdurmont.emoji.EmojiParser;
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

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	public String getPostsFromDb(Model model) {
		List<Post> posts = postService.getAllPostFromDb();
		model.addAttribute("posts", posts);
		model.addAttribute("AllPosts", posts.size());
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
	public String getNewPosts(Model model, @ModelAttribute Post post, @RequestParam(value = "query", required = false) String query) {
		PostResponse postResponse = postSearchServiceImpl.getPostResponseByGroupsList(groupService.listAllVkGroups(), query);
		if (postResponse != null) {
			ArrayList<Post> listPosts = postResponse.getPosts();
			postService.addPosts(listPosts);
			List<Post> posts = postService.getAllPostFromDb();
			Collections.sort(posts, Comparator.comparing(Post::getDate).reversed());
			preparationPost(posts);
			model.addAttribute("posts", posts);
			model.addAttribute("AllPosts", posts.size());
			model.addAttribute("FoundCount", postResponse.getCount());
			model.addAttribute("GettingCount", postResponse.getPosts().size());
			return "posts";
		}
		return home;
	}

	void preparationPost(List<Post> posts) {

		for (Iterator<Post> iter = posts.listIterator(); iter.hasNext(); ) {
			Post postCurrent = iter.next();
			Pattern phoneNumber = Pattern.compile("^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$");
			String ruNamePart = "[А-яЁё][-А-яЁё]+";
			Pattern russianNames = Pattern.compile("\\s*[А-ЯЁ][-А-яЁё]+\\s+(" + ruNamePart + "\\s+){1,5}" + ruNamePart + "\\s*");



			Matcher matcherPhoneNumber = phoneNumber.matcher(postCurrent.getText());
			Matcher matcherRussianNames = russianNames.matcher(postCurrent.getText());

			if (matcherPhoneNumber.find()) {
				postCurrent.setPhoneNumber(matcherPhoneNumber.group(0));
			}

			matcherRussianNames.matches();

			postCurrent.setOwnerId(Math.abs(postCurrent.getOwnerId()));
		}
	}

}
