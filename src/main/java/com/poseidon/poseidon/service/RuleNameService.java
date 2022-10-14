package com.poseidon.poseidon.service;

import com.poseidon.poseidon.domain.RuleName;

import java.util.Optional;

public interface RuleNameService {

    Iterable<RuleName> getRuleNames();

    Optional<RuleName> getRuleNameById(int ruleNameId);

    void deleteRuleName(RuleName ruleName);

    void addRuleName(RuleName ruleName);

    void updateRuleName(int existingRuleNameId, RuleName ruleName);
}
