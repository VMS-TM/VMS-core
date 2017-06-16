package vms.controllers;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.RequestParam;
import vms.globalVariables.ConstantsForVkApi;
import vms.models.ProxyServer;
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
import vms.services.absr.ProxyServerService;
import vms.services.absr.VkPostService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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

	@Autowired
	private ProxyServerService proxyServerService;


	@RequestMapping(value = {"/"}, method = RequestMethod.GET)
	public String getPostsFromDb(Model model) {
		List<Post> posts = postService.getAllPostFromDb();
		List<ProxyServer> proxy = proxyServerService.proxyServerList();

		List<Post> result = posts.stream()
				.filter(post -> "group".equals(post.getFromWhere()))
				.collect(Collectors.toList());

		List<ProxyServer> badProxy = proxy.stream()
				.filter(proxyServer -> Boolean.FALSE.equals(proxyServer.getWork()))
				.collect(Collectors.toList());

		prepareView(result);
		preparationPost(result);
		model.addAttribute("posts", result);
		model.addAttribute("AllPosts", result.size());
		model.addAttribute("badproxy", badProxy);

		return "posts";
	}

	@RequestMapping(value = {"/add"}, method = RequestMethod.POST)
	public String addPosts(@ModelAttribute List<Post> posts) {
		posts.forEach(post -> post.setFromWhere("group"));
		postService.addPosts(posts);
		return home;
	}

	@RequestMapping(value = {"/blacklistphone"}, method = RequestMethod.GET)
	public String showBlackListPhone(Model model) {
		List<Post> posts = postService.getAllPostFromDb();

		List<Post> blackListPhone = posts.stream()
				.filter(post -> Boolean.TRUE.equals(post.isBlackListPhone()))
				.collect(Collectors.toList());
		prepareView(blackListPhone);

		model.addAttribute("blackListPhone", blackListPhone);
		model.addAttribute("AllPosts", blackListPhone.size());

		return "blacklistphone";
	}

	@RequestMapping(value = {"/controlblacklistphone"}, method = RequestMethod.POST)
	public String controlBlackListPhone(Model model, @RequestParam(value = "idBlackListPhone") Long id) {
		Post blackPost = postService.getById(id);

		if (!blackPost.isBlackListPhone()) {
			blackPost.setBlackListPhone(true);
		} else {
			blackPost.setBlackListPhone(false);
		}

		postService.update(blackPost);

		List<Post> posts = postService.getAllPostFromDb();
		List<Post> blackListPhone = posts.stream()
				.filter(post -> Boolean.TRUE.equals(post.isBlackListPhone()))
				.collect(Collectors.toList());
		prepareView(blackListPhone);

		model.addAttribute("blackListPhone", blackListPhone);
		model.addAttribute("AllPosts", blackListPhone.size());


		return "blacklistphone";
	}

	@RequestMapping(value = {"/blacklisturl"}, method = RequestMethod.GET)
	public String showBlackListURL(Model model) {

		List<Post> posts = postService.getAllPostFromDb();

		List<Post> blackListURL = posts.stream()
				.filter(post -> Boolean.TRUE.equals(post.isBlackListURl()))
				.collect(Collectors.toList());
		prepareView(blackListURL);

		model.addAttribute("blackListURL", blackListURL);
		model.addAttribute("AllPosts", blackListURL.size());

		return "blacklisturl";
	}

	@RequestMapping(value = {"/controlblacklisturl"}, method = RequestMethod.POST)
	public String controlBlackListURL(Model model, @RequestParam(value = "idBlackListURL") Long id) {

		Post blackPost = postService.getById(id);

		if (!blackPost.isBlackListURl()) {
			blackPost.setBlackListURl(true);
		} else {
			blackPost.setBlackListURl(false);
		}

		postService.update(blackPost);

		List<Post> posts = postService.getAllPostFromDb();
		List<Post> blackListURL = posts.stream()
				.filter(post -> Boolean.TRUE.equals(post.isBlackListURl()))
				.collect(Collectors.toList());
		prepareView(blackListURL);

		model.addAttribute("blackListPhone", blackListURL);
		model.addAttribute("AllPosts", blackListURL.size());

		return "blacklisturl";
	}

	@RequestMapping(value = {"/news"}, method = RequestMethod.GET)
	public String getNews(Model model) {
		List<Post> posts = postService.getAllPostFromDb();

		List<Post> result = posts.stream()
				.filter(post -> "news".equals(post.getFromWhere()))
				.collect(Collectors.toList());

		List<Post> resultNotInBlackListPhone = result.stream()
				.filter(post -> Boolean.FALSE.equals(post.isBlackListPhone()))
				.collect(Collectors.toList());

		prepareView(resultNotInBlackListPhone);
		preparationPost(resultNotInBlackListPhone);
		model.addAttribute("posts", resultNotInBlackListPhone);
		model.addAttribute("AllPosts", resultNotInBlackListPhone.size());
		return "newspost";
	}

	@RequestMapping(value = {"/news/find"}, method = RequestMethod.POST)
	public String getNews(Model model, @RequestParam(value = "news") String query) {
		newsSearchService.getAdsFromNews(query);
		List<Post> posts = postService.getAllPostFromDb();

		List<Post> result = posts.stream()
				.filter(post -> "news".equals(post.getFromWhere()))
				.collect(Collectors.toList());

		List<Post> resultNotInBlackListPhone = result.stream()
				.filter(post -> Boolean.FALSE.equals(post.isBlackListPhone()))
				.collect(Collectors.toList());


		prepareView(resultNotInBlackListPhone);
		preparationPost(resultNotInBlackListPhone);

		model.addAttribute("posts", resultNotInBlackListPhone);
		model.addAttribute("AllPosts", resultNotInBlackListPhone.size());
		return "redirect:/post/news";
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
							  @RequestParam(value = "date") String date,
							  @RequestParam(value = "fromwhere") String from) throws ParseException {



		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Date dateOfPost = null;
		try {
			dateOfPost = format.parse(date);
		} catch (ParseException e) {

		}

		Post editedPost = new Post(id, title, owner, district, price, textOnView, adress, contact, info, from, dateOfPost);

		if ("news".equals(from)) {
			try {
				editedPost.setSavedInDb(true);
				postService.update(editedPost);
			} catch (DataIntegrityViolationException exp) {
				exp.printStackTrace();
			}
			return "redirect:/post/news";
		} else {
			try {
				editedPost.setSavedInDb(true);
				postService.update(editedPost);
			} catch (DataIntegrityViolationException exp) {
				exp.printStackTrace();
			}
			return home;
		}

	}

	@RequestMapping(value = {"/delete"}, method = RequestMethod.POST)
	public String deletePosts(@RequestParam(value = "idDeletePost") Long id) {
		postService.deletePost(id);
		return home;
	}

	@RequestMapping(value = {"/req"}, method = RequestMethod.POST)
	public String getNewPosts(Model model, @RequestParam(value = "query") String query) {
		postSearchServiceImpl.getPostResponseByGroupsList(groupService.listAllVkGroups(), query);

		List<Post> posts = postService.getAllPostFromDb();
		List<ProxyServer> proxy = proxyServerService.proxyServerList();

		List<Post> result = posts.stream()
				.filter(post -> "group".equals(post.getFromWhere()))
				.collect(Collectors.toList());

		List<ProxyServer> badProxy = proxy.stream()
				.filter(proxyServer -> Boolean.FALSE.equals(proxyServer.getWork()))
				.collect(Collectors.toList());


		if (result != null) {
			prepareView(result);
			preparationPost(result);
			model.addAttribute("posts", result);
			model.addAttribute("AllPosts", result.size());
			model.addAttribute("badproxy", badProxy);

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
								@RequestParam("saveInDataBase") boolean save,
								@RequestParam(value = "fromwhere") String from) throws ParseException {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dateOfPost = null;
		try {
			dateOfPost = format.parse(date);
		} catch (ParseException e) {

		}

		Post postToGroup = new Post(id, title, owner, district, price, textOnView, adress, contact, info, from, dateOfPost);
		String result = postToGroupService.postToGroup(ConstantsForVkApi.ID_GROUP, postToGroup);

		if ("news".equals(from)) {
			if (result == null || result.contains("error_code")) {
				return "redirect:/post/news/?postInGroupDanger";
			}
			postToGroup.setSavedInDb(save);
			postToGroup.setPostedToGroup(true);
			postService.update(postToGroup);
			return "redirect:/post/news/?postInGroupSuccess";

		} else {
			if (result == null || result.contains("error_code")) {
				return "redirect:/post/?postInGroupDanger";
			}
			postToGroup.setSavedInDb(save);
			postToGroup.setPostedToGroup(true);
			postService.update(postToGroup);
			return "redirect:/post/?postInGroupSuccess";
		}
	}

	void prepareView(List<Post> posts) {
		Collections.sort(posts, Comparator.comparing(Post::getDate).reversed());
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
