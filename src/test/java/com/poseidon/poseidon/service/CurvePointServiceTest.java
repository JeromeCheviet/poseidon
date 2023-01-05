package com.poseidon.poseidon.service;

import com.poseidon.poseidon.domain.CurvePoint;
import com.poseidon.poseidon.exception.DataNotDeletedException;
import com.poseidon.poseidon.exception.DataNotFoundException;
import com.poseidon.poseidon.repositories.CurvePointRepository;
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
class CurvePointServiceTest {

    @InjectMocks
    private CurvePointService curvePointService = new CurvePointServiceImpl();

    @Mock
    private CurvePointRepository curvePointRepository;

    @Mock
    private CurvePoint expectedCurvePoint;

    @BeforeEach
    private void setUp() {
        int expectedId = 1;
        Integer expectedCurveId = 1;
        LocalDate expectedAsOfDate = LocalDate.now();
        Double expectedTerm = 1.0;
        Double expectedValue = 1.5;
        LocalDate expectedCreationDate = LocalDate.now().minusDays(5);

        expectedCurvePoint = new CurvePoint();

        expectedCurvePoint.setId(expectedId);
        expectedCurvePoint.setCurveId(expectedCurveId);
        expectedCurvePoint.setTerm(expectedTerm);
        expectedCurvePoint.setAsOfDate(expectedAsOfDate);
        expectedCurvePoint.setValue(expectedValue);
        expectedCurvePoint.setCreationDate(expectedCreationDate);
    }

    @Test
    void testCurvePointLists() {
        List<CurvePoint> expectedCurvePointList = new ArrayList<>();
        expectedCurvePointList.add(expectedCurvePoint);

        when(curvePointRepository.findAll()).thenReturn(expectedCurvePointList);

        Iterable<CurvePoint> actualCurvePointList = curvePointService.getCurvePointLists();

        assertEquals(expectedCurvePointList, actualCurvePointList);
        verify(curvePointRepository, times(1)).findAll();
    }

    @Test
    void testGetCurvePointById() {
        when(curvePointRepository.findById(1)).thenReturn(Optional.ofNullable(expectedCurvePoint));

        CurvePoint actualCurvePoint = curvePointService.getCurvePointById(1);

        assertEquals(expectedCurvePoint, actualCurvePoint);
        verify(curvePointRepository, times(1)).findById(1);
    }

    @Test
    void testGetCurvePointByID_whenEmpty_returnException() {
        when(curvePointRepository.findById(100)).thenReturn(Optional.empty());

        Throwable exception = assertThrows(DataNotFoundException.class, () -> {
            curvePointService.getCurvePointById(100);
        });

        assertEquals("CurvePoint with id 100 not found", exception.getMessage());
        verify(curvePointRepository, times(1)).findById(100);
    }

    @Test
    void testDeleteCurvePoint() {
        doNothing().when(curvePointRepository).delete(expectedCurvePoint);
        when(curvePointRepository.findById(1)).thenReturn(Optional.empty());

        assertDoesNotThrow(
                () -> curvePointService.deleteCurvePoint(expectedCurvePoint)
        );

        verify(curvePointRepository, times(1)).delete(expectedCurvePoint);
        verify(curvePointRepository, times(1)).findById(1);
    }

    @Test
    void testDeleteCurvePoint_whenCurvePointIsPresent_returnException() {
        doNothing().when(curvePointRepository).delete(expectedCurvePoint);
        when(curvePointRepository.findById(1)).thenReturn(Optional.ofNullable(expectedCurvePoint));

        Throwable exception = assertThrows(DataNotDeletedException.class, () -> {
            curvePointService.deleteCurvePoint(expectedCurvePoint);
        });

        assertEquals("CurvePoint with id 1 has not been deleted", exception.getMessage());
        verify(curvePointRepository, times(1)).delete(expectedCurvePoint);
        verify(curvePointRepository, times(1)).findById(1);
    }

    @Test
    void testAddCurvePoint() {
        when(curvePointRepository.save(any(CurvePoint.class))).thenReturn(expectedCurvePoint);

        curvePointService.addCurvePoint(expectedCurvePoint);

        verify(curvePointRepository, times(1)).save(expectedCurvePoint);
    }

    @Test
    void testUpdateCurvePoint() {
        expectedCurvePoint.setValue(20.0);
        int actualCurvePointId = 1;

        when(curvePointRepository.save(expectedCurvePoint)).thenReturn(expectedCurvePoint);
        curvePointService.updateCurvePoint(actualCurvePointId, expectedCurvePoint);

        assertEquals(20.0, expectedCurvePoint.getValue());
        assertEquals(actualCurvePointId, expectedCurvePoint.getCurveId());
        verify(curvePointRepository, times(1)).save(expectedCurvePoint);
    }

}
