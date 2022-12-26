package com.poseidon.poseidon.repositories;

import com.poseidon.poseidon.domain.CurvePoint;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface to build queries runs in table curvepoint.
 */
@Repository
public interface CurvePointRepository extends CrudRepository<CurvePoint, Integer> {
}
