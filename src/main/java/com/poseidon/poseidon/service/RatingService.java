package com.poseidon.poseidon.service;

import com.poseidon.poseidon.domain.Rating;

/**
 * Interface link to CurvePoint operations
 */
public interface RatingService {

    /**
     * Get all rating
     *
     * @return an iterable list contains all rating.
     */
    Iterable<Rating> getRatingList();

    /**
     * Delete one rating
     *
     * @param rating An object which contain the rating to delete
     */
    void deleteRating(Rating rating);

    /**
     * Get one rating by his ID.
     *
     * @param ratingId int ID number
     * @return An object which contain the rating.
     */
    Rating getRatingById(int ratingId);

    /**
     * Add one rating
     *
     * @param rating An object which contain the rating to add
     */
    void addRating(Rating rating);

    /**
     * Update one rating
     *
     * @param existingRatingId Int ID number of rating to update
     * @param rating           An object which contain Rating's data to update.
     */
    void updateRating(int existingRatingId, Rating rating);
}
