package com.poseidon.poseidon.repositories;

import com.poseidon.poseidon.domain.Rating;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface to build queries runs in table rating.
 */
@Repository
public interface RatingRepository extends CrudRepository<Rating, Integer> {

}
