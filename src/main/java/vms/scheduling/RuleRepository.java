package vms.scheduling;

import org.springframework.data.repository.CrudRepository;
import vms.scheduling.Rule;

public interface RuleRepository extends CrudRepository<Rule,Long> {
	Rule getRuleByName(String name);
}
