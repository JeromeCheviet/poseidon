package com.poseidon.poseidon.controllers;

import com.poseidon.poseidon.domain.Rating;
import com.poseidon.poseidon.service.RatingService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

/**
 * Class which manage the Rating pages. Multiple API are present to see, add, update and delete Rating.
 */
@Controller
public class RatingController {
    private static final Logger logger = LogManager.getLogger(RatingController.class);

    @Autowired
    private RatingService ratingService;

    /**
     * Method to loading the main page of Rating operations.
     *
     * @param model An object which contain all ratings.
     * @return String The URI to load.
     */
    @RequestMapping("/rating/list")
    public String home(Model model) {
        logger.debug("Access /rating/list page");
        Iterable<Rating> ratings = ratingService.getRatingList();

        model.addAttribute("ratings", ratings);

        return "rating/list";
    }

    /**
     * Method to loading adding rating form.
     *
     * @param rating Object which contain an empty rating.
     * @return String the URI to load
     */
    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating) {
        logger.debug("Access /rating/add page.");
        return "rating/add";
    }

    /**
     * Method to post a new Rating
     *
     * @param rating An object which contain a new rating
     * @param result The result of validating rating
     * @param model  An object which contain the list of all ratings.
     * @return String If the new rating is valid, redirect to rating/list, else load the adding form.
     */
    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {
        logger.debug("Post valid form to add new rating");
        if (result.hasErrors()) {
            logger.info("Add form not valid !");
            return "rating/add";
        }

        ratingService.addRating(rating);
        logger.info("New rating added");
        model.addAttribute("ratings", ratingService.getRatingList());

        return "redirect:/rating/list";
    }

    /**
     * Method to load the updating form.
     *
     * @param id    The bid ID to update
     * @param model Object which contain the rating to update
     * @return String If rating exist into database, launch form with data of Rating. Else, load the Rating List page.
     */
    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        logger.debug("Access /rating/update/{} page.", id);
        Rating rating = ratingService.getRatingById(id);
        if (rating.getId() == id) {
            model.addAttribute("rating", rating);
            return "rating/update";
        }
        logger.info("No data found in database for id {}", id);
        return "rating/list";
    }

    /**
     * Method to post an updated rating
     *
     * @param id     The rating ID to update
     * @param rating Object which contain the rating to update
     * @param result The result of validating Rating
     * @param model  An object which contain the list of all ratings.
     * @return String If the updated rating is valid, redirect to rating/list, else load the updating form.
     */
    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                               BindingResult result, Model model) {
        logger.debug("Post valid form to update an existing rating");
        if (result.hasErrors()) {
            logger.info("Update form not valid !");
            return "rating/update";
        }

        ratingService.updateRating(id, rating);
        logger.info("Rating with id {} have been updated", id);
        model.addAttribute("ratings", ratingService.getRatingList());

        return "redirect:/rating/list";
    }

    /**
     * Method to deleting a Rating
     * <br>
     * If rating exist, it will send to RatingService class to be deleted.
     *
     * @param id    The rating ID to update
     * @param model An object which contain the list of all ratings.
     * @return String to redirect to Rating List page.
     */
    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        logger.debug("Access /rating/delete/{} page", id);
        Rating rating = ratingService.getRatingById(id);

        if (rating.getId() == id) {
            ratingService.deleteRating(rating);
            logger.info("Rating with id {} has been deleted.", id);
        }

        model.addAttribute("ratings", ratingService.getRatingList());

        return "redirect:/rating/list";
    }
}
