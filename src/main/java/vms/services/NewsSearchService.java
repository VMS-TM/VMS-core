package vms.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import vms.models.postenvironment.Post;
import vms.models.postenvironment.RootObject;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NewsSearchService {

	@Autowired
	PropertySearchService propertySearchService;

	final String uri = "https://api.vk.com/method";
	final Integer COUNT = 2;

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

	public List<Post> getAdsFromNews(String query) {

		RestTemplate restTemplate = new RestTemplate();

		RootObject rootObject = restTemplate.getForObject(UriForAdsFromNews(query, COUNT, propertySearchService.getValue("defaultKey")), RootObject.class);

		List<Post> posts = rootObject.getPostResponse().getPosts();

		List<Post> result = posts.stream()
				.filter(post -> !(post.getOwnerId() < 0))
				.collect(Collectors.toList());

		if (result.size() != 0) {
			return result;
		}

		return null;

	}

}
