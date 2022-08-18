package com.poseidon.poseidon.service;

import com.poseidon.poseidon.domain.BidList;
import com.poseidon.poseidon.repositories.BidListRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
