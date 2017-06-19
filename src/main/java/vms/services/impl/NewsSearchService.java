package vms.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import vms.globalVariables.ConstantsForVkApi;
import vms.models.postenvironment.Post;
import vms.models.postenvironment.RootObject;
import vms.services.absr.VkPostService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NewsSearchService {

	@Autowired
	PropertySearchService propertySearchService;

	@Autowired
	private VkPostService postService;


	private String UriForAdsFromNews(String query, String proxyServer) {
		StringBuilder sb = new StringBuilder(ConstantsForVkApi.URL);
		sb.append(ConstantsForVkApi.PARAMETER_NEWS_SEARCH_METHOD)
				.append(ConstantsForVkApi.PARAMETER_NEWS_QUERY)
				.append(query)
				.append(ConstantsForVkApi.PARAMETER_NEWS_EXTENDED)
				.append(ConstantsForVkApi.PARAMETER_NEWS_COUNT)
				.append(ConstantsForVkApi.TOKEN)
				.append(proxyServer)
				.append(ConstantsForVkApi.PARAMETER_NEWS_VERSION);
		return sb.toString();
	}

	public void getAdsFromNews(String query) {

		RestTemplate restTemplate = new RestTemplate();
		List<Post> postsInBD = postService.getAllPostFromDb();

		RootObject rootObject = restTemplate.getForObject(UriForAdsFromNews(query, propertySearchService.getValue("defaultKey")), RootObject.class);
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
