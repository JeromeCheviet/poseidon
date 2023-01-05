package com.poseidon.poseidon.service;

import com.poseidon.poseidon.domain.RuleName;

/**
 * Interface link to RuleName operations
 */
public interface RuleNameService {

    /**
     * Get all Rule Name
     *
     * @return an iterable list contains all rule name.
     */
    Iterable<RuleName> getRuleNames();

    /**
     * Get one rule name by his ID.
     *
     * @param ruleNameId int ID number
     * @return An object which contain the rule name.
     */
    RuleName getRuleNameById(int ruleNameId);

    /**
     * Delete one rule name
     *
     * @param ruleName An object which contain the RuleName to delete
     */
    void deleteRuleName(RuleName ruleName);

    /**
     * Add one rule name
     *
     * @param ruleName An object which contain the rule name to add
     */
    void addRuleName(RuleName ruleName);

    /**
     * Update one rule name
     *
     * @param existingRuleNameId Int ID number of rule name to update
     * @param ruleName           An object which contain RuleName's data to update.
     */
    void updateRuleName(int existingRuleNameId, RuleName ruleName);
}
