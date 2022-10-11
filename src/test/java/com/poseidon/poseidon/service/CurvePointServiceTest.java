package com.poseidon.poseidon.service;

import com.poseidon.poseidon.domain.CurvePoint;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        int expectedCurveId = 1;
        LocalDate expectedAsOfDate = LocalDate.now();
        double expectedTerm = 1.0;
        double expectedValue = 1.5;
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
    void testCurveListById() {
        when(curvePointRepository.findById(1)).thenReturn(Optional.ofNullable(expectedCurvePoint));

        Optional<CurvePoint> actualCurvePoint = curvePointService.getCurvePointById(1);

        assertEquals(expectedCurvePoint, actualCurvePoint.get());
        verify(curvePointRepository, times(1)).findById(1);
    }

    @Test
    void testDeleteCurvePoint() {
        doNothing().when(curvePointRepository).delete(expectedCurvePoint);

        curvePointService.deleteCurvePoint(expectedCurvePoint);

        verify(curvePointRepository, times(1)).delete(expectedCurvePoint);
    }

    @Test
    void testAddCurvePoint() {
        when(curvePointRepository.save(any(CurvePoint.class))).thenReturn(expectedCurvePoint);

        curvePointService.addCurvePoint(expectedCurvePoint);

        verify(curvePointRepository, times(1)).save(expectedCurvePoint);
    }

    @Test
    void testUpdateCurvePoint() {
        expectedCurvePoint.setValue(20);
        int actualCurvePointId = 1;

        when(curvePointRepository.save(expectedCurvePoint)).thenReturn(expectedCurvePoint);
        curvePointService.updateCurvePoint(actualCurvePointId, expectedCurvePoint);

        assertEquals(20, expectedCurvePoint.getValue());
        assertEquals(actualCurvePointId, expectedCurvePoint.getCurveId());
        verify(curvePointRepository, times(1)).save(expectedCurvePoint);
    }

}
