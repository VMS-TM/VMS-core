package vms.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.ResourceAccessException;
import vms.globalVariables.ConstantsForVkApi;
import vms.models.ProxyServer;
import vms.models.postenvironment.Photo;
import vms.models.postenvironment.Post;
import vms.models.postenvironment.PostResponse;
import vms.models.postenvironment.RootObject;
import vms.models.rawgroup.Group;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import vms.services.absr.PostSearchService;
import vms.services.absr.ProxyServerService;
import vms.services.absr.SearchUsersService;
import vms.services.absr.VkPostService;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.stream.Collectors;


@Service
public class PostSearchServiceImpl implements PostSearchService {

	@Autowired
	private ProxyServerService proxyServerService;

	@Autowired
	private SearchUsersService searchUsersService;

	@Autowired
	private VkPostService vkPostService;

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
		List<ProxyServer> proxyServerList = new ArrayList<>();

		for (ProxyServer proxyServer : allProxyServers) {
			if (proxyServer.getDestiny().equalsIgnoreCase("group")) {
				proxyServerList.add(proxyServer);
			}
		}

		ExecutorService executorService = Executors.newFixedThreadPool(proxyServerList.size());

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
							getPostFromList(postsInBD, postList, "group");
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

	@Override
	public List<Post> getPostResponseByGroupName(RestTemplate proxyTemplate, String token, String nameGroup, String query) {
		RootObject rootObject = null;
		ProxyServer proxyServer = proxyServerService.getProxyServerByToken(token);
		try {
			rootObject = proxyTemplate.getForObject(getUriQueryWall(token, nameGroup, query), RootObject.class);
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
		if (rootObject.getPostResponse() != null) {
			rootObject.getPostResponse().getPosts().removeIf(post -> post.getMarkedAsAds() == 1);
			proxyServer.setWork(true);
			proxyServerService.addProxyServer(proxyServer);
			return rootObject.getPostResponse().getPosts();
		}
		return null;
	}

	private String getUriQueryWall(String proxyServer, String ownerId, String query) {
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
	public void getPostFromList(List<Post> postsInBD, List<Post> result, String from) {
		Date date = new Date();
		/**
		 * 86_400_000 - 24 hours in milliseconds
		 */
		Long  daysAgoDate = date.getTime() - 86_400_000;

		result.forEach(post -> {
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
		result.forEach(post -> post.setFromWhere(from));

		/**
		 * Deletes found posts if they have an empty text field and are made earlier than in the last 24 hours
		 */
		result = result.stream().filter(post -> post.getText().length() != 0)
				.filter(post -> post.getDate().getTime() >= daysAgoDate)
				.collect(Collectors.toList());

		if (!postsInBD.containsAll(result)) {
			vkPostService.addPosts(result);
		} else if (postsInBD.containsAll(result) && result.size() != 0) {
			List<Post> postsWhichNotInDB = result.stream()
					.filter(post -> !postsInBD.contains(post))
					.collect(Collectors.toList());
			vkPostService.addPosts(postsWhichNotInDB);
		}
	}


	/*
		If we have  one group or less then proxy
	*/
	private void searchingThread(ExecutorService executorService, List<ProxyServer> proxyServerList, List<Post> postsInBD, List<Group> groups, String query, Integer i) {
		executorService.submit(new Thread(() -> {
			int randomProxy = new Random().nextInt(proxyServerList.size());
			RestTemplate proxyTemplate = searchUsersService.getRestTemplate(proxyServerList.get(randomProxy).getIp(), proxyServerList.get(randomProxy).getPort());
			List<Post> postList = getPostResponseByGroupName(proxyTemplate, proxyServerList.get(randomProxy).getToken(), groups.get(i).getId(), query);
			getPostFromList(postsInBD, postList, "group");
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

