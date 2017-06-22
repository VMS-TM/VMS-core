package vms.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import vms.globalVariables.ConstantsForVkApi;
import vms.models.ProxyServer;
import vms.models.postenvironment.Post;
import vms.models.postenvironment.RootObject;
import vms.models.usersenvironment.UserFromVK;
import vms.services.absr.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
public class SearchUsersPostsServiceImpl implements SearchUsersPostsService {

	@Autowired
	private VkPostService vkPostService;

	@Autowired
	private UserFromVkService userFromVkService;

	@Autowired
	private PostSearchService postSearchService;

	@Autowired
	private ProxyServerService proxyServerService;

	@Autowired
	private SearchUsersService searchUsersService;

	private RestTemplate restTemplate = new RestTemplate();

	private static final Logger logger = LoggerFactory.getLogger(SearchUsersPostsServiceImpl.class);

	/**
	 * Method return list of queries for search posts on user's wall
	 *
	 * @param query             for search posts on wall
	 * @param requestOnProxy    quantity of request on one proxy
	 * @param remainingRequests quantity of remaining requests
	 * @param userFromVKList    List of user's ids
	 * @param proxyServers      List pf proxy servers
	 * @return List of queries
	 */
	private List<String> getListOfQueries(String query, int requestOnProxy, int remainingRequests, List<UserFromVK> userFromVKList, List<ProxyServer> proxyServers) {
		List<String> queries = new ArrayList<>();

		StringBuilder stringBuilder = new StringBuilder();

		boolean flagNextProxyServer = false;
		int numberOfProxyServer = 0;
		int countRequest = 0;

		if (requestOnProxy == 0) {
			for (int i = 0; i < remainingRequests; i++) {
				String queryURL = stringBuilder.append(ConstantsForVkApi.URL)
						.append(ConstantsForVkApi.METHOD_GET_USERS_POSTS)
						.append(ConstantsForVkApi.PARAMETER_OWNER_ID)
						.append(userFromVKList.get(0).getIdOfUser())
						.append(ConstantsForVkApi.PARAMETER_QUERY_POST)
						.append(query)
						.append(ConstantsForVkApi.TOKEN)
						.append(proxyServers.get(0).getToken())
						.append(ConstantsForVkApi.VERSION).toString();

				queries.add(queryURL);
				stringBuilder.setLength(0);
			}
		} else {
			for (UserFromVK userFromVK : userFromVKList) {

				if (countRequest >= requestOnProxy) {
					if (remainingRequests > 0 && !flagNextProxyServer) {
						remainingRequests--;
						flagNextProxyServer = true;
					} else {
						countRequest = 0;
						numberOfProxyServer++;
						flagNextProxyServer = false;
					}
				}

				String queryURL = stringBuilder.append(ConstantsForVkApi.URL)
						.append(ConstantsForVkApi.METHOD_GET_USERS_POSTS)
						.append(ConstantsForVkApi.PARAMETER_OWNER_ID)
						.append(userFromVK.getIdOfUser())
						.append(ConstantsForVkApi.PARAMETER_QUERY_POST)
						.append(query)
						.append(ConstantsForVkApi.TOKEN)
						.append(proxyServers.get(numberOfProxyServer).getToken())
						.append(ConstantsForVkApi.VERSION).toString();

				queries.add(queryURL);
				stringBuilder.setLength(0);
				countRequest++;
			}
		}

		return queries;
	}

	/**
	 * Method which send request to vk api across proxy servers
	 *
	 * @param start         first element of list of queries
	 * @param end           last element of list of queries
	 * @param proxyTemplate	rest template with proxy properties
	 * @param queries		list of queries
	 * @param postsFromDB	list of posts from data base
	 */
	private void sendRequests(int start, int end, RestTemplate proxyTemplate, List<String> queries, List<Post> postsFromDB) {
		for (int i = start; i < end; i++) {
			if (i > 0 && i % 3 == 0) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			if (i > 0 && i % 100 == 0) {
				try {
					Thread.sleep(300_000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			RootObject rootObject = proxyTemplate.getForObject(queries.get(i), RootObject.class);

			List<Post> postList = rootObject.getPostResponse().getPosts();

			postSearchService.getPostFromList(postsFromDB, postList, "user");
		}
	}

	/**
	 * @param queries           List of queries for proxy
	 * @param proxyServers      list of proxy severs
	 * @param requestOnProxy    quantity of requests on one proxy
	 * @param remainingRequests quantity of remaining requests
	 */
	private void getListOfNewPosts(List<String> queries, List<ProxyServer> proxyServers, int requestOnProxy, int remainingRequests) {
		int firstElement = 0;
		int lastElement = 0;

		List<Post> postsFromDB = vkPostService.getAllPostFromDb();

		ExecutorService executorService = Executors.newFixedThreadPool(proxyServers.size());

		if (proxyServers.size() == 1 || requestOnProxy == 0) {
			RestTemplate proxyTemplate = searchUsersService.getRestTemplate(proxyServers.get(0).getIp(), proxyServers.get(0).getPort());
			executorService.submit(new Thread(() -> {
				sendRequests(0, queries.size(), proxyTemplate, queries, postsFromDB);
			}));

			executorService.shutdown();
		} else {
			for (ProxyServer proxyServer : proxyServers) {
				RestTemplate proxyTemplate = searchUsersService.getRestTemplate(proxyServer.getIp(), proxyServer.getPort());
				lastElement += requestOnProxy;
				if (remainingRequests > 0) {
					lastElement++;
					remainingRequests--;
				}

				final int start = firstElement;
				final int finish = lastElement;

				executorService.submit(new Thread(() -> {
					sendRequests(start, finish, proxyTemplate, queries, postsFromDB);
				}));

				firstElement += lastElement;
			}

			executorService.shutdown();
		}
	}

	/**
	 * Method for search posts on user's wall
	 *
	 * @param query
	 */
	@Override
	public void getUsersPosts(String query) {

		List<UserFromVK> userFromVKList = userFromVkService.getAllUsersOfVk();

		List<ProxyServer> proxyServers = proxyServerService.proxyServerList().stream()
				.filter(proxyServer -> "user".equals(proxyServer.getDestiny()))
				.collect(Collectors.toList());

		int requestOnProxy = userFromVKList.size() / proxyServers.size();
		int remainingRequests = userFromVKList.size() % proxyServers.size();

		List<String> queries = getListOfQueries(query, requestOnProxy, remainingRequests, userFromVKList, proxyServers);

		getListOfNewPosts(queries, proxyServers, requestOnProxy, remainingRequests);
	}
}
