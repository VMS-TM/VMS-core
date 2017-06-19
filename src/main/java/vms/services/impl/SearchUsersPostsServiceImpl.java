package vms.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import vms.models.postenvironment.Post;
import vms.models.postenvironment.RootObject;
import vms.services.absr.PhotoService;
import vms.services.absr.PostSearchService;
import vms.services.absr.SearchUsersPostsService;
import vms.services.absr.VkPostService;

import java.util.List;

@Service
public class SearchUsersPostsServiceImpl implements SearchUsersPostsService {

	@Autowired
	private PhotoService photoService;

	@Autowired
	private VkPostService vkPostService;

	@Autowired
	private PostSearchService postSearchService;

	private RestTemplate restTemplate = new RestTemplate();

	@Override
	public void getUsersPosts(String query) {
		String url = "https://api.vk.com/method/wall.search?count=100&owner_id=419790750&query=Аренда&access_token=bb6211359dc3e3eb8f6fe190d36e8a42ad054dd2e146ec845072dcd19377d474caafd648b3712e3254e54&v=5.52";
		RootObject rootObject = restTemplate.getForObject(url, RootObject.class);
		List<Post> usersPostList = rootObject.getPostResponse().getPosts();
		List<Post> postsInBD = vkPostService.getAllPostFromDb();
		postSearchService.getPostFromList(postsInBD, usersPostList, "user");
	}
}
