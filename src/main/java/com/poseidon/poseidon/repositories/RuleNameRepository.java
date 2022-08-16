package com.poseidon.poseidon.repositories;

import com.poseidon.poseidon.domain.RuleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RuleNameRepository extends CrudRepository<RuleName, Integer> {

}
