package vms.services.impl.posts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import vms.globalconstants.ConstantsForVkApi;
import vms.models.groups.Group;
import vms.models.posts.Photo;
import vms.models.posts.Post;
import vms.models.posts.Query;
import vms.models.posts.RootObject;
import vms.models.property.Property;
import vms.models.property.ProxyServer;
import vms.services.absr.posts.PostSearchService;
import vms.services.absr.posts.QueryService;
import vms.services.absr.posts.VkPostService;
import vms.services.absr.property.PropertyService;
import vms.services.absr.property.ProxyServerService;
import vms.services.absr.users.SearchUsersService;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;


@Service
public class PostSearchServiceImpl implements PostSearchService {

	public static final int TWENTY_FOUR_HOURS = 86_400_000;
	@Autowired
	private PropertyService propertyService;

	@Autowired
	private ProxyServerService proxyServerService;

	@Autowired
	private SearchUsersService searchUsersService;

	@Autowired
	private VkPostService vkPostService;

	@Autowired
	private QueryService queryService;

	private static final Logger logger = LoggerFactory.getLogger(PostSearchServiceImpl.class);

	/**
	 * confirm one PostResponse object from any PostResponse
	 *
	 * @param groups list of Groups
	 * @param query  keywords for search
	 * @return object PostResponse
	 */
	@Override
	public void getPostResponseByGroupsList(List<Group> groups, String query) {
		int counterProxy = 0;
		int firstElement = 0;
		int lastElement = 0;


		List<Post> postsInBD = vkPostService.getAllPostFromDb();

		List<ProxyServer> allProxyServers = proxyServerService.proxyServerList();

		List<ProxyServer> proxyServerList = null;
		ExecutorService executorService = null;

		if (allProxyServers.size() != 0) {
			proxyServerList = new ArrayList<>();

			for (ProxyServer proxyServer : allProxyServers) {
				if (proxyServer.getDestiny().equalsIgnoreCase("groups")) {
					proxyServerList.add(proxyServer);
				}
			}
			executorService = Executors.newFixedThreadPool(proxyServerList.size());

			if (groups.size() > 1) {
				int requestOnProxy = groups.size() / proxyServerList.size();
				int remainingRequests = groups.size() % proxyServerList.size();
			/*
				If we have proxy > groups
			 */
				if (requestOnProxy < 1) {
					for (int i = 0; i < groups.size(); i++) {
						searchingThread(executorService, proxyServerList, postsInBD, groups, query, i);
					}
				} else {
					for (ProxyServer proxyServer : proxyServerList) {
						RestTemplate proxyTemplate = searchUsersService.getRestTemplate(proxyServerList.get(counterProxy).getIp(), proxyServerList.get(counterProxy).getPort());
						lastElement += requestOnProxy;
						if (remainingRequests > 0) {
							remainingRequests--;
							lastElement += 1;
						}
						final int start = firstElement;
						final int finish = lastElement;
						executorService.submit(new Thread(() -> {
							for (int i = start; i < finish; i++) {
								List<Post> postList = getPostResponseByGroupName(proxyTemplate, proxyServer.getToken(), groups.get(i).getId(), query);
								if (i % 3 == 0) {
									try {
										Thread.sleep(1000);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
								}
								getPostFromList(postsInBD, postList, "groups", query);
							}
						}));
						firstElement += lastElement;
						counterProxy++;
					}
				}
			} else {
				searchingThread(executorService, proxyServerList, postsInBD, groups, query, 0);
			}
		}

		if (propertyService.getAllProperties().size() == 1) {
			Property property = propertyService.getPropertyById(1L);
			int count = 0;
			RestTemplate restTemplate = new RestTemplate();
			List<Post> postList = null;
			for (Group group : groups) {
				postList = getPostResponseByGroupNameWithoutProxy(restTemplate, property.getValue(), group.getId(), query);
				if (count % 3 == 0) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				getPostFromList(postsInBD, postList, "groups", query);
				count++;
			}
		} else {
			List<Property> propertyList = propertyService.getAllProperties();
			int randomProperty = new Random().nextInt(propertyList.size());

			int requestOnProxy = groups.size() / propertyList.size();
			int remainingRequests = groups.size() % propertyList.size();
			int count = 0;

			if (requestOnProxy < 1) {
				RestTemplate restTemplate = new RestTemplate();
				List<Post> postList = null;
				for (Group group : groups) {
					postList = getPostResponseByGroupNameWithoutProxy(restTemplate, propertyList.get(randomProperty).getValue(), group.getId(), query);
					if (count % 3 == 0) {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					getPostFromList(postsInBD, postList, "groups", query);
					count++;
				}
			} else {
				for (Property property : propertyList) {
					RestTemplate proxyTemplate = new RestTemplate();
					lastElement += requestOnProxy;
					if (remainingRequests > 0) {
						remainingRequests--;
						lastElement += 1;
					}
					for (int i = firstElement; i < lastElement; i++) {
						List<Post> postList = getPostResponseByGroupNameWithoutProxy(proxyTemplate, property.getValue(), groups.get(i).getId(), query);
						if (i % 3 == 0) {
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						getPostFromList(postsInBD, postList, "groups", query);
					}
					firstElement += lastElement;
					counterProxy++;
				}
			}
		}
	}

	@Override
	public List<Post> getPostResponseByGroupName(RestTemplate proxyTemplate, String token, Long id, String query) {
		RootObject rootObject = null;
		ProxyServer proxyServer = proxyServerService.getProxyServerByToken(token);
		try {
			rootObject = proxyTemplate.getForObject(getUriQueryWall(token, id, query), RootObject.class);
		} catch (ResourceAccessException exp) {
			/*
				Check proxy if it still works, ping to host
		 	*/
			if (!isProxyAlive(proxyServer, ConstantsForVkApi.TIMEOUT)) {
				proxyServer.setWork(false);
				proxyServerService.addProxyServer(proxyServer);
			}
		}
		/*
			Check if proxy works as normal mode
		 */
		if (rootObject != null && rootObject.getPostResponse() != null) {
			rootObject.getPostResponse().getPosts().removeIf(post -> post.getMarkedAsAds() == 1);
			proxyServer.setWork(true);
			proxyServerService.addProxyServer(proxyServer);
			return rootObject.getPostResponse().getPosts();
		}
		return null;
	}

	@Override
	public List<Post> getPostResponseByGroupNameWithoutProxy(RestTemplate proxyTemplate, String token, Long id, String query) throws ResourceAccessException {

		RootObject rootObject = null;
		rootObject = proxyTemplate.getForObject(getUriQueryWall(token, id, query), RootObject.class);
		/*
			Check if proxy works as normal mode
		 */
		if (rootObject != null && rootObject.getPostResponse() != null) {
			rootObject.getPostResponse().getPosts().removeIf(post -> post.getMarkedAsAds() == 1);
			return rootObject.getPostResponse().getPosts();
		}
		return null;

	}


	private String getUriQueryWall(String proxyServer, Long ownerId, String query) {
		StringBuilder sb = new StringBuilder(ConstantsForVkApi.URL);
		sb.append(ConstantsForVkApi.PARAMETER_GROUP_SEARCH)
				.append(ownerId)
				.append(ConstantsForVkApi.PARAMETER_GROUP_VERSION)
				.append(ConstantsForVkApi.PARAMETER_GROUP_QUERY)
				.append(query)
				.append(ConstantsForVkApi.PARAMETER_GROUP_COUNT)
				.append(ConstantsForVkApi.TOKEN)
				.append(proxyServer);
		return sb.toString();
	}

	/*
		Threads are searching posts from groups and comparing if we have this in our Data Base or not. If not - add to DB.
		If yes but some if them no then create a list and add to it only original posts which we don't have in DB.
	*/
	@Override
	public void getPostFromList(List<Post> postsInBD, List<Post> result, String from, String query) {
		Date date = new Date();
		Query word = queryService.getQuery(query, from);
		List<Post> posts = vkPostService.findPostsBlackListAndFrom(true, from);
		List<Post> queryPostList = word.getPosts().stream().filter(post -> Boolean.FALSE.equals(post.isBlackList())).collect(Collectors.toList());


		if (word == null) {
			word = new Query(query, from);
		}

		/**
		 * 86_400_000 - 24 hours in milliseconds
		 */
		Long daysAgoDate = date.getTime() - TWENTY_FOUR_HOURS;

		Query finalWord = word;
		result.stream()
				.filter(post -> post.getDate().getTime() >= daysAgoDate)
				.filter(post -> post.getText().length() != 0)
				.forEach(post -> {
					post.setWord(query);
					post.setFromWhere(from);
					post.setQuery(finalWord);
					if (post.getAttachmentContainers() != null) {
						List<Photo> photos = new ArrayList<>();
						post.getAttachmentContainers().forEach(container -> {
							if (container.getType().equals("photo")) {
								Photo photo = new Photo();
								photo.setPost(post);
								photo.setPostIdInVk(container.getPhoto().getId());
								photo.setOwnerIdInVk(container.getPhoto().getOwnerId());
								if (container.getPhoto().getPhoto604() != null) {
									photo.setReferenceOnPost(container.getPhoto().getPhoto604());
									post.setHavePhoto(true);
								} else if (container.getPhoto().getPhoto75() != null) {
									photo.setReferenceOnPost(container.getPhoto().getPhoto75());
									post.setHavePhoto(true);
								} else if (container.getPhoto().getPhoto130() != null) {
									photo.setReferenceOnPost(container.getPhoto().getPhoto130());
									post.setHavePhoto(true);
								}
								photos.add(photo);
							}
						});
						post.setPhotos(photos);
					}
				});

		if ((!posts.containsAll(result)) && ((!queryPostList.containsAll(result))) && (!postsInBD.containsAll(result))) {
			Set<Post> postSet = new HashSet<>(result);
			word.setPosts(postSet);
			queryService.addQuery(word);
		} else if (postsInBD.containsAll(result) && result.size() != 0) {
			result.stream()
					.filter(post -> !postsInBD.contains(post));
			Set<Post> postSet = new HashSet<>(result);
			word.setPosts(postSet);
			queryService.addQuery(word);
		}
	}


	/*
			If we have  one groups or less then proxy
		*/
	private void searchingThread(ExecutorService executorService, List<ProxyServer> proxyServerList, List<Post> postsInBD, List<Group> groups, String query, Integer i) {
		executorService.submit(new Thread(() -> {
			int randomProxy = new Random().nextInt(proxyServerList.size());
			RestTemplate proxyTemplate = searchUsersService.getRestTemplate(proxyServerList.get(randomProxy).getIp(), proxyServerList.get(randomProxy).getPort());
			List<Post> postList = getPostResponseByGroupName(proxyTemplate, proxyServerList.get(randomProxy).getToken(), groups.get(i).getId(), query);
			getPostFromList(postsInBD, postList, "groups", query);
		}));
	}

	private boolean isProxyAlive(ProxyServer proxyServer, int timeout) {
		try (Socket socket = new Socket()) {
			socket.connect(new InetSocketAddress(proxyServer.getIp(), proxyServer.getPort()), timeout);
			return true;
		} catch (IOException e) {
			return false;
		}
	}
}
