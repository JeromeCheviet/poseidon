package com.poseidon.poseidon.service;

import com.poseidon.poseidon.domain.BidList;
import com.poseidon.poseidon.repositories.BidListRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BidListServiceTest {

    @InjectMocks
    private BidListService bidListService = new BidListServiceImpl();

    @Mock
    private BidListRepository bidListRepository;

    @Mock
    private BidList expectedBidList;

    @BeforeEach
    private void setUp() {
        int expectedBidListId = 1;
        String expectedAccount = "account";
        String expectedType = "type";
        double expectedBidQuantity = 10.0;
        double expectedAskQuantity = 5.0;
        double expectedBid = 2.0;
        double expectedAsk = 1.0;
        String expectedBenchmark = "benchmark";
        LocalDate expectedBidListDate = LocalDate.now();
        String expectedCommentary = "commentary";
        String expectedSecurity = "security";
        String expectedStatus = "status";
        String expectedTrader = "trader";
        String expectedBook = "book";
        String expectedCreationName = "creation";
        LocalDate expectedCreationDate = LocalDate.now();
        String expectedRevisionName = "revision";
        LocalDate expectedRevisionDate = LocalDate.now();
        String expectedDealName = "deal";
        String expectedDealType = "dealtype";
        String expectedSourceListId = "source";
        String expectedSide = "side";

        expectedBidList = new BidList();
        expectedBidList.setBidListId(expectedBidListId);
        expectedBidList.setAccount(expectedAccount);
        expectedBidList.setType(expectedType);
        expectedBidList.setBibQuantity(expectedBidQuantity);
        expectedBidList.setAskQuantity(expectedAskQuantity);
        expectedBidList.setBid(expectedBid);
        expectedBidList.setAsk(expectedAsk);
        expectedBidList.setBenchmark(expectedBenchmark);
        expectedBidList.setBidListDate(expectedBidListDate);
        expectedBidList.setCommentary(expectedCommentary);
        expectedBidList.setSecurity(expectedSecurity);
        expectedBidList.setStatus(expectedStatus);
        expectedBidList.setTrader(expectedTrader);
        expectedBidList.setBook(expectedBook);
        expectedBidList.setCreationName(expectedCreationName);
        expectedBidList.setCreationDate(expectedCreationDate);
        expectedBidList.setRevisionName(expectedRevisionName);
        expectedBidList.setRevisionDate(expectedRevisionDate);
        expectedBidList.setDealName(expectedDealName);
        expectedBidList.setDealType(expectedDealType);
        expectedBidList.setSourceListId(expectedSourceListId);
        expectedBidList.setSide(expectedSide);

    }

    @Test
    void testGetBidLists() {
        List<BidList> expectedBidLists = new ArrayList<>();
        expectedBidLists.add(expectedBidList);

        when(bidListRepository.findAll()).thenReturn(expectedBidLists);

        Iterable<BidList> actualBidList = bidListService.getBidLists();

        assertEquals(expectedBidLists, actualBidList);
        verify(bidListRepository, times(1)).findAll();

    }
}
