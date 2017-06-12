package vms.controllers;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.RequestParam;
import vms.globalVariables.ConstantsForVkApi;
import vms.models.postenvironment.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import vms.services.NewsSearchService;
import vms.services.PostToGroupService;
import vms.services.absr.GroupService;
import vms.services.absr.PostSearchService;
import vms.services.absr.VkPostService;
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

	@Autowired
 	private NewsSearchService newsSearchService;


	@RequestMapping(value = {"/"}, method = RequestMethod.GET)
	public String getPostsFromDb(Model model) {
		List<Post> posts = postService.getAllPostFromDb();
		Collections.sort(posts, Comparator.comparing(Post::getDate).reversed());
		prepareOwnerID(posts);
		preparationPost(posts);
		model.addAttribute("posts", posts);
		model.addAttribute("AllPosts", posts.size());
		return "posts";
	}

	@RequestMapping(value = {"/add"}, method = RequestMethod.POST)
	public String addPosts(@ModelAttribute List<Post> posts) {
		Collections.sort(posts, Comparator.comparing(Post::getDate).reversed());
		postService.addPosts(posts);
		return home;
	}

	@RequestMapping(value = {"/news"}, method = RequestMethod.GET)
 	public String getNews(@RequestParam(value = "query", required = false) String query) {
				newsSearchService.getAdsFromNews(query);
				return "newspost";
	}

	@RequestMapping(value = {"/update"}, method = RequestMethod.POST)
	public String updatePosts(Model model,
							  @RequestParam(value = "id") Long id,
							  @RequestParam(value = "title") String title,
							  @RequestParam(value = "owner") String owner,
							  @RequestParam(value = "district") String district,
							  @RequestParam(value = "price") String price,
							  @RequestParam(value = "textOnView") String textOnView,
							  @RequestParam(value = "adress") String adress,
							  @RequestParam(value = "contact") String contact,
							  @RequestParam(value = "info") String info,
							  @RequestParam("date") String date) throws ParseException {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dateOfPost = format.parse(date);
		Post editedPost = new Post(id, title, owner, district, price, textOnView, adress, contact, info, dateOfPost);

		try {
			editedPost.setSavedInDb(true);
			postService.update(editedPost);
		} catch (DataIntegrityViolationException exp) {
			return home;
		}

		return home;
	}

	@RequestMapping(value = {"/delete"}, method = RequestMethod.POST)
	public String deletePosts(@RequestParam(value = "idDeletePost") Long id) {
		postService.deletePost(id);
		return home;
	}

	@RequestMapping(value = {"/req"}, method = RequestMethod.GET)
	public String getNewPosts(Model model, @ModelAttribute Post post, @RequestParam(value = "query", required = false) String query) {
		postSearchServiceImpl.getPostResponseByGroupsList(groupService.listAllVkGroups(), query);
		List<Post> posts = postService.getAllPostFromDb();
		if (posts != null) {
			Collections.sort(posts, Comparator.comparing(Post::getDate).reversed());
			prepareOwnerID(posts);
			preparationPost(posts);
			model.addAttribute("posts", posts);
			model.addAttribute("AllPosts", posts.size());
			return "posts";
		}

		return "redirect:/post/req?warning";
	}

	@RequestMapping(value = {"/addPost"}, method = RequestMethod.POST)
	public String doPostToGroup(@RequestParam(value = "id") Long id,
								@RequestParam(value = "title") String title,
								@RequestParam(value = "owner") String owner,
								@RequestParam(value = "district") String district,
								@RequestParam(value = "price") String price,
								@RequestParam(value = "textOnView") String textOnView,
								@RequestParam(value = "adress") String adress,
								@RequestParam(value = "contact") String contact,
								@RequestParam(value = "info") String info,
								@RequestParam("date") String date,
								@RequestParam("saveInDataBase") boolean save) throws ParseException {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dateOfPost = format.parse(date);
		Post postToGroup = new Post(id, title, owner, district, price, textOnView, adress, contact, info, dateOfPost);
		String result = postToGroupService.postToGroup(ConstantsForVkApi.ID_GROUP, postToGroup);

		if (result == null || result.contains("error_code")) {
			return "redirect:/post/?postInGroupDanger";
		}

		postToGroup.setSavedInDb(save);
		postToGroup.setPostedToGroup(true);
		postService.update(postToGroup);

		return "redirect:/post/?postInGroupSuccess";

	}

	void prepareOwnerID(List<Post> posts) {
		posts.forEach(post -> post.setOwnerId(Math.abs(post.getOwnerId())));
	}


	void preparationPost(List<Post> posts) {

		for (Iterator<Post> iter = posts.listIterator(); iter.hasNext(); ) {
			Post postCurrent = iter.next();
			if (postCurrent.isSavedInDb() != true) {
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

}
