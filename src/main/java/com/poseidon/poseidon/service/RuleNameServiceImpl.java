package com.poseidon.poseidon.service;

import com.poseidon.poseidon.domain.RuleName;
import com.poseidon.poseidon.repositories.RuleNameRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RuleNameServiceImpl implements RuleNameService {
    private final static Logger logger = LogManager.getLogger(RuleNameServiceImpl.class);

    @Autowired
    private RuleNameRepository ruleNameRepository;

    @Override
    public Iterable<RuleName> getRuleNames() {
        logger.debug("Get all rule names");

        return ruleNameRepository.findAll();
    }

    @Override
    public Optional<RuleName> getRuleNameById(int ruleNameId) {
        logger.debug("Get rule name with id {}", ruleNameId);

        return ruleNameRepository.findById(ruleNameId);
    }

    @Override
    public void deleteRuleName(RuleName ruleName) {
        logger.debug("Delete rule name {}", ruleName.getId());

        ruleNameRepository.delete(ruleName);
    }

    @Override
    public void addRuleName(RuleName ruleName) {
        logger.debug("Add new rule name : {}", ruleName.getName());

        ruleNameRepository.save(ruleName);
    }

    @Override
    public void updateRuleName(int existingRuleNameId, RuleName ruleName) {
        logger.debug("Updating rule name with id {} and name {}", ruleName.getId(), ruleName.getName());
        ruleName.setId(existingRuleNameId);

        ruleNameRepository.save(ruleName);
    }
}
