package com.poseidon.poseidon.service;

import com.poseidon.poseidon.domain.Trade;
import com.poseidon.poseidon.exception.TradeNotDeletedException;
import com.poseidon.poseidon.exception.TradeNotFoundException;
import com.poseidon.poseidon.repositories.TradeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TradeServiceTest {

    @InjectMocks
    private TradeService tradeService = new TradeServiceImpl();

    @Mock
    private TradeRepository tradeRepository;

    @Mock
    private Trade expectedTrade;

    @BeforeEach
    private void setUp() {
        int expectedTradeID = 1;
        String expectedAccount = "account";
        String expectedType = "type";
        Double expectedBuyQuantity = 10.0;
        Double expectedSellQuantity = 2.0;
        Double expectedBuyPrice = 3.4;
        Double expectedSellPrice = 4.1;
        LocalDate expectTradeDate = LocalDate.now();
        String expectedSecurity = "security";
        String expectedStatus = "status";
        String expectedTrader = "trader";
        String expectedBenchmark = "benchmark";
        String expectedBook = "book";
        String expectedCreationName ="name";
        LocalDate expectedCreationDate = LocalDate.now().minusMonths(6);
        String expectedRevisionName = "revision";
        LocalDate expectedRevisionDate = LocalDate.now().minusMonths(2);
        String expectedDealName = "deal";
        String expectedDealType = "deal type";
        String expectedSourceListId = "source list";
        String expectedSide = "side";

        expectedTrade = new Trade();
        expectedTrade.setTradeId(expectedTradeID);
        expectedTrade.setAccount(expectedAccount);
        expectedTrade.setType(expectedType);
        expectedTrade.setBuyQuantity(expectedBuyQuantity);
        expectedTrade.setSellQuantity(expectedSellQuantity);
        expectedTrade.setBuyPrice(expectedBuyPrice);
        expectedTrade.setSellPrice(expectedSellPrice);
        expectedTrade.setTradeDate(expectTradeDate);
        expectedTrade.setSecurity(expectedSecurity);
        expectedTrade.setStatus(expectedStatus);
        expectedTrade.setTrader(expectedTrader);
        expectedTrade.setBenchmark(expectedBenchmark);
        expectedTrade.setBook(expectedBook);
        expectedTrade.setCreationName(expectedCreationName);
        expectedTrade.setCreationDate(expectedCreationDate);
        expectedTrade.setRevisionName(expectedRevisionName);
        expectedTrade.setRevisionDate(expectedRevisionDate);
        expectedTrade.setDealName(expectedDealName);
        expectedTrade.setDealType(expectedDealType);
        expectedTrade.setSourceListId(expectedSourceListId);
        expectedTrade.setSide(expectedSide);
    }

    @Test
    void testGetAllTrade() {
        List<Trade> expectedTradeList = new ArrayList<>();
        expectedTradeList.add(expectedTrade);

        when(tradeRepository.findAll()).thenReturn(expectedTradeList);

        Iterable<Trade> actualTradeList = tradeService.getTradeList();

        assertEquals(expectedTradeList, actualTradeList);
        verify(tradeRepository, times(1)).findAll();
    }

    @Test
    void testGetTradeById() {
        when(tradeRepository.findById(1)).thenReturn(Optional.ofNullable(expectedTrade));

        Trade actualTrade = tradeService.getTradeById(1);

        assertEquals(expectedTrade, actualTrade);
        verify(tradeRepository, times(1)).findById(1);
    }

    @Test
    void testGetTradeById_whenEmpty_returnException() {
        when(tradeRepository.findById(100)).thenReturn(Optional.empty());

        Throwable exception = assertThrows(TradeNotFoundException.class, () -> {
            tradeService.getTradeById(100);
        });

        assertEquals("Trade with id 100 not found", exception.getMessage());
        verify(tradeRepository, times(1)).findById(100);
    }

    @Test
    void testDeleteTrade() {
        doNothing().when(tradeRepository).delete(expectedTrade);
        when(tradeRepository.findById(1)).thenReturn(Optional.empty());

        assertDoesNotThrow(
                () -> tradeService.deleteTrade(expectedTrade)
        );

        verify(tradeRepository, times(1)).delete(expectedTrade);
        verify(tradeRepository, times(1)).findById(1);
    }

    @Test
    void testDeleteTrade_whenTradeIsPresent_returnException() {
        doNothing().when(tradeRepository).delete(expectedTrade);
        when(tradeRepository.findById(1)).thenReturn(Optional.ofNullable(expectedTrade));

        Throwable exception = assertThrows(TradeNotDeletedException.class, () -> {
            tradeService.deleteTrade(expectedTrade);
        });

        assertEquals("Trade with id 1 has not been deleted", exception.getMessage());
        verify(tradeRepository, times(1)).delete(expectedTrade);
        verify(tradeRepository, times(1)).findById(1);
    }

    @Test
    void testAddTrade() {
        when(tradeRepository.save(any(Trade.class))).thenReturn(expectedTrade);

        tradeService.addTrade(expectedTrade);

        verify(tradeRepository, times(1)).save(expectedTrade);
    }
    @Test
    void testUpdateTrade() {
        expectedTrade.setAccount("updated account");
        int actualTradeId = 1;
        when(tradeRepository.save(expectedTrade)).thenReturn(expectedTrade);

        tradeService.updateTrade(actualTradeId, expectedTrade);

        assertEquals("updated account", expectedTrade.getAccount());
        assertEquals(actualTradeId, expectedTrade.getTradeId());
        verify(tradeRepository, times(1)).save(expectedTrade);
    }
}
