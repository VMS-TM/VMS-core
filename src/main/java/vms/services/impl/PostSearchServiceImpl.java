package vms.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import vms.globalVariables.ConstantsForVkApi;
import vms.models.ProxyServer;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;


@Service
public class PostSearchServiceImpl implements PostSearchService {

	@Autowired
	private ProxyServerService proxyServerService;

	@Autowired
	private SearchUsersService searchUsersService;

	@Autowired
	private VkPostService postService;

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


		List<Post> postsInBD = postService.getAllPostFromDb();
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
				for	(int i = 0 ; i < groups.size(); i++) {
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
							PostResponse postResponse = getPostResponseByGroupName(proxyTemplate, proxyServer.getToken(), groups.get(i).getId(), query);
							if (i % 3 == 0) {
								try {
									Thread.sleep(1000);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}

							addPostToDB(postsInBD, postResponse);
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
	public PostResponse getPostResponseByGroupName(RestTemplate proxyTemplate, String token, String nameGroup, String query) {
		RootObject rootObject = proxyTemplate.getForObject(getUriQueryWall(token, nameGroup, query), RootObject.class);
		ProxyServer proxyServer = proxyServerService.getProxyServerByToken(token);

		/*
			Check if proxy works as normal mode
		 */

		if (proxyServer.getWork() == null) {
			if (rootObject != null || !rootObject.getPostResponse().getPosts().toString().contains("error")) {
				proxyServer.setWork(true);
				proxyServerService.addProxyServer(proxyServer);
			} else {
				proxyServer.setWork(false);
				proxyServerService.addProxyServer(proxyServer);
			}
		}


		if (rootObject.getPostResponse() != null) {
			rootObject.getPostResponse().getPosts().removeIf(post -> post.getMarkedAsAds() == 1);
			return rootObject.getPostResponse();
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
	private void addPostToDB(List<Post> postsInBD, PostResponse postResponse) {
		if (!postsInBD.containsAll(postResponse.getPosts())) {
			postResponse.getPosts().forEach(post -> post.setFromWhere("group"));
			postService.addPosts(postResponse.getPosts());

		} else if (postsInBD.containsAll(postResponse.getPosts()) && postResponse.getPosts().size() != 0) {
			List<Post> postsWhichNotInDB = new ArrayList<>();

			for (Post post : postResponse.getPosts()) {
				if (!postsInBD.contains(post)) {
					post.setFromWhere("group");
					postsWhichNotInDB.add(post);
				}
			}

			postService.addPosts(postsWhichNotInDB);
		}
	}

	/*
		If we have only one group in out list then we use random proxy
	*/
	private void searchingThread(ExecutorService executorService, List<ProxyServer> proxyServerList, List<Post> postsInBD, List<Group> groups, String query, Integer i) {
		executorService.submit(new Thread(() -> {
			int randomProxy = new Random().nextInt(proxyServerList.size());
			RestTemplate proxyTemplate = searchUsersService.getRestTemplate(proxyServerList.get(randomProxy).getIp(), proxyServerList.get(randomProxy).getPort());
			PostResponse postResponse = getPostResponseByGroupName(proxyTemplate, proxyServerList.get(randomProxy).getToken(), groups.get(i).getId(), query);

			addPostToDB(postsInBD, postResponse);
		}));
	}
}

