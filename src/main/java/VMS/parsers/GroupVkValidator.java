package VMS.parsers;

import VMS.model.Group;
import VMS.model.postenv.Post;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class GroupVkValidator {
	//constants
	final String ACCESS_TOKEN = "f0874a39a169ec6e0b35749c71cdcecc7da034205785e5d622c173454ff95b4532cbf6bf20bf924f365e4";
	final String uri = "https://api.vk.com/method";
	final String version = "&v=5.63";

	public boolean ifGroupExist(Group group){
		boolean result = false;
		//first - check existing by name
		if (!(group.getName().equals(""))){
			result = ifGroupExistCheckById(group.getName());
		//second check existing by vk_id
		} else if (group.getId_vk()!=null){
			result = ifGroupExistCheckById(group.getId_vk().toString());
		}
		return result;
	}

	public boolean ifGroupExistCheckById(String nameGroup) {

		Boolean groupExist = true;
		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.getForObject(getUriSearchGroup(nameGroup), String.class);

		Pattern pt = Pattern.compile("error");
		Matcher mt = pt.matcher(result);

		if (mt.find()){
				groupExist = false;
		}
		return groupExist;
	}

	private String getUriSearchGroup(String name){
		StringBuilder sb =  new StringBuilder(uri);
		sb.append("/groups.getById");
		sb.append("?");
		sb.append("group_ids=");
		sb.append(name);
		sb.append("&v=5.63");
		sb.append("&count=1");
		sb.append("&access_token=");
		sb.append(ACCESS_TOKEN);
		return sb.toString();
	}
}
