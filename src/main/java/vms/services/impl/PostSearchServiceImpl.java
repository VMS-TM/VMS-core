package vms.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import vms.models.ProxyServer;
import vms.models.postenvironment.Post;
import vms.models.postenvironment.PostResponse;
import vms.models.postenvironment.RootObject;
import vms.models.rawgroup.Group;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import vms.services.absr.PostSearchService;
import vms.services.absr.ProxyServerService;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;


@Service
public class PostSearchServiceImpl implements PostSearchService {

	@Autowired
	private ProxyServerService proxyServerService;

	//constants for query
	final String ACCESS_TOKEN = "808bcfd51bd94b4d0593a2dda57037fc4fdc46cac46e20d1b260c1a90d88b4c23023dd977e9639f7f8279";
	final String uri = "https://api.vk.com/method";
	final String version = "&v=5.63";
	private int count = 0;

	/**
	 * confirm one PostResponse object from any PostResponse
	 *
	 * @param groups list of Groups
	 * @param query  keywords for search
	 * @return object PostResponse
	 */
	@Override
	public PostResponse getPostResponseByGroupsList(List<Group> groups, String query) {
		List<ProxyServer> proxyServerList = new ArrayList<>();
		proxyServerList.addAll(proxyServerService.proxyServerList());
		PostResponse postResponseSum = new PostResponse();
		int count = 0;
		int counterProxy = 0;
		int firstElement = 0;
		int lastElement = 0;
		ArrayList<Post> posts = new ArrayList<>();
		List<PostResponse> responseList = new ArrayList<>();
		ExecutorService executorService = Executors.newFixedThreadPool(proxyServerList.size());

		int requestOnProxy = groups.size() / proxyServerList.size();
		int remainingRequests = groups.size() % proxyServerList.size();

		for (ProxyServer proxyServer : proxyServerList) {
			RestTemplate proxyTemplate = getRestTemplate(proxyServerList.get(counterProxy).getIp(), proxyServerList.get(counterProxy).getPort());
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
					responseList.add(postResponse);
				}
			}));
			firstElement += lastElement;
			counterProxy++;
		}

		try {
			executorService.shutdown();
			executorService.awaitTermination(Integer.MAX_VALUE, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		for (PostResponse postResponse : responseList) {
			if (postResponse != null) {
				posts.addAll(postResponse.getPosts());
				count += postResponse.getCount();
			}
		}

		if (posts.size() > 0) {
			postResponseSum.setPosts(posts);
			postResponseSum.setCount(count);
		}

		return postResponseSum;
	}

	@Override
	public PostResponse getPostResponseByGroupName(RestTemplate proxyTemplate, String proxyServer, String nameGroup, String query) {
		RootObject rootObject = proxyTemplate.getForObject(getUriQueryWall(proxyServer, nameGroup, query), RootObject.class);
		if (rootObject.getPostResponse() != null) {
			rootObject.getPostResponse().getPosts().removeIf(post -> post.getMarkedAsAds() == 1);
			return rootObject.getPostResponse();
		}
		return null;
	}

	private String getUriQueryWall(String proxyServer, String ownerId, String query) {
		StringBuilder sb = new StringBuilder(uri);
		sb.append("/wall.search?owner_id=-");
		sb.append(ownerId);
		sb.append("&v=5.63");
		sb.append("&query=");
		sb.append(query);
		sb.append("&count=100");
		sb.append("&access_token=");
		sb.append(proxyServer);
		return sb.toString();
	}

	private String getUriQueryWall(String ownerId, String query, int count) {
		StringBuilder sb = new StringBuilder(uri);
		sb.append("/wall.search?owner_id=");
		sb.append(ownerId);
		sb.append("&v=5.63");
		sb.append("&count=");
		sb.append(count);
		sb.append("&query=");
		sb.append(query);
		sb.append("&access_token=");
		sb.append(ACCESS_TOKEN);
		return sb.toString();
	}

	private RestTemplate getRestTemplate(String ip, int port) {
		SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
		InetSocketAddress address = new InetSocketAddress(ip, port);
		Proxy proxy = new Proxy(Proxy.Type.HTTP, address);

		factory.setProxy(proxy);

		return new RestTemplate(factory);
	}
}

