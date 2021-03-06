package vms.controllers.posts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import vms.globalconstants.ConstantsForVkApi;
import vms.models.posts.Post;
import vms.models.posts.Query;
import vms.models.property.ProxyServer;
import vms.services.absr.groups.GroupService;
import vms.services.absr.posts.PostSearchService;
import vms.services.absr.posts.QueryService;
import vms.services.absr.posts.VkPostService;
import vms.services.absr.property.ProxyServerService;
import vms.services.absr.users.SearchUsersPostsService;
import vms.services.absr.users.UserFromVkService;
import vms.services.impl.posts.NewsSearchService;
import vms.services.impl.posts.PostToGroupService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

	private final Map<Query, ScheduledFuture<?>> mapSchedule = new ConcurrentHashMap<>();

	private static final int DELETE_THREADS = 1;

	private final ScheduledExecutorService deleteOldPosts = Executors.newScheduledThreadPool(DELETE_THREADS);

	@RequestMapping(value = {"/"}, method = RequestMethod.GET)
	public String getPostsFromDb(Model model,
								 @RequestParam(value = "key", required = false) String key,
								 @RequestParam(value = "fromwhere", required = false) String from) {

		List<ProxyServer> badProxy = proxyServerService.findBadProxy(false);

		if (key != null && from != null) {
			Query query = queryService.getQuery(key, from);

			if (query != null) {
				model.addAttribute("posts", query.getPosts().stream().filter(post -> Boolean.FALSE.equals(post.isBlackList())).collect(Collectors.toList()));
			}

			model.addAttribute("mapSchedule", findMap(mapSchedule, "groups"));
			model.addAttribute("badproxy", badProxy);
			return "posts/posts";
		} else {
			List<Post> result = postService.findPostsBlackListAndFrom(false, "groups");

			prepareView(result);
			preparationPost(result);

			model.addAttribute("posts", result);
			model.addAttribute("mapSchedule", findMap(mapSchedule, "groups"));
			model.addAttribute("badproxy", badProxy);

			return "posts/posts";
		}
	}

	@RequestMapping(value = {"/add"}, method = RequestMethod.POST)
	public String addPosts(@ModelAttribute List<Post> posts) {
		posts.forEach(post -> post.setFromWhere("groups"));
		postService.addPosts(posts);
		return home;
	}

	@RequestMapping(value = {"/blacklistphone"}, method = RequestMethod.GET)
	public String showBlackListPhone(Model model) {
		List<Post> blackList = postService.getAllBlackPosts();
		prepareView(blackList);
		model.addAttribute("blackListPhone", blackList);
		return "posts/blacklistphone";
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
		return "redirect:/posts/blacklistphone";
	}

	@RequestMapping(value = {"/blacklisturl"}, method = RequestMethod.GET)
	public String showBlackListURL(Model model) {
		List<Post> blackList = postService.getAllBlackPosts();
		prepareView(blackList);
		model.addAttribute("blackListURL", blackList);
		return "posts/blacklisturl";
	}


	@RequestMapping(value = {"/news"}, method = RequestMethod.GET)
	public String getNews(Model model,
						  @RequestParam(value = "key", required = false) String key,
						  @RequestParam(value = "fromwhere", required = false) String from) {

		List<ProxyServer> badProxy = proxyServerService.findBadProxy(false);
		if (key != null && from != null) {
			Query query = queryService.getQuery(key, from);

			if (query != null) {
				model.addAttribute("posts", query.getPosts().stream().filter(post -> Boolean.FALSE.equals(post.isBlackList())).collect(Collectors.toList()));
			}

			model.addAttribute("mapSchedule", findMap(mapSchedule, "news"));
			model.addAttribute("badproxy", badProxy);
			return "posts/newspost";
		} else {
			List<Post> result = postService.findPostsBlackListAndFrom(false, "news");
			prepareView(result);
			preparationPost(result);
			model.addAttribute("posts", result);
			model.addAttribute("mapSchedule", findMap(mapSchedule, "news"));
			model.addAttribute("badproxy", badProxy);
			return "posts/newspost";
		}
	}

	@RequestMapping(value = {"/stop"}, method = RequestMethod.POST)
	public String cancelThread(Model model,
							   @RequestParam(value = "key") String key,
							   @RequestParam(value = "fromwhere") String from) {

		Query query = queryService.getQuery(key, from);

		if (query != null && mapSchedule.get(query) != null) {
			mapSchedule.get(query).cancel(true);
			mapSchedule.remove(query);
		}

		return redirect(from);
	}

	@RequestMapping(value = {"/news/find"}, method = RequestMethod.POST)
	public String getNews(Model model,
						  @RequestParam(value = "news") String query,
						  @RequestParam(value = "time") Long time) {

		Query word = queryService.getQuery(query, "news");

		List<Post> result = postService.findPostsBlackListAndFrom(false, "news");
		prepareView(result);
		preparationPost(result);
		Set<Post> postSet = new HashSet<>(result);

		if (word == null) {
			word = new Query(query, "news");
			word.setPosts(postSet);
			queryService.addQuery(word);

			mapSchedule.put(word, scheduledExecutorService.scheduleAtFixedRate(() -> {
				newsSearchService.getAdsFromNews(query);
			}, 0, time, TimeUnit.MINUTES));

		} else {
			return "redirect:/post/news?wordExist";

		}


		model.addAttribute("posts", result);
		model.addAttribute("mapSchedule", mapSchedule);
		return "redirect:/post/news";
	}

	@RequestMapping(value = {"/update"}, method = RequestMethod.POST)
	public String updatePosts(Model model,
							  @RequestParam(value = "query") String key,
							  @RequestParam(value = "id") Long id,
							  @RequestParam(value = "dbId") Long dbId,
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
		Date dateOfPost = getDate(date);
		Query query = queryService.getQuery(key, from);
		Post editedPost = new Post(dbId, id, title, owner, district, price, textOnView, adress, contact, info, from, dateOfPost, key);
		Set<Post> postSet = query.getPosts();
		hasPhoto(dbId, from, editedPost);

		updatePost(dbId, from, query, editedPost, postSet);

		return redirect(from);
	}

	@RequestMapping(value = {"/deletePost"}, method = RequestMethod.POST)
	public String deletePosts(@RequestParam(value = "fromwhere") String from,
							  @RequestParam(value = "dbId", required = false) Long dbId) {

		List<Query> queryList = queryService.findAllByFrom(from);
		Post post = postService.getByIdAndFrom(dbId, from);
		if (queryList.size() != 0) {
			queryList.forEach(query -> {
				Set<Post> postList = query.getPosts();
				if (postList.contains(post)) {
					post.setBlackList(true);
					query.setPosts(postList);
					queryService.addQuery(query);
				}
			});
			if (post != null) {
				post.setBlackList(true);
				postService.addPost(post);
			}
		}

		deleteBlackListPosts(from);

		return redirect(from);
	}

	@RequestMapping(value = {"/deleteAllPosts"}, method = RequestMethod.POST)
	public String deleteAllPosts(@RequestParam(value = "fromwhere") String from) {

		List<Post> postList = postService.getParticualPosts(from);

		if (postList != null) {
			postService.deleteAllPosts(postList);
		}

		deleteBlackListPosts(from);

		return redirect(from);
	}

	@RequestMapping(value = {"/deleteAllQuery"}, method = RequestMethod.POST)
	public String deleteAllQuery(@RequestParam(value = "fromwhere") String from) {

		List<Query> queryList = queryService.getAllQueryFromDb();

		if (queryList != null) {

			queryList.forEach(query -> {

				if (mapSchedule.get(query) != null) {
					mapSchedule.get(query).cancel(true);
					mapSchedule.remove(query);
				}

			});

			queryService.deleteAllQuery(queryList);
		}


		deleteBlackListPosts(from);

		return redirect(from);
	}


	@RequestMapping(value = {"/req"}, method = RequestMethod.POST)
	public String getNewPosts(Model model,
							  @RequestParam(value = "query") String query,
							  @RequestParam(value = "time") Long time) {

		Query word = queryService.getQuery(query, "groups");
		List<Post> result = postService.findPostsBlackListAndFrom(false, "groups");
		List<ProxyServer> badProxy = proxyServerService.findBadProxy(false);
		Set<Post> postSet = new HashSet<>(result);


		if (word == null) {
			word = new Query(query, "groups");

			word.setPosts(postSet);
			queryService.addQuery(word);
			mapSchedule.put(word, scheduledExecutorService.scheduleAtFixedRate(() -> {
				postSearchServiceImpl.getPostResponseByGroupsList(groupService.listAllVkGroups(), query);
			}, 0, time, TimeUnit.MINUTES));
		} else {
			 return "redirect:/post/?wordExist";
		}

		if (result != null) {
			prepareView(result);
			preparationPost(result);
			model.addAttribute("posts", result);
			model.addAttribute("badproxy", badProxy);
			return "redirect:/post/";
		}

		return "redirect:/post/?warning";
	}

	@RequestMapping(value = {"/addPost"}, method = RequestMethod.POST)
	public String doPostToGroup(@RequestParam(value = "id") Long id,
								@RequestParam(value = "dbId") Long dbId,
								@RequestParam(value = "query") String key,
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

		Date dateOfPost = getDate(date);
		Query query = queryService.getQuery(key, from);
		Post postToGroup = new Post(dbId, id, title, owner, district, price, textOnView, adress, contact, info, from, dateOfPost, key);
		hasPhoto(dbId, from, postToGroup);
		Set<Post> postSet = query.getPosts();
		String result = postToGroupService.postToGroup(ConstantsForVkApi.ID_GROUP, postToGroup);
		if ("news".equals(from)) {
			if (result == null || result.contains("error_code")) {
				return "redirect:/post/news/?postInGroupDanger";
			}
			postToGroup.setSavedInDb(save);
			postToGroup.setPostedToGroup(true);
			postSet.add(postToGroup);
			query.setPosts(postSet);
			queryService.addQuery(query);
			return "redirect:/post/news/?postInGroupSuccess";
		} else if ("user".equals(from)) {
			if (result == null || result.contains("error_code")) {
				return "redirect:/post/users/wall?postInGroupDanger";
			}
			postToGroup.setSavedInDb(save);
			postToGroup.setPostedToGroup(true);
			postSet.add(postToGroup);
			query.setPosts(postSet);
			queryService.addQuery(query);
			return "redirect:/post/users/wall?postInGroupSuccess";
		} else {
			if (result == null || result.contains("error_code")) {
				return "redirect:/post/?postInGroupDanger";
			}
			postToGroup.setSavedInDb(save);
			postToGroup.setPostedToGroup(true);
			postSet.add(postToGroup);
			query.setPosts(postSet);
			queryService.addQuery(query);
			return "redirect:/post/?postInGroupSuccess";
		}
	}

	@RequestMapping(value = "/users/wall", method = RequestMethod.GET)
	public String usersWallPostPage(ModelMap modelMap,
									@RequestParam(value = "key", required = false) String key,
									@RequestParam(value = "fromwhere", required = false) String from) {

		if (proxyServerService.getProxyServerByDestiny("user").isEmpty()) {
			return "property/noproxy";
		}
		if (userFromVkService.getAllUsersOfVk().isEmpty()) {
			return "property/nousers";
		}

		modelMap.addAttribute("vkusers", userFromVkService.getAllUsersOfVk());
		modelMap.addAttribute("mapSchedule", findMap(mapSchedule, "user"));
		return "users/usersposts";
	}

	@RequestMapping(value = "/users/wall", method = RequestMethod.POST)
	public String searchUsersWallPosts(ModelMap modelMap,
									   @RequestParam(value = "query") String query,
									   @RequestParam(value = "time") Long time) {

		Query word = queryService.getQuery(query, "user");

		if (word == null) {
			word = new Query(query, "user");
		}

		queryService.addQuery(word);

		mapSchedule.put(word, scheduledExecutorService.scheduleAtFixedRate(() -> {
			usersPostsService.getUsersPosts(query);
		}, 0, time, TimeUnit.MINUTES));
		return "redirect:/post/users/wall";
	}

	private void prepareView(List<Post> posts) {
		posts.sort(Comparator.comparing(Post::getDate).reversed());
		posts.forEach(post -> post.setOwnerId(Math.abs(post.getOwnerId())));
	}


	private void preparationPost(List<Post> posts) {
		for (Post postCurrent : posts) {
			if (!postCurrent.isSavedInDb()) {
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
		if ("groups".equals(from)) {
			return "redirect:/post/";
		} else if ("news".equals(from)) {
			return "redirect:/post/news";
		} else if ("user".equals(from)) {
			return "redirect:/post/users/wall";
		}
		return home;
	}

	private Map<Query, ScheduledFuture<?>> findMap(Map<Query, ScheduledFuture<?>> mapSchedule, String from) {
		return mapSchedule.entrySet()
				.stream().filter(query -> from.equals(query.getKey().getFromwhere()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	}

	private void hasPhoto(Long dbId, String from, Post editedPost) {

		Post post = postService.getByIdAndFrom(dbId, from);
		if (post != null && post.isHavePhoto()) {
			editedPost.setHavePhoto(true);
			editedPost.setPhotos(post.getPhotos());
		}
	}

	private Date getDate(String date) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dateOfPost = null;
		dateOfPost = format.parse(date);
		return dateOfPost;
	}

	private void updatePost(Long dbId, String from, Query query, Post editedPost, Set<Post> postSet) {
		try {
			editedPost.setSavedInDb(true);
			postSet.remove(postService.getByIdAndFrom(dbId, from));
			postSet.add(editedPost);
			query.setPosts(postSet);
			queryService.update(query);
		} catch (DataIntegrityViolationException exp) {
			exp.printStackTrace();
		}
	}

	private void deleteBlackListPosts(String from) {

		scheduledExecutorService.scheduleAtFixedRate(() -> {

			List<Post> posts = postService.findPostsBlackListAndFrom(true, from);

			if (posts != null) {
				postService.delete(posts);
			}

			List<Query> queryList = queryService.findAllByFrom(from);

			if (queryList != null) {

				queryList.forEach(query -> {
					List<Post> queryPostList = query.getPosts().stream().filter(post -> Boolean.FALSE.equals(post.isBlackList())).collect(Collectors.toList());

					postService.delete(queryPostList);
				});

			}

		}, 0, 24, TimeUnit.HOURS);
	}

}
