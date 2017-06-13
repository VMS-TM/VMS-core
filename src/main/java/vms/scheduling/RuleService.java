package vms.scheduling;

import vms.scheduling.Rule;

import java.util.List;

public interface RuleService {

	Rule RuleByName(String name);

	List<Rule> getAllRules();

	void addRule(Rule rule);

	void deleteRule(Rule rule);

	void updateRule(Rule rule);

	Rule getRuleById(Long id);
}
