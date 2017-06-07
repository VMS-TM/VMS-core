package vms.services.absr;

import org.springframework.web.client.RestTemplate;
import vms.models.ProxyServer;
import vms.models.postenvironment.PostResponse;
import vms.models.rawgroup.Group;

import java.util.List;

public interface PostSearchService {

	PostResponse getPostResponseByGroupsList(List<Group> groups, String query);

	PostResponse getPostResponseByGroupName(RestTemplate proxyTemplate, String proxyServer, String nameGroup, String query);
}
