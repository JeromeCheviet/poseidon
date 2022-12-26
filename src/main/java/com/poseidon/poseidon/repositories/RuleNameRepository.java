package com.poseidon.poseidon.repositories;

import com.poseidon.poseidon.domain.RuleName;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface to build queries runs in table rulename.
 */
@Repository
public interface RuleNameRepository extends CrudRepository<RuleName, Integer> {

}
