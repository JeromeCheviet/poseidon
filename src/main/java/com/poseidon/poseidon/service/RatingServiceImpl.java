package com.poseidon.poseidon.service;

import com.poseidon.poseidon.domain.Rating;
import com.poseidon.poseidon.exception.RatingNotDeletedException;
import com.poseidon.poseidon.exception.RatingNotFoundException;
import com.poseidon.poseidon.repositories.RatingRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RatingServiceImpl implements RatingService {

    private static final Logger logger = LogManager.getLogger(RatingServiceImpl.class);

    @Autowired
    private RatingRepository ratingRepository;

    @Override
    public Iterable<Rating> getRatingList() {
        logger.debug("Get all rating");

        return ratingRepository.findAll();
    }

    @Override
    public void deleteRating(Rating rating) {
        int id = rating.getId();
        logger.debug("Delete rating {}", id);

        ratingRepository.delete(rating);

        Optional<Rating> deletedRating = ratingRepository.findById(id);
        if (deletedRating.isPresent())
            throw new RatingNotDeletedException("Rating with id " + id + " has not been deleted");
    }

    @Override
    public Rating getRatingById(int ratingId) {
        logger.debug("Get rating with id : {}", ratingId);

        return ratingRepository.findById(ratingId).orElseThrow(
                () -> new RatingNotFoundException("Rating with id " + ratingId + " not found")
        );
    }

    @Override
    public void addRating(Rating rating) {
        logger.debug("Add new rating : {}", rating.getMoodysRating());

        ratingRepository.save(rating);
    }

    @Override
    public void updateRating(int existingRatingId, Rating rating) {
        logger.debug("Updating rating with id {}", existingRatingId);
        rating.setId(existingRatingId);

        ratingRepository.save(rating);
    }
}
