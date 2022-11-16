package com.poseidon.poseidon.service;

import com.poseidon.poseidon.domain.RuleName;
import com.poseidon.poseidon.exception.DataNotDeletedException;
import com.poseidon.poseidon.exception.DataNotFoundException;
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
    public RuleName getRuleNameById(int ruleNameId) {
        logger.debug("Get rule name with id {}", ruleNameId);

        return ruleNameRepository.findById(ruleNameId).orElseThrow(
                () -> new DataNotFoundException("Rule name with id " + ruleNameId + " not found")
        );
    }

    @Override
    public void deleteRuleName(RuleName ruleName) {
        int id = ruleName.getId();
        logger.debug("Delete rule name {}", id);

        ruleNameRepository.delete(ruleName);

        Optional<RuleName> deletedRuleName = ruleNameRepository.findById(id);
        if (deletedRuleName.isPresent())
            throw new DataNotDeletedException("Rule name with id " + id + " has not been deleted");
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
