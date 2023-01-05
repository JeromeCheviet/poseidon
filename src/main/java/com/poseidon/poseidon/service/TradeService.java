package com.poseidon.poseidon.service;

import com.poseidon.poseidon.domain.Trade;

/**
 * Interface link to Trade operations
 */
public interface TradeService {

    /**
     * Get all Trade
     *
     * @return an iterable list contains all Trade.
     */
    Iterable<Trade> getTradeList();

    /**
     * Get one Trade by his ID.
     *
     * @param tradeId int ID number
     * @return An object which contain the Trade.
     */
    Trade getTradeById(int tradeId);

    /**
     * Delete one Trade
     *
     * @param trade An object which contain the Trade to delete
     */
    void deleteTrade(Trade trade);

    /**
     * Add one Traade
     *
     * @param trade An object which contain the Trade to add
     */
    void addTrade(Trade trade);

    /**
     * Update one Trade
     *
     * @param existingTradeId Int ID number of Trade to update
     * @param trade           An object which contain Trade's data to update.
     */
    void updateTrade(int existingTradeId, Trade trade);
}
