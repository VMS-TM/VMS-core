package vms.services.impl.posts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import vms.globalconstants.ConstantsForVkApi;
import vms.models.posts.Post;
import vms.services.absr.property.PropertyService;
import vms.services.impl.property.PropertySearchService;

import java.util.Objects;

@Service
public class PostToGroupService {

	@Autowired
	private PropertySearchService propertySearchService;

	@Autowired
	private PropertyService propertyService;

	private static final Logger logger = LoggerFactory.getLogger(PostToGroupService.class);

	private String getMessage(Post post) {
		StringBuilder stringBuilder = new StringBuilder();

		if (!Objects.equals(post.getHeadling(), "")) {
			stringBuilder.append("Сдается: ").append(post.getHeadling()).append("\n");
		}

		if (!Objects.equals(post.getArea(), "")) {
			stringBuilder.append("Район: ").append(post.getArea()).append("\n");
		}

		if (!Objects.equals(post.getMetroAndAddress(), "")) {
			stringBuilder.append("Адрес: ").append(post.getMetroAndAddress()).append("\n");
		}

		if (!Objects.equals(post.getPriceOfFlat(), "")) {
			stringBuilder.append("Стоимость: ").append(post.getPriceOfFlat()).append("\n");
		}

		if (!Objects.equals(post.getNameOfPerson(), "")) {
			stringBuilder.append("Собственник: ").append(post.getNameOfPerson()).append("\n");
		}

		if (!Objects.equals(post.getPhoneNumber(), "")) {
			stringBuilder.append("Контакты: ").append(post.getPhoneNumber()).append("\n");
		}

		if (!Objects.equals(post.getTextOnView(), "")) {
			stringBuilder.append("Текст: ").append(post.getTextOnView()).append("\n");
		}

		if (!Objects.equals(post.getText(), "")) {
			stringBuilder.append("Доп. Информация: ").append(post.getText()).append("\n");
		}

		return stringBuilder.toString();
	}

	private String getPhoto(Post post) {
		StringBuilder stringBuilder = new StringBuilder();


		post.getPhotos().forEach(photo -> {
			stringBuilder.append("photo")
					.append(photo.getOwnerIdInVk())
					.append("_")
					.append(photo.getPostIdInVk())
					.append(",");
		});


		return stringBuilder.toString();
	}

	public String postToGroup(Integer idGroup, Post post) {

		RestTemplate restTemplate = new RestTemplate();
		String uri = ConstantsForVkApi.URL + ConstantsForVkApi.PARAMETER_POST_TO_GROUP_METHOD;
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

		map.add("owner_id", "-" + idGroup);
		map.add("friends_only", "0");
		map.add("from_group", "1");
		map.add("message", getMessage(post));
		map.add("signed", "0");
		map.add("mark_as_ads", "0");

		if (post.isHavePhoto()) {
			map.add("attachments", getPhoto(post));
		}

		map.add("access_token", propertyService.getPropertyById(1L).getValue());
		map.add("v", "5.65");
		String result = restTemplate.postForObject(uri, map, String.class);

		if (result != null) {
			return result;
		}


		return null;

	}

}