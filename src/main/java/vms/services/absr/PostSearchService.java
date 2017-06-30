package vms.services.absr;

import org.springframework.web.client.RestTemplate;
import vms.models.postenvironment.Post;
import vms.models.rawgroup.Group;

import java.util.List;

public interface PostSearchService {

	void getPostResponseByGroupsList(List<Group> groups, String query);

	List<Post> getPostResponseByGroupName(RestTemplate proxyTemplate, String proxyServer, String nameGroup, String query);

	List<Post> getPostResponseByGroupNameWithoutProxy(RestTemplate proxyTemplate, String token, String nameGroup, String query);

	void getPostFromList(List<Post> postsInBD, List<Post> result, String from, String query);
}
