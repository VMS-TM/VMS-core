package vms.services.absr;

import org.springframework.web.client.RestTemplate;
import vms.models.postenvironment.Post;
import vms.models.rawgroup.Group;

import java.util.List;

public interface PostSearchService {

	void getPostResponseByGroupsList(List<Group> groups, String query);

	List<Post> getPostResponseByGroupName(RestTemplate proxyTemplate, String token, Long id, String query);

	List<Post> getPostResponseByGroupNameWithoutProxy(RestTemplate proxyTemplate, String token, Long id, String query);

	void getPostFromList(List<Post> postsInBD, List<Post> result, String from, String query);
}
