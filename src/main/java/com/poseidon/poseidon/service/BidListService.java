package com.poseidon.poseidon.service;

import com.poseidon.poseidon.domain.BidList;

/**
 * Interface link to BidList operations
 */
public interface BidListService {
    /**
     * Get all BidList
     *
     * @return an iterable list contains all BidList.
     */
    Iterable<BidList> getBidLists();
}
