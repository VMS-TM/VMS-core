package vms.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import vms.models.postenvironment.Photo;
import vms.models.postenvironment.Post;
import vms.models.postenvironment.RootObject;
import vms.models.postenvironment.attachmentenv.AttachmentContainer;
import vms.services.absr.PhotoService;
import vms.services.absr.SearchUsersPostsService;
import vms.services.absr.VkPostService;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchUsersPostsServiceImpl implements SearchUsersPostsService {

	@Autowired
	private PhotoService photoService;

	@Autowired
	private VkPostService vkPostService;

	private RestTemplate restTemplate = new RestTemplate();

	@Override
	public void getUsersPosts(String query) {
		String url = "https://api.vk.com/method/wall.search?count=100&owner_id=419790750&query=Аренда&access_token=bb6211359dc3e3eb8f6fe190d36e8a42ad054dd2e146ec845072dcd19377d474caafd648b3712e3254e54&v=5.52";
		RootObject rootObject = restTemplate.getForObject(url, RootObject.class);
		List<Post> usersPostList = rootObject.getPostResponse().getPosts();

		for (Post post : usersPostList) {
			if (post.getAttachmentContainers() != null) {
				List<Photo> photos = new ArrayList<>();
				for (AttachmentContainer container : post.getAttachmentContainers()) {
					if (container.getType().equals("photo") && container.getPhoto().getPhoto604() != null) {

						Photo photo = new Photo();
						photo.setPost(post);
						photo.setReferenceOnPost(container.getPhoto().getPhoto604());

						photos.add(photo);
					}
				}
				post.setPhotos(photos);

				post.setHavePhoto(true);
				post.setFromWhere("user");

				vkPostService.addPost(post);
				//photoService.addPhotos(photos);

			} else {
				vkPostService.addPost(post);
			}
		}
	}
}
