package vms.services;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import vms.models.rawgroup.Group;
import vms.models.rawgroup.RootObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;


@Service
public class GroupsSearchService {

	//константы
	final String ACCESS_TOKEN = "&access_token=f0874a39a169ec6e0b35749c71cdcecc7da034205785e5d622c173454ff95b4532cbf6bf20bf924f365e4";
	final String path = "https://api.vk.com/method/execute";
	final String version = "&v=5.64";

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
				if (r.getMembersCount() >= count && r.getIsClosed()==0  ) {
					items.add(r);
				}
			}
		}
		return items;


	}

	private URI getUri(String path, String query) {
		StringBuilder sb = new StringBuilder();
		sb.append("https://api.vk.com/method/execute?code=var%20hyi1=API.groups.search(%7B%22q%22:%20%22");
		sb.append(query);
		sb.append("%22,%22offset%22:0,%20%22count%22:%20500%7D);%0Aif(hyi1.items.length%3E0)%0A%7B%0Avar%20nya1=API.groups.getById(%7B%22group_ids%22:%20hyi1.items@.id,%20%22fields%22:%22members_count%22%7D);%0Avar%20hyi2%20=%20API.groups.search(%7B%22q%22:%20%22");
		sb.append(query);
		sb.append("%22,%20%22offset%22:500,%22count%22:%20500%7D);%0Aif(hyi2.items.length%20%3E0%20)%0A%7B%0Avar%20nya2%20=%20API.groups.getById(%7B%22group_ids%22:%20hyi2.items@.id,%20%22fields%22:%22members_count%22%7D);return+nya1+%2B+nya2%3B%7D%0Areturn%20nya1;%0A%7D%0Areturn%200;&v=5.64&access_token=f0874a39a169ec6e0b35749c71cdcecc7da034205785e5d622c173454ff95b4532cbf6bf20bf924f365e4");


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

		for (String query1 : firstSplit) {
			resultQuery.add(query1);
		}

		for (String query1 : firstSplit) {
			for (String query2 : secondSplit) {
				resultQuery.add(query1 + " " + query2);
			}
		}

		return resultQuery;
	}

	private URI getUriForValidate(String query) {
		StringBuilder sb = new StringBuilder();
		sb.append("https://api.vk.com/method/groups.getById?group_id=");
		sb.append(query);
		sb.append("&fields=members_count&v=5.64&access_token=f0874a39a169ec6e0b35749c71cdcecc7da034205785e5d622c173454ff95b4532cbf6bf20bf924f365e4");

		URI uri = null;
		try {
			uri = new URI(sb.toString());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		return uri;
	}

	public Group validate(String query) {

		Group group = null;
		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.getForObject(getUriForValidate(query), String.class);

		if (result.contains("\"error_code\":100") || result.contains("\"deactivated\":\"deleted\"") || result.contains("\"members_count\":0")) {
			return group;
		} else {
			ObjectMapper objectMapper = new ObjectMapper();
			RootObject rootObject = null;

			try {
				rootObject = objectMapper.readValue(result, RootObject.class);
			} catch (IOException e) {
				e.printStackTrace();
			}

			return group = rootObject.getGroup().get(0);
		}

	}

}