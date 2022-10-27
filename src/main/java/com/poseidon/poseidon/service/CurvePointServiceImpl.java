package com.poseidon.poseidon.service;

import com.poseidon.poseidon.domain.CurvePoint;
import com.poseidon.poseidon.exception.CurvePointNotDeletedException;
import com.poseidon.poseidon.exception.CurvePointNotFoundException;
import com.poseidon.poseidon.repositories.CurvePointRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CurvePointServiceImpl implements CurvePointService {

    private static final Logger logger = LogManager.getLogger(CurvePointServiceImpl.class);

    @Autowired
    private CurvePointRepository curvePointRepository;

    @Override
    public Iterable<CurvePoint> getCurvePointLists() {
        logger.debug("Get all Curve point");

        return curvePointRepository.findAll();
    }

    @Override
    public void deleteCurvePoint(CurvePoint curvePoint) {
        int id = curvePoint.getId();
        logger.debug("Delete curve point {}", curvePoint.getCurveId());

        curvePointRepository.delete(curvePoint);

        Optional<CurvePoint> deletedCurvePoint = curvePointRepository.findById(id);
        if (deletedCurvePoint.isPresent())
            throw new CurvePointNotDeletedException("CurvePoint with id " + id + " has not been deleted");
    }

    @Override
    public CurvePoint getCurvePointById(int curvePointId) {
        logger.debug("Get Curve Point with id : {}", curvePointId);

        return curvePointRepository.findById(curvePointId).orElseThrow(
                () -> new CurvePointNotFoundException("CurvePoint with id " + curvePointId + " not found")
        );
    }

    @Override
    public void addCurvePoint(CurvePoint curvePoint) {
        logger.debug("Add new Curve Point : {}", curvePoint.getCurveId());

        curvePointRepository.save(curvePoint);
    }

    @Override
    public void updateCurvePoint(int existingCurvePointId, CurvePoint curvePoint) {
        logger.debug("Updating curve point with id {}", existingCurvePointId);

        curvePoint.setCurveId(existingCurvePointId);
        curvePointRepository.save(curvePoint);
    }
}
