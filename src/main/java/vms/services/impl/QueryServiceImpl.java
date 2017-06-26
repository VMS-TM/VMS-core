package vms.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vms.models.postenvironment.Query;
import vms.repositories.QueryRepository;
import vms.services.absr.QueryService;

@Service
public class QueryServiceImpl implements QueryService {

	@Autowired
 	private QueryRepository queryRepository;

	@Override
	public void addQuery(Query query) {
		queryRepository.save(query);
	}
}
