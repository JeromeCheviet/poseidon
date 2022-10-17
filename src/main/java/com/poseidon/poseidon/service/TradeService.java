package com.poseidon.poseidon.service;

import com.poseidon.poseidon.domain.Trade;

import java.util.Optional;

public interface TradeService {
    Iterable<Trade> getTradeList();

    Optional<Trade> getTradeById(int tradeId);

    void deleteTrade(Trade trade);

    void addTrade(Trade trade);

    void updateTrade(int existingTradeId, Trade trade);
}
