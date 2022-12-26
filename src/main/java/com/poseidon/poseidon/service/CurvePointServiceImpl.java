package com.poseidon.poseidon.service;

import com.poseidon.poseidon.domain.CurvePoint;
import com.poseidon.poseidon.exception.DataNotDeletedException;
import com.poseidon.poseidon.exception.DataNotFoundException;
import com.poseidon.poseidon.repositories.CurvePointRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Class to interact with CurvePoint table data
 */
@Service
public class CurvePointServiceImpl implements CurvePointService {

    private static final Logger logger = LogManager.getLogger(CurvePointServiceImpl.class);

    @Autowired
    private CurvePointRepository curvePointRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<CurvePoint> getCurvePointLists() {
        logger.debug("Get all Curve point");

        return curvePointRepository.findAll();
    }

    /**
     * {@inheritDoc}
     *
     * <br>Before deleting, the method save the Curve point ID in a variable. After deleted the curve point, the method
     * search if a Curve point with this ID exist. If Curve point is present, a private exception is throw.
     */
    @Override
    public void deleteCurvePoint(CurvePoint curvePoint) {
        int id = curvePoint.getId();
        logger.debug("Delete curve point {}", curvePoint.getCurveId());

        curvePointRepository.delete(curvePoint);

        Optional<CurvePoint> deletedCurvePoint = curvePointRepository.findById(id);
        if (deletedCurvePoint.isPresent())
            throw new DataNotDeletedException("CurvePoint with id " + id + " has not been deleted");
    }

    /**
     * {@inheritDoc}
     *
     * <br>If no Curve point is found, a private exception is throw.
     */
    @Override
    public CurvePoint getCurvePointById(int curvePointId) {
        logger.debug("Get Curve Point with id : {}", curvePointId);

        return curvePointRepository.findById(curvePointId).orElseThrow(
                () -> new DataNotFoundException("CurvePoint with id " + curvePointId + " not found")
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addCurvePoint(CurvePoint curvePoint) {
        logger.debug("Add new Curve Point : {}", curvePoint.getCurveId());

        curvePointRepository.save(curvePoint);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateCurvePoint(int existingCurvePointId, CurvePoint curvePoint) {
        logger.debug("Updating curve point with id {}", existingCurvePointId);

        curvePoint.setCurveId(existingCurvePointId);
        curvePointRepository.save(curvePoint);
    }
}
