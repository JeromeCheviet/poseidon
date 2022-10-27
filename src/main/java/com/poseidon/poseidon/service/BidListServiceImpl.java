package com.poseidon.poseidon.service;

import com.poseidon.poseidon.domain.BidList;
import com.poseidon.poseidon.exception.BidListNotDeletedException;
import com.poseidon.poseidon.exception.BidListNotFoundException;
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


    @Override
    public Iterable<BidList> getBidLists() {
        logger.debug("Get all bid list.");

        return bidListRepository.findAll();
    }

    @Override
    public BidList getBidlistById(int bidListId) {
        logger.debug("Get bidlist with id : {}", bidListId);

        return bidListRepository.findById(bidListId).orElseThrow(
                () -> new BidListNotFoundException("Bidlist with id " + bidListId + " not found")
        );
    }

    @Override
    public void deleteBidList(BidList bidList) {
        int id = bidList.getBidListId();
        logger.debug("Delete bidlist {}", id);

        bidListRepository.delete(bidList);

        Optional<BidList> deletedBidlist = bidListRepository.findById(id);
        if (deletedBidlist.isPresent())
            throw new BidListNotDeletedException("Bidlist with id " + id + " has not been deleted");
    }

    @Override
    public void addBidList(BidList bidList) {
        logger.debug("Add new bidlist : {}", bidList.getBidListId());

        bidListRepository.save(bidList);
    }

    @Override
    public void updateBidList(int existingBidlistId, BidList bidList) {
        logger.debug("Updating bidlist with id {} and account name {}", existingBidlistId, bidList.getAccount());
        bidList.setBidListId(existingBidlistId);
        bidListRepository.save(bidList);
    }
}
