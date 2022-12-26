package com.poseidon.poseidon.service;

import com.poseidon.poseidon.domain.Trade;
import com.poseidon.poseidon.exception.DataNotDeletedException;
import com.poseidon.poseidon.exception.DataNotFoundException;
import com.poseidon.poseidon.repositories.TradeRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Class to interact with Trade table data
 */
@Service
public class TradeServiceImpl implements TradeService {
    private static final Logger logger = LogManager.getLogger(TradeServiceImpl.class);

    @Autowired
    private TradeRepository tradeRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<Trade> getTradeList() {
        logger.debug("Get all trades");

        return tradeRepository.findAll();
    }

    /**
     * {@inheritDoc}
     *
     * <br>If no Trade is found, a private exception is throw.
     */
    @Override
    public Trade getTradeById(int tradeId) {
        logger.debug("Get trade with id : {}", tradeId);

        return tradeRepository.findById(tradeId).orElseThrow(
                () -> new DataNotFoundException("Trade with id " + tradeId + " not found")
        );
    }

    /**
     * {@inheritDoc}
     *
     * <br>Before deleting, the method save the Trade ID in a variable. After deleted the Trade, the method
     * search if a Trade with this ID exist. If Trade is present, a private exception is throw.
     */
    @Override
    public void deleteTrade(Trade trade) {
        int id = trade.getTradeId();
        logger.debug("Delete trade {}", id);

        tradeRepository.delete(trade);

        Optional<Trade> deletedTrade = tradeRepository.findById(id);
        if (deletedTrade.isPresent())
            throw new DataNotDeletedException("Trade with id " + id + " has not been deleted");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addTrade(Trade trade) {
        logger.debug("Add new trade : {}", trade.getTradeId());

        tradeRepository.save(trade);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateTrade(int existingTradeId, Trade trade) {
        logger.debug("Update trade with id {}", existingTradeId);
        trade.setTradeId(existingTradeId);

        tradeRepository.save(trade);
    }

}
