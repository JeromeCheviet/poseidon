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

    /**
     * Get one Bid by his ID.
     *
     * @param bidListId int ID number
     * @return An object which contain the Bid.
     */
    BidList getBidlistById(int bidListId);

    /**
     * Delete one Bid
     *
     * @param bidList An object which contain the Bid to delete
     */
    void deleteBidList(BidList bidList);

    /**
     * Add one Bid
     *
     * @param bidList An object which contain the Bid to add
     */
    void addBidList(BidList bidList);

    /**
     * Update one Bid
     *
     * @param existingBidlistId Int ID number of Bid to update
     * @param bidList           An object which contain Bid's data to update.
     */
    void updateBidList(int existingBidlistId, BidList bidList);
}
