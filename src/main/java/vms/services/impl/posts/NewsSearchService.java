package vms.services.impl.posts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import vms.globalconstants.ConstantsForVkApi;
import vms.models.posts.Post;
import vms.models.posts.RootObject;
import vms.models.property.Property;
import vms.services.absr.posts.PostSearchService;
import vms.services.absr.posts.VkPostService;
import vms.services.absr.property.PropertyService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NewsSearchService {

	@Autowired
	private PropertyService propertyService;

	@Autowired
	private VkPostService vkPostService;

	@Autowired
	private PostSearchService postSearchService;

	private static final Logger logger = LoggerFactory.getLogger(NewsSearchService.class);

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
		List<Post> postsInBD = vkPostService.getAllPostFromDb();
		Property property = propertyService.getPropertyById(1L);

		RootObject rootObject = restTemplate.getForObject(UriForAdsFromNews(query, property.getValue()), RootObject.class);
		List<Post> posts = rootObject.getPostResponse().getPosts();
		List<Post> result = posts.stream()
				.filter(post -> !(post.getOwnerId() < 0))
				.collect(Collectors.toList());

		postSearchService.getPostFromList(postsInBD, result, "news", query);
	}

}
