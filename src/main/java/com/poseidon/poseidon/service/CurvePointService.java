package com.poseidon.poseidon.service;

import com.poseidon.poseidon.domain.CurvePoint;

public interface CurvePointService {
    Iterable<CurvePoint> getCurvePointLists();

    void deleteCurvePoint(CurvePoint curvePoint);

    CurvePoint getCurvePointById(int curvePointId);

    void addCurvePoint(CurvePoint curvePoint);

    void updateCurvePoint(int existingCurvePointId, CurvePoint curvePoint);
}
