package com.poseidon.poseidon.service;

import com.poseidon.poseidon.domain.Trade;
import com.poseidon.poseidon.exception.TradeNotDeletedException;
import com.poseidon.poseidon.exception.TradeNotFoundException;
import com.poseidon.poseidon.repositories.TradeRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TradeServiceImpl implements TradeService {
    private static final Logger logger = LogManager.getLogger(TradeServiceImpl.class);

    @Autowired
    private TradeRepository tradeRepository;

    @Override
    public Iterable<Trade> getTradeList() {
        logger.debug("Get all trades");

        return tradeRepository.findAll();
    }

    @Override
    public Trade getTradeById(int tradeId) {
        logger.debug("Get trade with id : {}", tradeId);

        return tradeRepository.findById(tradeId).orElseThrow(
                () -> new TradeNotFoundException("Trade with id " + tradeId + " not found")
        );
    }

    @Override
    public void deleteTrade(Trade trade) {
        int id = trade.getTradeId();
        logger.debug("Delete trade {}", id);

        tradeRepository.delete(trade);

        Optional<Trade> deletedTrade = tradeRepository.findById(id);
        if (deletedTrade.isPresent())
            throw new TradeNotDeletedException("Trade with id " + id + " has not been deleted");
    }

    @Override
    public void addTrade(Trade trade) {
        logger.debug("Add new trade : {}", trade.getTradeId());

        tradeRepository.save(trade);
    }

    @Override
    public void updateTrade(int existingTradeId, Trade trade) {
        logger.debug("Update trade with id {}", existingTradeId);
        trade.setTradeId(existingTradeId);

        tradeRepository.save(trade);
    }

}
