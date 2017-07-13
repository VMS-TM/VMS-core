package vms.services.impl.posts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vms.models.posts.Query;
import vms.repositories.posts.QueryRepository;
import vms.services.absr.posts.QueryService;

import java.util.List;

@Service
public class QueryServiceImpl implements QueryService {

	@Autowired
	private QueryRepository queryRepository;

	@Override
	public void addQuery(List<Query> queries) {
		queryRepository.save(queries);
	}

	@Override
	public List<Query> getAllQueryFromDb() {
		return (List<Query>) queryRepository.findAll();
	}

	@Override
	public void update(Query query) {
		queryRepository.save(query);
	}

	@Override
	public void delete(Query query) {
		queryRepository.delete(query);
	}

	@Override
	public void deleteQuery(Long id) {
		queryRepository.delete(id);
	}

	@Override
	public void addQuery(Query query) {
		queryRepository.save(query);
	}

	@Override
	public void deleteAllQuery(List<Query> queries) {
		queryRepository.delete(queries);
	}

	@Override
	public Query getQuery(String query, String from) {
		return queryRepository.getQueryByWordAndFromwhere(query, from);
	}

	@Override
	public List<Query> getAllQuery(String query, String from) {
		return queryRepository.findAllByWordAndFromwhere(query, from);
	}

	@Override
	public Query getById(Long id) {
		return queryRepository.getQueryById(id);
	}

	@Override
	public List<Query> findAllByFrom(String from) {
		return queryRepository.findAllByFromwhere(from);
	}
}
