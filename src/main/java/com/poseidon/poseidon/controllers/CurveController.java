package com.poseidon.poseidon.controllers;

import com.poseidon.poseidon.domain.CurvePoint;
import com.poseidon.poseidon.service.CurvePointService;
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
 * Class which manage the CurvePoint pages. Multiple API are present to see, add, update and delete Curve.
 */
@Controller
public class CurveController {
    private static final Logger logger = LogManager.getLogger(CurveController.class);

    @Autowired
    private CurvePointService curvePointService;

    /**
     * Method to loading the main page of Curve operations.
     *
     * @param model An object which contain all curves.
     * @return String The URI to load.
     */
    @RequestMapping("/curvePoint/list")
    public String home(Model model) {
        logger.debug("Access /curvePoint/list page");
        Iterable<CurvePoint> curvePoints = curvePointService.getCurvePointLists();

        model.addAttribute("curvePoints", curvePoints);

        return "curvePoint/list";
    }

    /**
     * Method to loading adding Curve form.
     *
     * @param curve Object which contain an empty curve.
     * @return String the URI to load
     */
    @GetMapping("/curvePoint/add")
    public String addBidForm(CurvePoint curve) {
        logger.debug("Access /curvePoint/add page.");
        return "curvePoint/add";
    }

    /**
     * Method to post a new Curve
     *
     * @param curvePoint An object which contain a new curve
     * @param result     The result of validating Curve
     * @param model      An object which contain the list of all curves.
     * @return String If the new curve is valid, redirect to curvePoint/list, else load the adding form.
     */
    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
        logger.debug("Post validate form to add new curve point.");
        if (result.hasErrors()) {
            logger.info("Add form not valid !");
            return "curvePoint/add";
        }

        curvePointService.addCurvePoint(curvePoint);
        logger.info("new curve added");
        model.addAttribute("curvePoints", curvePointService.getCurvePointLists());

        return "curvePoint/list";
    }

    /**
     * Method to load the updating form.
     *
     * @param id    The curve ID to update
     * @param model Object which contain the curve to update
     * @return String If curve exist into database, launch form with data of Curve. Else, load the Curve List page.
     */
    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        logger.debug("Access /curvePoint/update/{} page", id);
        CurvePoint curvePoint = curvePointService.getCurvePointById(id);
        if (curvePoint.getCurveId() == id) {
            model.addAttribute("curvePoint", curvePoint);
            return "curvePoint/update";
        }

        model.addAttribute("curvePoints", curvePointService.getCurvePointLists());

        return "curvePoint/list";
    }

    /**
     * Method to post an updated curve
     *
     * @param id         The curve ID to update
     * @param curvePoint Object which contain the bid to update
     * @param result     The result of validating Curve
     * @param model      An object which contain the list of all curves.
     * @return String If the updated curve is valid, redirect to curvePoint/list, else load the updating form.
     */
    @PostMapping("/curvePoint/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
                            BindingResult result, Model model) {
        logger.debug("Post validate form to update an existing curve");
        if (result.hasErrors()) {
            logger.info("Update form not valid !");
            return "curvePoint/update";
        }

        curvePointService.updateCurvePoint(id, curvePoint);
        logger.info("Curve with id {} have been updated.", id);
        model.addAttribute("curvePoints", curvePointService.getCurvePointLists());

        return "redirect:/curvePoint/list";
    }

    /**
     * Method to deleting a Curve
     * <br>
     * If curve exist, it will send to CurvePointService class to be deleted.
     *
     * @param id    The curve ID to update
     * @param model An object which contain the list of all curves.
     * @return String to redirect to curve List page.
     */
    @GetMapping("/curvePoint/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        logger.debug("Access /curvePoint/delete/{} page", id);
        CurvePoint curvePoint = curvePointService.getCurvePointById(id);

        if (curvePoint.getCurveId() == id) {
            curvePointService.deleteCurvePoint(curvePoint);
            logger.info("Curve point with id {} has been deleted.", id);
        }

        model.addAttribute("curvePoints", curvePointService.getCurvePointLists());

        return "redirect:/curvePoint/list";
    }
}
