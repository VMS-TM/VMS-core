package vms.services.absr;

import vms.models.postenvironment.PostResponse;
import vms.models.rawgroup.Group;

public interface PostSearchService {

	PostResponse getPostResponseByGroupsList(Iterable<Group> groups, String query);

	PostResponse getPostResponseByGroupName(String nameGroup, String query);
}
