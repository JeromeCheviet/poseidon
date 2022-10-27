package com.poseidon.poseidon.controllers;

import com.poseidon.poseidon.exception.*;
import com.poseidon.poseidon.service.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionController {
    private static final Logger logger = LogManager.getLogger(ExceptionController.class);
    private static final String notFound = "notfound";
    private static final String notDeleted = "notdeleted";

    @Autowired
    private BidListService bidListService;
    @Autowired
    private CurvePointService curvePointService;
    @Autowired
    private RatingService ratingService;
    @Autowired
    private RuleNameService ruleNameService;
    @Autowired
    private TradeService tradeService;

    @ExceptionHandler(BidListNotFoundException.class)
    public ModelAndView BidListNotFoundHandler(BidListNotFoundException e) {
        logger.error(e.getMessage());
        logger.warn("Redirect to /bidList/list?err=notfound page");

        ModelAndView model = new ModelAndView("redirect:/bidList/list");
        model.addObject("bidLists", bidListService.getBidLists());
        model.addObject("err", notFound);

        return model;
    }

    @ExceptionHandler(BidListNotDeletedException.class)
    public ModelAndView BidListNotDeletedHandler(BidListNotDeletedException e) {
        logger.error(e.getMessage());
        logger.warn("Redirect to /bidList/list?err=notdeleted");

        ModelAndView model = new ModelAndView("redirect:/bidList/list");
        model.addObject("bidLists", bidListService.getBidLists());
        model.addObject("err", notDeleted);

        return model;
    }

    @ExceptionHandler(CurvePointNotFoundException.class)
    public ModelAndView CurvePointNotFoundHandler(CurvePointNotFoundException e) {
        logger.error(e.getMessage());
        logger.warn("Redirect to /curvePoint/list?err=notfound page");

        ModelAndView model = new ModelAndView("redirect:/curvePoint/list");
        model.addObject("curvePoints", curvePointService.getCurvePointLists());
        model.addObject("err", notFound);

        return model;
    }

    @ExceptionHandler(CurvePointNotDeletedException.class)
    public ModelAndView CurvePointNotDeletedHandler(CurvePointNotDeletedException e) {
        logger.error(e.getMessage());
        logger.warn("Redirect to /curvePoint/list?err=notdeleted page");

        ModelAndView model = new ModelAndView("redirect:/curvePoint/list");
        model.addObject("curvePoints", curvePointService.getCurvePointLists());
        model.addObject("err", notDeleted);

        return model;
    }

    @ExceptionHandler(RatingNotFoundException.class)
    public ModelAndView RatingNotFoundHandler(RatingNotFoundException e) {
        logger.error(e.getMessage());
        logger.warn("Redirect to /rating/list?err=notfound page");

        ModelAndView model = new ModelAndView("redirect:/rating/list");
        model.addObject("ratings", ratingService.getRatingList());
        model.addObject("err", notFound);

        return model;
    }

    @ExceptionHandler(RatingNotDeletedException.class)
    public ModelAndView RatingNotDeletedHandler(RatingNotDeletedException e) {
        logger.error(e.getMessage());
        logger.warn("Redirect to /rating/list?err=notdeleted page");

        ModelAndView model = new ModelAndView("redirect:/rating/list");
        model.addObject("ratings", ratingService.getRatingList());
        model.addObject("err", notDeleted);

        return model;
    }

    @ExceptionHandler(RuleNameNotFoundException.class)
    public ModelAndView RuleNameNotFoundHandler(RuleNameNotFoundException e) {
        logger.error(e.getMessage());
        logger.warn("Redirect to /ruleName/list?err=notfound page");

        ModelAndView model = new ModelAndView("redirect:/ruleName/list");
        model.addObject("ruleNames", ruleNameService.getRuleNames());
        model.addObject("err", notFound);

        return model;
    }

    @ExceptionHandler(RuleNameNotDeletedException.class)
    public ModelAndView RuleNameNotDeletedHandler(RatingNotDeletedException e) {
        logger.error(e.getMessage());
        logger.warn("Redirect to /ruleName/list?err=notdeleted page");

        ModelAndView model = new ModelAndView("redirect:/ruleName/list");
        model.addObject("ruleNames", ruleNameService.getRuleNames());
        model.addObject("err", notDeleted);

        return model;
    }

    @ExceptionHandler(TradeNotFoundException.class)
    public ModelAndView TradeNotFoundHandler(TradeNotFoundException e) {
        logger.error(e.getMessage());
        logger.warn("Redirect to /trade/list?err=notfound page");

        ModelAndView model = new ModelAndView("redirect:/trade/list");
        model.addObject("trades", tradeService.getTradeList());
        model.addObject("err", notFound);

        return model;
    }

    @ExceptionHandler(TradeNotDeletedException.class)
    public ModelAndView TradeNameNotDeletedHandler(TradeNotDeletedException e) {
        logger.error(e.getMessage());
        logger.warn("Redirect to /trade/list?err=notdeleted page");

        ModelAndView model = new ModelAndView("redirect:/trade/list");
        model.addObject("trades", tradeService.getTradeList());
        model.addObject("err", notDeleted);

        return model;
    }
}
