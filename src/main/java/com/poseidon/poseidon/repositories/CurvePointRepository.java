package com.poseidon.poseidon.repositories;

import com.poseidon.poseidon.domain.CurvePoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CurvePointRepository extends CrudRepository<CurvePoint, Integer> {
}
