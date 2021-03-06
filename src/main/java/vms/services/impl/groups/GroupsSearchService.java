package vms.services.impl.groups;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import vms.globalconstants.ConstantsForVkApi;
import vms.models.groups.Group;
import vms.models.groups.RootObject;
import vms.services.absr.property.PropertyService;

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

	public List<Group> getGroupsByGroupName(String first, String second, long count) throws IOException {

		RestTemplate restTemplate = new RestTemplate();
		List<Group> items = new ArrayList<>();

		String path = ConstantsForVkApi.URL + ConstantsForVkApi.PARAMETER_GROUPS_SEARCH_METHOD;
		String result = restTemplate.getForObject(getUri(first), String.class);

		ObjectMapper objectMapper = new ObjectMapper();

		RootObject rootObject = objectMapper.readValue(result, RootObject.class);

		if (rootObject != null) {
			for (Group r : rootObject.getGroup()) {
				if (r.getMembers_count() >= count && r.getIsClosed() == 0) {
					items.add(r);
				}
			}
		}


		return items;


	}

	private URI getUri(String query){
		StringBuilder sb =  new StringBuilder();
		sb.append(ConstantsForVkApi.PARAMETER_GROUPS_METHOD_EXECUTE)
				.append(query.replaceAll("\\p{P}",""))
				.append(ConstantsForVkApi.PARAMETER_GROUP_SEARCH_QUERY_ONE)
				.append(query.replaceAll("\\p{P}",""))
				.append(ConstantsForVkApi.PARAMETER_GROUP_SEARCH_QUERY_TWO);


		URI uri = null;
		try {
			uri = new URI(sb.toString().replaceAll(" ","+"));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}


		return uri;
	}


	private String getUriTwo(String path, String query) {
		StringBuilder sb = new StringBuilder();
		sb.append(path)
				.append(ConstantsForVkApi.PARAMETER_GROUPS_QUERY)
				.append(query)
				.append(ConstantsForVkApi.PARAMETER_GROUPS_COUNT)
				.append(ConstantsForVkApi.TOKEN);

		if (propertyService.getPropertyById(1L) != null) {
			sb.append(propertyService.getPropertyById(1L).getValue());
		}

		return sb.toString();
	}

	private List<String> parseQuery(String first, String second) {

		List<String> resultQuery = new ArrayList<>();
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

	public Group validate(String query) throws IOException {

		RestTemplate restTemplate = new RestTemplate();
		RootObject rootObject = null;
		ObjectMapper objectMapper = new ObjectMapper();
		String result = restTemplate.getForObject(getUriForValidate(query), String.class);

		rootObject = objectMapper.readValue(result, RootObject.class);

		if (rootObject.getError() != null) {
			logger.debug(rootObject.getError().getErrorMsg());
			return null;
		}

		if (rootObject.getGroup() != null) {
			return rootObject.getGroup().get(0);
		}

		return null;
	}

}
