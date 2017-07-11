package vms.services.impl;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import vms.configs.security.SecurityConfig;
import vms.globalVariables.ConstantsForVkApi;
import vms.models.rawgroup.Group;
import vms.models.rawgroup.RootObject;
import vms.services.absr.PropertyService;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
public class GroupsSearchService {

	@Autowired
	private PropertyService propertyService;

	private static final Logger logger = LoggerFactory.getLogger(GroupsSearchService.class);

	final String path = ConstantsForVkApi.URL + ConstantsForVkApi.PARAMETER_GROUP_SEARCH_METHOD;

	public List<Group> getGroupsByGroupName(String first, String second, long count) {

		RestTemplate restTemplate = new RestTemplate();
		List<Group> items = new ArrayList<Group>();

		for (String query : parseQuery(first, second)) {
			String result = restTemplate.getForObject(getUri(path, query), String.class);

			ObjectMapper objectMapper = new ObjectMapper();
			RootObject rootObject = null;

			try {
				rootObject = objectMapper.readValue(result, RootObject.class);
			} catch (IOException e) {
				e.printStackTrace();
			}


			for (Group r : rootObject.getGroup()) {
				if (r.getMembers_count() >= count && r.getIsClosed() == 0) {
					items.add(r);
				}
			}
		}
		return items;


	}

	private URI getUri(String path, String query) {
		StringBuilder sb = new StringBuilder();
		sb.append(ConstantsForVkApi.URL)
				.append(ConstantsForVkApi.PARAMETER_GROUP_SEARCH_EXE)
				.append(query)
				.append(ConstantsForVkApi.PARAMETER_GROUP_SEARCH_QUERY_ONE)
				.append(query)
				.append(ConstantsForVkApi.PARAMETER_GROUP_SEARCH_QUERY_TWO);


		URI uri = null;
		try {
			uri = new URI(sb.toString().replaceAll(" ", "+"));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}


		return uri;
	}

	private List<String> parseQuery(String first, String second) {

		List<String> resultQuery = new ArrayList<String>();
		String[] firstSplit = first.split("[,;:.!?\\s]+");
		String[] secondSplit = second.split("[,;:.!?\\s]+");

		Collections.addAll(resultQuery, firstSplit);

		for (String query1 : firstSplit) {
			for (String query2 : secondSplit) {
				resultQuery.add(query1 + " " + query2);
			}
		}

		return resultQuery;
	}

	private String getUriForValidate(String query) {
		StringBuilder sb = new StringBuilder();
		sb.append(ConstantsForVkApi.URL)
				.append(ConstantsForVkApi.PARAMETER_GROUP_GET_ID)
				.append(query)
				.append(ConstantsForVkApi.PARAMETER_GROUP_GET_ID_QUERY)
				.append(ConstantsForVkApi.TOKEN);

		if (propertyService.getPropertyById(1L) != null) {
			sb.append(propertyService.getPropertyById(1L).getValue());
		}


		return sb.toString();
	}

	public Group validate(String query) {

		RestTemplate restTemplate = new RestTemplate();
		RootObject result = restTemplate.getForObject(getUriForValidate(query), RootObject.class);

		if (result.getGroup() != null) {
			return result.getGroup().get(0);
		}

		return null;
	}

}
