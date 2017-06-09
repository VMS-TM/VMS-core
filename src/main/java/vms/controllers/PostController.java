package vms.controllers;

import com.vdurmont.emoji.EmojiParser;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestParam;
import vms.globalVariables.ConstantsForVkApi;
import vms.models.postenvironment.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import vms.models.postenvironment.PostResponse;
import vms.services.PostToGroupService;
import vms.services.absr.GroupService;
import vms.services.absr.PostSearchService;
import vms.services.absr.VkPostService;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

	@Autowired
	private PostToGroupService postToGroupService;


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
	public String updatePosts(@RequestParam(value = "id") Integer id,
							  @RequestParam(value = "title") String title,
							  @RequestParam(value = "owner") String owner,
							  @RequestParam(value = "district") String district,
							  @RequestParam(value = "price") String price,
							  @RequestParam(value = "textOnView") String textOnView,
							  @RequestParam(value = "adress") String adress,
							  @RequestParam(value = "contact") String contact,
							  @RequestParam(value = "info") String info,
							  @RequestParam("date") String date) throws ParseException {


		DateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
		Date dateOfPost = format.parse(date);
		Post editedPost = new Post(id, title, owner, district, price, textOnView, adress, contact, info, dateOfPost);

		try {
			postService.update(editedPost);
		} catch (DataIntegrityViolationException exp) {
			return home;
		}

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
		if (postResponse.getPosts() != null) {
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

		return "redirect:/post/req?warning";
	}

	@RequestMapping(value = {"/addPost"}, method = RequestMethod.POST)
	public String doPostToGroup(@RequestParam(value = "title") String title,
								@RequestParam(value = "owner") String owner,
								@RequestParam(value = "district") String district,
								@RequestParam(value = "price") String price,
								@RequestParam(value = "textOnView") String textOnView,
								@RequestParam(value = "adress") String adress,
								@RequestParam(value = "contact") String contact,
								@RequestParam(value = "info") String info) {

		Post postToGroup = new Post(title, owner, district, price, textOnView, adress, contact, info);

		String result = postToGroupService.postToGroup(ConstantsForVkApi.ID_GROUP, postToGroup);

		if (result == null || result.contains("error_code")) {
			return "redirect:/post/req?postInGroupDanger";
		}

		return "redirect:/post/req?postInGroupSuccess";

	}

	void preparationPost(List<Post> posts) {
		for (Iterator<Post> iter = posts.listIterator(); iter.hasNext(); ) {
			Post postCurrent = iter.next();
			postCurrent.setOwnerId(Math.abs(postCurrent.getOwnerId()));
			Pattern phoneNumber = Pattern.compile("(((8|\\+7)-?)?\\(?\\d{3}\\)?-?\\d{1}-?\\d{1}-?\\d{1}-?\\d{1}-?\\d{1}-?\\d{1}-?\\d{1})|(^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$)|(((8|\\+7) ?)?\\(?\\d{3}\\)? ?\\d{3}-?\\d{2}-?\\d{2})");
			Pattern rent = Pattern.compile("(?<=сдаётся по | Стоимость |стоимость |Стоимость в месяц |стоимость в месяц |аренды в месяц |в месяц |Сдается за |cдается за|Залог |залог  |Стоимость аренды |cтоимость аренды |Аренда |аренда |Цена |цена |стоит |Стоит | ВСЕГО за| всего за ).*(\\d|\\d.p|\\d p|\\d.руб|\\d руб|\\d руб.|\\d рублей|\\d.рублей|\\d т.р.|\\d т. р.|\\d.\u20BD).(?=\\s)");
			Pattern metroAndAddress = Pattern.compile("(?<=ул.|Улица |улица |Квартира |М. |м. |м.|м |квартира |районе |Районе |метро |Метро |Адрес |Адрес: |адрес |адрес: |адресу ).*(\\W+)(?=\\D+)");

			Matcher matcherPhoneNumber = phoneNumber.matcher(postCurrent.getText());
			Matcher matcherRent = rent.matcher(postCurrent.getText());
			Matcher matcherMetroAndAddress = metroAndAddress.matcher(postCurrent.getText());


			if (matcherPhoneNumber.find()) {
				postCurrent.setPhoneNumber(matcherPhoneNumber.group(0));
			} else if (matcherRent.find()) {
				postCurrent.setPriceOfFlat(matcherRent.group(0));
			} else if (matcherMetroAndAddress.find()) {
				postCurrent.setMetroAndAddress(matcherMetroAndAddress.group(0));
			}
		}
	}

}
