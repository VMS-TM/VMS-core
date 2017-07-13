package vms.services.absr.posts;


import vms.models.posts.Query;

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

	List<Query> getAllQuery(String query, String from);

	Query getById(Long id);

	List<Query> findAllByFrom(String from);

}
