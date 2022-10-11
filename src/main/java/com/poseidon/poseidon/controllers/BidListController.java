package com.poseidon.poseidon.controllers;

import com.poseidon.poseidon.domain.BidList;
import com.poseidon.poseidon.service.BidListService;
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
public class BidListController {
    private static final Logger logger = LogManager.getLogger(BidListController.class);

    @Autowired
    private BidListService bidListService;

    @RequestMapping("/bidList/list")
    public String home(Model model) {
        logger.debug("Access /bidList/list page.");
        Iterable<BidList> bidLists = bidListService.getBidLists();

        model.addAttribute("bidLists", bidLists);

        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid) {
        logger.debug("Access /bidList/add page.");
        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bid, BindingResult result, Model model) {
        logger.debug("Post validate form to add new bid");
        if (result.hasErrors()){
            logger.info("Add form not valid !");
            return "bidList/add";
        }

        bidListService.addBidList(bid);
        logger.info("New bid added.");
        model.addAttribute("bidlists", bidListService.getBidLists());

        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        logger.debug("Access /biList/update/{} page.", id);
        Optional<BidList>bidList = bidListService.getBidlistById(id);
        if (bidList.isPresent()) {
            model.addAttribute("bidList", bidList.get());
            return "bidList/update";
        }
        logger.info("No data found in database for id {}", id);
        return "bidList/list";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
                            BindingResult result, Model model) {
        logger.debug("Post validate form to update an existing bid.");
        if (result.hasErrors()) {
            logger.info("Update form not valid !");
            return "bidList/update";
        }

        bidListService.updateBidList(id, bidList);
        logger.info("Bid with id {} have been updated", id);
        model.addAttribute("bidlists", bidListService.getBidLists());

        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        logger.debug("Access /bidList/delete/{} page", id);
        Optional<BidList> bidList = bidListService.getBidlistById(id);

        if (bidList.isPresent()) {
            bidListService.deleteBidList(bidList.get());
        }

        model.addAttribute("bidLists", bidListService.getBidLists());

        return "redirect:/bidList/list";
    }
}
