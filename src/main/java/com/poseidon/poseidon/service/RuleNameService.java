package com.poseidon.poseidon.service;

import com.poseidon.poseidon.domain.RuleName;

public interface RuleNameService {

    Iterable<RuleName> getRuleNames();

    RuleName getRuleNameById(int ruleNameId);

    void deleteRuleName(RuleName ruleName);

    void addRuleName(RuleName ruleName);

    void updateRuleName(int existingRuleNameId, RuleName ruleName);
}
