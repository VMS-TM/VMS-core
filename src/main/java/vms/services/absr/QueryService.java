package vms.services.absr;


import vms.models.postenvironment.Post;
import vms.models.postenvironment.Query;

import java.util.List;

public interface QueryService {

	void addQuery(Query query);

	void addQuery(List<Query> queries);

	void deleteAllQuery(List<Query> queries);

	List<Query> getAllQueryFromDb();

	void update(Query query);

	void delete(Query query);

	void deleteQuery(Long id);

	Query getQuery(String query, String from);

	Query getById(Long id);

}
