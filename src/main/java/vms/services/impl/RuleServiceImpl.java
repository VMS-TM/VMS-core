package vms.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vms.repositories.RuleRepository;
import vms.scheduling.Rule;
import vms.services.absr.RuleService;

import java.util.List;

@Service
public class RuleServiceImpl implements RuleService {

	@Autowired
	RuleRepository ruleRepository;

	@Override
	public Rule RuleByName(String name) {
		return ruleRepository.getRuleByName(name);
	}

	@Override
	public List<Rule> getAllRules() {
		return (List<Rule>) ruleRepository.findAll();
	}

	@Override
	public void addRule(Rule rule) {
		ruleRepository.save(rule);
	}

	@Override
	public void deleteRule(Rule rule) {
		ruleRepository.delete(rule);
	}

	@Override
	public void updateRule(Rule rule) {
		ruleRepository.save(rule);
	}

	@Override
	public Rule getRuleById(Long id) {
		return ruleRepository.findOne(id);
	}
}
