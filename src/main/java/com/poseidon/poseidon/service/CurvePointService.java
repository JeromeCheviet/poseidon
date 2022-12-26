package com.poseidon.poseidon.service;

import com.poseidon.poseidon.domain.CurvePoint;

/**
 * Interface link to CurvePoint operations
 */
public interface CurvePointService {

    /**
     * Get all Curve Point
     *
     * @return an iterable list contains all curve point.
     */
    Iterable<CurvePoint> getCurvePointLists();

    /**
     * Delete one curve point
     *
     * @param curvePoint An object which contain the CurvePoint to delete
     */
    void deleteCurvePoint(CurvePoint curvePoint);

    /**
     * Get one curve point by his ID.
     *
     * @param curvePointId int ID number
     * @return An object which contain the Curve point.
     */
    CurvePoint getCurvePointById(int curvePointId);

    /**
     * Add one Curve point
     *
     * @param curvePoint An object which contain the curve point to add
     */
    void addCurvePoint(CurvePoint curvePoint);

    /**
     * Update one Bid
     *
     * @param existingCurvePointId Int ID number of curve point to update
     * @param curvePoint           An object which contain CurvePoint's data to update.
     */
    void updateCurvePoint(int existingCurvePointId, CurvePoint curvePoint);
}
