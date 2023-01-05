package com.poseidon.poseidon.repositories;

import com.poseidon.poseidon.domain.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface to build queries runs in table trade.
 */
@Repository
public interface TradeRepository extends CrudRepository<Trade, Integer> {

}
