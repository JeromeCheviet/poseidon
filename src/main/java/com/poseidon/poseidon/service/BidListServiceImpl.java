package com.poseidon.poseidon.service;

import com.poseidon.poseidon.domain.BidList;
import com.poseidon.poseidon.exception.DataNotDeletedException;
import com.poseidon.poseidon.exception.DataNotFoundException;
import com.poseidon.poseidon.repositories.BidListRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Class to interact with BidList table data
 */
@Service
public class BidListServiceImpl implements BidListService {

    private static final Logger logger = LogManager.getLogger(BidListServiceImpl.class);

    @Autowired
    private BidListRepository bidListRepository;


    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<BidList> getBidLists() {
        logger.debug("Get all bid list.");

        return bidListRepository.findAll();
    }

    /**
     * {@inheritDoc}
     *
     * <br>If no Bid is found, a private exception is throw.
     */
    @Override
    public BidList getBidlistById(int bidListId) {
        logger.debug("Get bidlist with id : {}", bidListId);

        return bidListRepository.findById(bidListId).orElseThrow(
                () -> new DataNotFoundException("Bidlist with id " + bidListId + " not found")
        );
    }

    /**
     * {@inheritDoc}
     *
     * <br>Before deleting, the method save the Bid ID in a variable. After deleted the Bid, the method
     * search if a Bid with this ID exist. If Bid is present, a private exception is throw.
     */
    @Override
    public void deleteBidList(BidList bidList) {
        int id = bidList.getBidListId();
        logger.debug("Delete bidlist {}", id);

        bidListRepository.delete(bidList);

        Optional<BidList> deletedBidlist = bidListRepository.findById(id);
        if (deletedBidlist.isPresent())
            throw new DataNotDeletedException("Bidlist with id " + id + " has not been deleted");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addBidList(BidList bidList) {
        logger.debug("Add new bidlist : {}", bidList.getBidListId());

        bidListRepository.save(bidList);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateBidList(int existingBidlistId, BidList bidList) {
        logger.debug("Updating bidlist with id {} and account name {}", existingBidlistId, bidList.getAccount());
        bidList.setBidListId(existingBidlistId);
        bidListRepository.save(bidList);
    }
}
