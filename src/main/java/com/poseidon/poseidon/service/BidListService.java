package com.poseidon.poseidon.service;

import com.poseidon.poseidon.domain.BidList;

import java.util.Optional;

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

    Optional<BidList> getBidlistById(int bidListId);

    void deleteBidList(BidList bidList);

    void addBidList(BidList bidList);

    void updateBidList(int existingBidlistId, BidList bidList);
}
