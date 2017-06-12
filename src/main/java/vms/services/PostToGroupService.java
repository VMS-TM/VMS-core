package vms.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import vms.models.postenvironment.Post;

@Service
public class PostToGroupService {

	@Autowired
	PropertySearchService propertySearchService;

	final String uri = "https://api.vk.com/method";

	private String doPost(Integer idOfGroup, String text, String proxyServer) {
		StringBuilder sb = new StringBuilder(uri);
		sb.append("/wall.post?owner_id=-")
			.append(idOfGroup)
			.append("&friends_only=1")
			.append("&from_group=0")
			.append("&message=")
			.append(text)
			.append("&signed=0")
			.append("&mark_as_ads=0")
			.append("&access_token=")
			.append(proxyServer)
			.append("&v=5.65");
		return sb.toString();
	}

	public String postToGroup(Integer idGroup, Post post) {

		RestTemplate restTemplate = new RestTemplate();

		String result = restTemplate.getForObject(doPost(idGroup, post.getText(), propertySearchService.getValue("defaultKey")), String.class);

		if (result != null) {
			return result;
		}

		return null;

	}

}