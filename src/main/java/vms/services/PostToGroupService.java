package vms.services;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import vms.globalVariables.ConstantsForVkApi;
import vms.models.postenvironment.Post;
import vms.models.rawgroup.RootObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Service
public class PostToGroupService {

	@Autowired
	PropertySearchService propertySearchService;

	final String uri = "https://api.vk.com/method";

	public String postToGroup(Integer idGroup, Post post) {

		RestTemplate restTemplate = new RestTemplate();
		String uri = "https://api.vk.com/method/wall.post";
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

		map.add("owner_id", "-" + idGroup);
		map.add("friends_only", "0");
		map.add("from_group", "1");
		map.add("message", post.getText());
		map.add("signed", "0");
		map.add("mark_as_ads", "0");
		map.add("access_token", propertySearchService.getValue("defaultKey"));
		map.add("v", "5.65");


		String result = restTemplate.postForObject(uri, map, String.class);

		if (result != null) {
			return result;
		}

		return null;

	}

}