package com.poseidon.poseidon.service;

import com.poseidon.poseidon.domain.Rating;
import com.poseidon.poseidon.exception.RatingNotDeletedException;
import com.poseidon.poseidon.exception.RatingNotFoundException;
import com.poseidon.poseidon.repositories.RatingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RatingServiceTest {

    @InjectMocks
    private RatingService ratingService = new RatingServiceImpl();

    @Mock
    private RatingRepository ratingRepository;

    @Mock
    private Rating expectedRating;

    @BeforeEach
    private void setUp(){
        int expectedRatingId = 1;
        String expectedMoodysRating = "moody";
        String expectedSandRating = "sand";
        String expectedFitchRating = "fitch";
        Integer expectedOrderNumber = 10;

        expectedRating = new Rating();

        expectedRating.setId(expectedRatingId);
        expectedRating.setMoodysRating(expectedMoodysRating);
        expectedRating.setSandPRating(expectedSandRating);
        expectedRating.setFitchRating(expectedFitchRating);
        expectedRating.setOrderNumber(expectedOrderNumber);
    }

    @Test
    void testGetRatingLists() {
        List<Rating> expectedRatingList = new ArrayList<>();
        expectedRatingList.add(expectedRating);

        when(ratingRepository.findAll()).thenReturn(expectedRatingList);

        Iterable<Rating> actualRatingList = ratingService.getRatingList();

        assertEquals(expectedRatingList, actualRatingList);
        verify(ratingRepository, times(1)).findAll();
    }

    @Test
    void testGetRatingById() {
        when(ratingRepository.findById(1)).thenReturn(Optional.ofNullable(expectedRating));

        Rating actualRating = ratingService.getRatingById(1);

        assertEquals(expectedRating, actualRating);
        verify(ratingRepository, times(1)).findById(1);
    }

    @Test
    void testGetRatingById_whenEmpty_returnException() {
        when(ratingRepository.findById(100)).thenReturn(Optional.empty());

        Throwable exception = assertThrows(RatingNotFoundException.class, () -> {
            ratingService.getRatingById(100);
        });

        assertEquals("Rating with id 100 not found", exception.getMessage());
        verify(ratingRepository, times(1)).findById(100);
    }

    @Test
    void testDeleteRating() {
        doNothing().when(ratingRepository).delete(expectedRating);
        when(ratingRepository.findById(1)).thenReturn(Optional.empty());

        assertDoesNotThrow(
                () -> ratingService.deleteRating(expectedRating)
        );

        verify(ratingRepository, times(1)).delete(expectedRating);
        verify(ratingRepository, times(1)).findById(1);
    }

    @Test
    void testDeleteRating_whenRatingIsPresent_returnException() {
        doNothing().when(ratingRepository).delete(expectedRating);
        when(ratingRepository.findById(1)).thenReturn(Optional.ofNullable(expectedRating));

        Throwable exception = assertThrows(RatingNotDeletedException.class, () -> {
            ratingService.deleteRating(expectedRating);
        });

        assertEquals("Rating with id 1 has not been deleted", exception.getMessage());
        verify(ratingRepository, times(1)).delete(expectedRating);
        verify(ratingRepository, times(1)).findById(1);
    }

    @Test
    void testAddRating() {
        when(ratingRepository.save(any(Rating.class))).thenReturn(expectedRating);

        ratingService.addRating(expectedRating);

        verify(ratingRepository, times(1)).save(expectedRating);
    }

    @Test
    void testUpdateRating() {
        expectedRating.setMoodysRating("new moody");
        int actualRatingId = 1;

        when(ratingRepository.save(expectedRating)).thenReturn(expectedRating);

        ratingService.updateRating(actualRatingId, expectedRating);

        assertEquals("new moody", expectedRating.getMoodysRating());
        assertEquals(actualRatingId, expectedRating.getId());
        verify(ratingRepository, times(1)).save(expectedRating);
    }
}
