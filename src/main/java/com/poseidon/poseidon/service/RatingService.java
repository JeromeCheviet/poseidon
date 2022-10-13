package com.poseidon.poseidon.service;

import com.poseidon.poseidon.domain.Rating;

import java.util.Optional;

public interface RatingService {

    Iterable<Rating> getRatingList();

    void deleteRating(Rating rating);

    Optional<Rating> getRatingById(int ratingId);

    void addRating(Rating rating);

    void updateRating(int existingRatingId, Rating rating);
}
