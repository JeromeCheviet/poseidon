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
import java.util.Optional;

@Controller
public class CurveController {
    private static final Logger logger = LogManager.getLogger(CurveController.class);

    @Autowired
    private CurvePointService curvePointService;

    @RequestMapping("/curvePoint/list")
    public String home(Model model) {
        logger.debug("Access /curvePoint/list page");
        Iterable<CurvePoint> curvePoints = curvePointService.getCurvePointLists();

        model.addAttribute("curvePoints", curvePoints);

        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String addBidForm(CurvePoint bid) {
        logger.debug("Access /curvePoint/add page.");
        return "curvePoint/add";
    }

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
