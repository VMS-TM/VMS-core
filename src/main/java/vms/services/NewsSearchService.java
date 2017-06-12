package vms.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import vms.models.ProxyServer;
import vms.models.postenvironment.Post;
import vms.models.postenvironment.PostResponse;
import vms.models.postenvironment.RootObject;
import vms.models.rawgroup.Group;
import vms.services.absr.ProxyServerService;
import vms.services.absr.SearchUsersService;
import vms.services.absr.VkPostService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
public class NewsSearchService {

	@Autowired
	PropertySearchService propertySearchService;

	@Autowired
	private VkPostService postService;

	final String uri = "https://api.vk.com/method";
	final Integer COUNT = 200;

	private String UriForAdsFromNews(String query, Integer count, String proxyServer) {
		StringBuilder sb = new StringBuilder(uri);
		sb.append("/newsfeed.search?q=")
				.append(query)
				.append("&extended=1")
				.append("&count=")
				.append(count)
				.append("&access_token=")
				.append(proxyServer)
				.append("&v=5.65");
		return sb.toString();
	}

	public void getAdsFromNews(String query) {

		RestTemplate restTemplate = new RestTemplate();
		List<Post> postsInBD = postService.getAllPostFromDb();

		RootObject rootObject = restTemplate.getForObject(UriForAdsFromNews(query, COUNT, propertySearchService.getValue("defaultKey")), RootObject.class);
		List<Post> posts = rootObject.getPostResponse().getPosts();
		List<Post> result = posts.stream()
				.filter(post -> !(post.getOwnerId() < 0))
				.collect(Collectors.toList());

		if (!postsInBD.containsAll(result)) {
				result.forEach(post -> post.setFromWhere("news"));
				postService.addPosts(result);
		} else if (postsInBD.containsAll(result) && result.size() != 0) {
			List<Post> postsWhichNotInDB = new ArrayList<>();

			for (Post post : result) {
				if (!postsInBD.contains(post)) {
					post.setFromWhere("news");
					postsWhichNotInDB.add(post);
				}
			}

			postService.addPosts(postsWhichNotInDB);
		}
	}

}
