package com.poseidon.poseidon.service;

import com.poseidon.poseidon.domain.Trade;

public interface TradeService {
    Iterable<Trade> getTradeList();

    Trade getTradeById(int tradeId);

    void deleteTrade(Trade trade);

    void addTrade(Trade trade);

    void updateTrade(int existingTradeId, Trade trade);
}
