package com.poseidon.poseidon;

import com.poseidon.poseidon.domain.CurvePoint;
import com.poseidon.poseidon.repositories.CurvePointRepository;
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
class CurvePointTests {

    @Autowired
    private CurvePointRepository curvePointRepository;

    @Test
    void curvePointTest() {
        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setCurveId(10);
        curvePoint.setTerm(10d);
        curvePoint.setValue(30d);

        // Save
        curvePoint = curvePointRepository.save(curvePoint);
        assertNotNull(curvePoint.getId());
        assertTrue(curvePoint.getCurveId() == 10);

        // Update
        curvePoint.setCurveId(20);
        curvePoint = curvePointRepository.save(curvePoint);
        assertTrue(curvePoint.getCurveId() == 20);

        // Find
        Iterable<CurvePoint> curvePoints = curvePointRepository.findAll();
        List<CurvePoint> listResult = new ArrayList<>();
        curvePoints.forEach(listResult::add);
        assertTrue(listResult.size() > 0);

        // Delete
        Integer id = curvePoint.getId();
        curvePointRepository.delete(curvePoint);
        Optional<CurvePoint> curvePointList = curvePointRepository.findById(id);
        assertFalse(curvePointList.isPresent());
    }

}
