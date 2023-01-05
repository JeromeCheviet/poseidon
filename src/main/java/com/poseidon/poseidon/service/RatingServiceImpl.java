package com.poseidon.poseidon.service;

import com.poseidon.poseidon.domain.Rating;
import com.poseidon.poseidon.exception.DataNotDeletedException;
import com.poseidon.poseidon.exception.DataNotFoundException;
import com.poseidon.poseidon.repositories.RatingRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Class to interact with Rating table data
 */
@Service
public class RatingServiceImpl implements RatingService {

    private static final Logger logger = LogManager.getLogger(RatingServiceImpl.class);

    @Autowired
    private RatingRepository ratingRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<Rating> getRatingList() {
        logger.debug("Get all rating");

        return ratingRepository.findAll();
    }

    /**
     * {@inheritDoc}
     *
     * <br>Before deleting, the method save the Rating ID in a variable. After deleted the Rating, the method
     * search if a Rating with this ID exist. If Rating is present, a private exception is throw.
     */
    @Override
    public void deleteRating(Rating rating) {
        int id = rating.getId();
        logger.debug("Delete rating {}", id);

        ratingRepository.delete(rating);

        Optional<Rating> deletedRating = ratingRepository.findById(id);
        if (deletedRating.isPresent())
            throw new DataNotDeletedException("Rating with id " + id + " has not been deleted");
    }

    /**
     * {@inheritDoc}
     *
     * <br>If no Rating is found, a private exception is throw.
     */
    @Override
    public Rating getRatingById(int ratingId) {
        logger.debug("Get rating with id : {}", ratingId);

        return ratingRepository.findById(ratingId).orElseThrow(
                () -> new DataNotFoundException("Rating with id " + ratingId + " not found")
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addRating(Rating rating) {
        logger.debug("Add new rating : {}", rating.getMoodysRating());

        ratingRepository.save(rating);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateRating(int existingRatingId, Rating rating) {
        logger.debug("Updating rating with id {}", existingRatingId);
        rating.setId(existingRatingId);

        ratingRepository.save(rating);
    }
}
