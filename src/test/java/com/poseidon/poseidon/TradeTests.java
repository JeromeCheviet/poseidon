package com.poseidon.poseidon;

import com.poseidon.poseidon.domain.Trade;
import com.poseidon.poseidon.repositories.TradeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Sql(scripts = "/schema.sql")
class TradeTests {

    @Autowired
    private TradeRepository tradeRepository;

    @Test
    void tradeTest() {
        Trade trade = new Trade();
        trade.setAccount("Trade Account");
        trade.setType("Type");

        // Save
        trade = tradeRepository.save(trade);
        assertNotNull(trade.getTradeId());
        assertTrue(trade.getAccount().equals("Trade Account"));

        // Update
        trade.setAccount("Trade Account Update");
        trade = tradeRepository.save(trade);
        assertTrue(trade.getAccount().equals("Trade Account Update"));

        // Find
        Iterable<Trade> trades = tradeRepository.findAll();
        List<Trade> listResult = new ArrayList<>();
        trades.forEach(listResult::add);
        assertTrue(listResult.size() > 0);

        // Delete
        Integer id = trade.getTradeId();
        tradeRepository.delete(trade);
        Optional<Trade> tradeList = tradeRepository.findById(id);
        assertFalse(tradeList.isPresent());
    }
}
