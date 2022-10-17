package com.poseidon.poseidon.service;

import com.poseidon.poseidon.domain.Trade;
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
    public Optional<Trade> getTradeById(int tradeId) {
        logger.debug("Get trade with id : {}", tradeId);

        return tradeRepository.findById(tradeId);
    }

    @Override
    public void deleteTrade(Trade trade) {
        logger.debug("Delete trade {}", trade);

        tradeRepository.delete(trade);
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
