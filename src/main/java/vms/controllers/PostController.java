package vms.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestParam;
import vms.VmsApplication;
import vms.globalVariables.ConstantsForVkApi;
import vms.models.ProxyServer;
import vms.models.postenvironment.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import vms.models.postenvironment.Query;
import vms.services.impl.NewsSearchService;
import vms.services.impl.PostToGroupService;
import vms.services.absr.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/post")
public class PostController {

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

	@Autowired
	private SearchUsersPostsService usersPostsService;

	@Autowired
	private UserFromVkService userFromVkService;

	@Autowired
	private QueryService queryService;

	private static final Logger logger = LoggerFactory.getLogger(PostController.class);

	private static final String home = "redirect:/post/";


	private final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(ConstantsForVkApi.POOL_SIZE);
	private final Map<String, ScheduledFuture<?>> mapSchedule = new ConcurrentHashMap<>();
	private final Map<String, Map<String, ScheduledFuture<?>>> queryMap = new ConcurrentHashMap<>();

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
		model.addAttribute("mapSchedule", mapSchedule);
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
		List<Post> blackList = postService.getAllBlackPosts();
		prepareView(blackList);
		model.addAttribute("blackListPhone", blackList);
		return "blacklistphone";
	}

	@RequestMapping(value = {"/controlblacklist"}, method = RequestMethod.POST)
	public String controlBlackListPhone(Model model, @RequestParam(value = "idBlackList") Long id) {
		Post blackPost = postService.getById(id);
		if (!blackPost.isBlackList()) {
			blackPost.setBlackList(true);
		} else {
			blackPost.setBlackList(false);
		}
		postService.update(blackPost);
		List<Post> blackList = postService.getAllBlackPosts();
		prepareView(blackList);
		model.addAttribute("blackListPhone", blackList);
		return "redirect:/post/blacklistphone";
	}

	@RequestMapping(value = {"/blacklisturl"}, method = RequestMethod.GET)
	public String showBlackListURL(Model model) {
		List<Post> blackList = postService.getAllBlackPosts();
		prepareView(blackList);
		model.addAttribute("blackListURL", blackList);
		return "blacklisturl";
	}


	@RequestMapping(value = {"/news"}, method = RequestMethod.GET)
	public String getNews(Model model) {
		List<Post> posts = postService.getAllPostFromDb();
		List<Post> result = posts.stream()
				.filter(post -> "news".equals(post.getFromWhere()))
				.filter(post -> Boolean.FALSE.equals(post.isBlackList()))
				.collect(Collectors.toList());
		prepareView(result);
		preparationPost(result);
		model.addAttribute("posts", result);
		model.addAttribute("mapSchedule", mapSchedule);
		return "newspost";
	}

	@RequestMapping(value = {"/stop"}, method = RequestMethod.POST)
	public String cancelThread(Model model,
							   @RequestParam(value = "key") String key,
							   @RequestParam(value = "fromwhere") String from) {

		try {
			mapSchedule.get(key).cancel(true);
			mapSchedule.remove(key);
		} catch (NullPointerException exp) {

		}

		return redirect(from);
	}

	@RequestMapping(value = {"/news/find"}, method = RequestMethod.POST)
	public String getNews(Model model,
						  		@RequestParam(value = "news") String query,
						  		@RequestParam(value = "time") Long time) {

		mapSchedule.put(query, scheduledExecutorService.scheduleAtFixedRate(() -> {
			newsSearchService.getAdsFromNews(query);
		}, 0, time, TimeUnit.MINUTES));

		List<Post> posts = postService.getAllPostFromDb();
		List<Post> result = posts.stream()
				.filter(post -> "news".equals(post.getFromWhere()))
				.filter(post -> Boolean.FALSE.equals(post.isBlackList()))
				.collect(Collectors.toList());
		prepareView(result);
		preparationPost(result);

		model.addAttribute("posts", result);
		model.addAttribute("mapSchedule", mapSchedule);
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
		if (postService.getById(id).isHavePhoto()) {
			editedPost.setHavePhoto(true);
			editedPost.setPhotos(postService.getById(id).getPhotos());
		}
		if ("news".equals(from)) {
			try {
				editedPost.setSavedInDb(true);
				postService.update(editedPost);
			} catch (DataIntegrityViolationException exp) {
				exp.printStackTrace();
			}
			return "redirect:/post/news";
		} else if ("user".equals(from)) {
			try {
				editedPost.setSavedInDb(true);
				postService.update(editedPost);
			} catch (DataIntegrityViolationException exp) {
				exp.printStackTrace();
			}
			return "redirect:/post/users/wall";
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

	@RequestMapping(value = {"/deletePost"}, method = RequestMethod.POST)
	public String deletePosts(@RequestParam(value = "idDeletePost") Long id,
							  @RequestParam(value = "fromwhere") String from) {


		if (postService.getById(id) != null) {
			postService.delete(postService.getById(id));
		}

		return redirect(from);
	}

	@RequestMapping(value = {"/deleteAllPosts"}, method = RequestMethod.POST)
	public String deleteAllPosts(@RequestParam(value = "fromwhere") String from) {

			List<Post> postList = postService.getAllPostFromDb();
			postService.deleteAllPosts(postList);

		return redirect(from);
	}

	@RequestMapping(value = {"/req"}, method = RequestMethod.POST)
	public String getNewPosts(Model model,
							  @RequestParam(value = "query") String query,
							  @RequestParam(value = "time") Long time) {

		mapSchedule.put(query, scheduledExecutorService.scheduleAtFixedRate(() -> {
			postSearchServiceImpl.getPostResponseByGroupsList(groupService.listAllVkGroups(), query);
		}, 0, time, TimeUnit.MINUTES));

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
			return "redirect:/post/";
		}
		return "redirect:/post/?warning";
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
		if (postService.getById(id).isHavePhoto()) {
			postToGroup.setHavePhoto(true);
			postToGroup.setPhotos(postService.getById(id).getPhotos());
		}
		String result = postToGroupService.postToGroup(ConstantsForVkApi.ID_GROUP, postToGroup);
		if ("news".equals(from)) {
			if (result == null || result.contains("error_code")) {
				return "redirect:/post/news/?postInGroupDanger";
			}
			postToGroup.setSavedInDb(save);
			postToGroup.setPostedToGroup(true);
			postService.update(postToGroup);
			return "redirect:/post/news/?postInGroupSuccess";
		} else if ("user".equals(from)) {
			if (result == null || result.contains("error_code")) {
				return "redirect:/post/users/wall?postInGroupDanger";
			}
			postToGroup.setSavedInDb(save);
			postToGroup.setPostedToGroup(true);
			postService.update(postToGroup);
			return "redirect:/post/users/wall?postInGroupSuccess";
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

	@RequestMapping(value = "/users/wall", method = RequestMethod.GET)
	public String usersWallPostPage(ModelMap modelMap) {
		if(proxyServerService.getProxyServerByDestiny("user").isEmpty()){
			return "noproxy";
		}
		if(userFromVkService.getAllUsersOfVk().isEmpty()){
			return "nousers";
		}
		modelMap.addAttribute("vkusers", userFromVkService.getAllUsersOfVk());
		modelMap.addAttribute("mapSchedule", mapSchedule);
		return "usersposts";
	}

	@RequestMapping(value = "/users/wall", method = RequestMethod.POST)
	public String searchUsersWallPosts(ModelMap modelMap,
									   @RequestParam(value = "query") String query,
									   @RequestParam(value = "time") Long time) {
		mapSchedule.put(query, scheduledExecutorService.scheduleAtFixedRate(() -> {
			usersPostsService.getUsersPosts(query);
		}, 0, time, TimeUnit.MINUTES));
		return "redirect:/post/users/wall";
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

	private String redirect(String from) {
		if ("group".equals(from)) {
			return "redirect:/post/";
		} else if ("news".equals(from)) {
			return "redirect:/post/news";
		} else if ("user".equals(from)) {
			return "redirect:/post/users/wall";
		}
		return home;
	}

}
