package com.poseidon.poseidon.service;

import com.poseidon.poseidon.domain.BidList;
import com.poseidon.poseidon.exception.BidListNotDeletedException;
import com.poseidon.poseidon.exception.BidListNotFoundException;
import com.poseidon.poseidon.repositories.BidListRepository;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
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
        expectedBidList.setBidQuantity(expectedBidQuantity);
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

    @Test
    void testGetBidListById() {
        when(bidListRepository.findById(1)).thenReturn(Optional.ofNullable(expectedBidList));

        BidList actualBidList = bidListService.getBidlistById(1);

        assertEquals(expectedBidList, actualBidList);
        verify(bidListRepository, times(1)).findById(1);
    }

    @Test
    void testGetBidListById_whenEmpty_returnException() {
        when(bidListRepository.findById(100)).thenReturn(Optional.empty());

        Throwable exception = assertThrows(BidListNotFoundException.class, () -> {
            bidListService.getBidlistById(100);
        });

        assertEquals("Bidlist with id 100 not found", exception.getMessage());
        verify(bidListRepository, times(1)).findById(100);
    }

    @Test
    void testAddBidList() {
        when(bidListRepository.save(any(BidList.class))).thenReturn(expectedBidList);

        bidListService.addBidList(expectedBidList);

        verify(bidListRepository, times(1)).save(expectedBidList);
    }

    @Test
    void testUpdateBidList() {
        expectedBidList.setAccount("updated account");
        int actualBidListId = 1;
        when(bidListRepository.save(expectedBidList)).thenReturn(expectedBidList);

        bidListService.updateBidList(actualBidListId, expectedBidList);

        assertEquals("updated account", expectedBidList.getAccount());
        assertEquals(actualBidListId, expectedBidList.getBidListId());
        verify(bidListRepository, times(1)).save(expectedBidList);
    }

    @Test
    void testDeleteBidList() {
        doNothing().when(bidListRepository).delete(expectedBidList);
        when(bidListRepository.findById(1)).thenReturn(Optional.empty());

        assertDoesNotThrow(
                () -> bidListService.deleteBidList(expectedBidList)
        );

        verify(bidListRepository, times(1)).delete(expectedBidList);
        verify(bidListRepository, times(1)).findById(1);
    }

    @Test
    void testDeleteBidList_whenBidListIsPresent_returnException() {
        doNothing().when(bidListRepository).delete(expectedBidList);
        when(bidListRepository.findById(1)).thenReturn(Optional.ofNullable(expectedBidList));

        Throwable exception = assertThrows(BidListNotDeletedException.class, () -> {
            bidListService.deleteBidList(expectedBidList);
        });

        assertEquals("Bidlist with id 1 has not been deleted", exception.getMessage());
        verify(bidListRepository, times(1)).delete(expectedBidList);
        verify(bidListRepository, times(1)).findById(1);
    }
}
