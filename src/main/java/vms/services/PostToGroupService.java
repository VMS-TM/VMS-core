package vms.services;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import vms.models.postenvironment.Post;
import vms.models.rawgroup.Group;
import vms.models.rawgroup.RootObject;

import java.io.IOException;

@Service
public class PostToGroupService {

	@Autowired
	private  PropertySearchService propertySearchService;

	final String uri = "https://api.vk.com/method";

	private String doPost(Integer idOfGroup, String text, String proxyServer) {
		StringBuilder sb = new StringBuilder(uri);
		sb.append("/wall.post?owner_id=");
		sb.append(idOfGroup);
		sb.append("&message=");
		sb.append(text);
		sb.append("access_token=");
		sb.append(proxyServer);
		return sb.toString();
	}

	public void postToGroup(Integer idGroup, Post post) {

		String token = propertySearchService.getValue("defaultKey");

		doPost(idGroup, post.getText(), token);

	}

}