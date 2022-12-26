package com.poseidon.poseidon.repositories;

import com.poseidon.poseidon.domain.BidList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface to build queries runs in table bidList.
 */
@Repository
public interface BidListRepository extends JpaRepository<BidList, Integer> {

}
