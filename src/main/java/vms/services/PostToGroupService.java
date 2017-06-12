package vms.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import vms.models.postenvironment.Post;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostToGroupService {

	@Autowired
	PropertySearchService propertySearchService;

	final String uri = "https://api.vk.com/method";

	private String getURI(Post post) {
		StringBuilder stringBuilder = new StringBuilder();

		if (post.getHeadling() != null) {
			stringBuilder.append("Сдается: " + post.getHeadling() + "\n");
		}

		if (post.getArea() != null) {
			stringBuilder.append("Район: " + post.getArea() + "\n");
		}

		if (post.getMetroAndAddress() != null) {
			stringBuilder.append("Адрес: " + post.getMetroAndAddress() + "\n");
		}

		if (post.getPriceOfFlat() != null) {
			stringBuilder.append("Стоимость: " + post.getPriceOfFlat() + "\n");
		}

		if (post.getNameOfPerson() != null) {
			stringBuilder.append("Собственник: " + post.getNameOfPerson() + "\n");
		}

		if (post.getPhoneNumber() != null) {
			stringBuilder.append("Контакты: " + post.getPhoneNumber() + "\n");
		}

		if (post.getTextOnView() != null) {
			stringBuilder.append("Текст: " + post.getTextOnView() + "\n");
		}

		if (post.getText() != null) {
			stringBuilder.append("Доп. Информация: " + post.getText() + "\n");
		}

		return stringBuilder.toString();
	}

	public String postToGroup(Integer idGroup, Post post) {

		RestTemplate restTemplate = new RestTemplate();
		String uri = "https://api.vk.com/method/wall.post";
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		
		map.add("owner_id", "-" + idGroup);
		map.add("friends_only", "0");
		map.add("from_group", "1");
		map.add("message", getURI(post));
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