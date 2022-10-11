package com.poseidon.poseidon.service;

import com.poseidon.poseidon.domain.CurvePoint;

import java.util.Optional;

public interface CurvePointService {
    Iterable<CurvePoint> getCurvePointLists();

    void deleteCurvePoint(CurvePoint curvePoint);

    Optional<CurvePoint> getCurvePointById(int curvePointId);

    void addCurvePoint(CurvePoint curvePoint);

    void updateCurvePoint(int existingCurvePointId, CurvePoint curvePoint);
}
