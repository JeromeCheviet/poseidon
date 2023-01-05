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

/**
 * Class to interact with RuleName table data
 */
@Service
public class RuleNameServiceImpl implements RuleNameService {
    private final static Logger logger = LogManager.getLogger(RuleNameServiceImpl.class);

    @Autowired
    private RuleNameRepository ruleNameRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<RuleName> getRuleNames() {
        logger.debug("Get all rule names");

        return ruleNameRepository.findAll();
    }

    /**
     * {@inheritDoc}
     *
     * <br>If no Rule Name is found, a private exception is throw.
     */
    @Override
    public RuleName getRuleNameById(int ruleNameId) {
        logger.debug("Get rule name with id {}", ruleNameId);

        return ruleNameRepository.findById(ruleNameId).orElseThrow(
                () -> new DataNotFoundException("Rule name with id " + ruleNameId + " not found")
        );
    }

    /**
     * {@inheritDoc}
     *
     * <br>Before deleting, the method save the Rule Name ID in a variable. After deleted the Rule, the method
     * search if a Raule name with this ID exist. If rule name is present, a private exception is throw.
     */
    @Override
    public void deleteRuleName(RuleName ruleName) {
        int id = ruleName.getId();
        logger.debug("Delete rule name {}", id);

        ruleNameRepository.delete(ruleName);

        Optional<RuleName> deletedRuleName = ruleNameRepository.findById(id);
        if (deletedRuleName.isPresent())
            throw new DataNotDeletedException("Rule name with id " + id + " has not been deleted");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addRuleName(RuleName ruleName) {
        logger.debug("Add new rule name : {}", ruleName.getName());

        ruleNameRepository.save(ruleName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateRuleName(int existingRuleNameId, RuleName ruleName) {
        logger.debug("Updating rule name with id {} and name {}", ruleName.getId(), ruleName.getName());
        ruleName.setId(existingRuleNameId);

        ruleNameRepository.save(ruleName);
    }
}
