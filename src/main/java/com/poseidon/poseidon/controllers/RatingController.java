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
import java.util.Optional;

@Controller
public class RatingController {
    private static final Logger logger = LogManager.getLogger(RatingController.class);

    @Autowired
    private RatingService ratingService;

    @RequestMapping("/rating/list")
    public String home(Model model)
    {
        logger.debug("Access /rating/list page");
        Iterable<Rating> ratings = ratingService.getRatingList();

        model.addAttribute("ratings", ratings);

        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating) {
        logger.debug("Access /rating/add page.");
        return "rating/add";
    }

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

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        logger.debug("Access /rating/update/{} page.", id);
        Optional<Rating> rating = ratingService.getRatingById(id);
        if (rating.isPresent()) {
            model.addAttribute("rating", rating.get());
            return "rating/update";
        }
        logger.info("No data found in database for id {}", id);
        return "rating/list";
    }

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

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        logger.debug("Access /rating/delete/{} page", id);
        Optional<Rating> rating = ratingService.getRatingById(id);

        if (rating.isPresent()) {
            ratingService.deleteRating(rating.get());
            logger.info("Rating with id {} has been deleted.", id);
        }

        model.addAttribute("ratings", ratingService.getRatingList());

        return "redirect:/rating/list";
    }
}
