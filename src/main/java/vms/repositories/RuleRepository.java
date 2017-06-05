package vms.repositories;

import org.springframework.data.repository.CrudRepository;
import vms.scheduling.Rule;

public interface RuleRepository extends CrudRepository<Rule,Long> {
	public Rule getRuleByName(String name);
}
