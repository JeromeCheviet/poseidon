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

/**
 * Class which manage the BidList pages. Multiple API are present to see, add, update and delete Bids.
 */
@Controller
public class BidListController {
    private static final Logger logger = LogManager.getLogger(BidListController.class);

    @Autowired
    private BidListService bidListService;

    /**
     * Method to loading the main page of Bid operations.
     *
     * @param model An object which contain all bids.
     * @return String The URI to load.
     */
    @RequestMapping("/bidList/list")
    public String home(Model model) {
        logger.debug("Access /bidList/list page.");
        Iterable<BidList> bidLists = bidListService.getBidLists();

        model.addAttribute("bidLists", bidLists);

        return "bidList/list";
    }

    /**
     * Method to loading adding Bid form.
     *
     * @param bid Object which contain an empty bid.
     * @return String the URI to load
     */
    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid) {
        logger.debug("Access /bidList/add page.");
        return "bidList/add";
    }

    /**
     * Method to post a new Bid
     *
     * @param bid    An object which contain a new bid
     * @param result The result of validating Bid
     * @param model  An object which contain the list of all bids.
     * @return String If the new bid is valid, redirect to bidList/list, else load the adding form.
     */
    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bid, BindingResult result, Model model) {
        logger.debug("Post validate form to add new bid");
        if (result.hasErrors()) {
            logger.info("Add form not valid !");
            return "bidList/add";
        }

        bidListService.addBidList(bid);
        logger.info("New bid added.");
        model.addAttribute("bidlists", bidListService.getBidLists());

        return "redirect:/bidList/list";
    }

    /**
     * Method to load the updating form.
     *
     * @param id    The bid ID to update
     * @param model Object which contain the bid to update
     * @return String If bid exist into database, launch form with data of Bid. Else, load the Bid List page.
     */
    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        logger.debug("Access /biList/update/{} page.", id);

        BidList bidList = bidListService.getBidlistById(id);

        if (bidList.getBidListId() == id) {
            model.addAttribute("bidList", bidList);
            return "bidList/update";
        }

        model.addAttribute("bidLists", bidListService.getBidLists());
        return "bidList/list";
    }

    /**
     * Method to post an updated bid
     *
     * @param id      The bid ID to update
     * @param bidList Object which contain the bid to update
     * @param result  The result of validating Bid
     * @param model   An object which contain the list of all bids.
     * @return String If the updated bid is valid, redirect to bidList/list, else load the updating form.
     */
    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
                            BindingResult result, Model model) {
        logger.debug("Post validate form to update an existing bid.");
        if (result.hasErrors()) {
            logger.info("Update form not valid !");
            return "bidList/update";
        }

        bidListService.updateBidList(id, bidList);
        logger.info("Bid with id {} has been updated", id);
        model.addAttribute("bidlists", bidListService.getBidLists());

        return "redirect:/bidList/list";
    }

    /**
     * Method to deleting a Bid
     * <br>
     * If bid exist, it will send to BidListService class to be deleted.
     *
     * @param id    The bid ID to update
     * @param model An object which contain the list of all bids.
     * @return String to redirect to Bid List page.
     */
    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        logger.debug("Access /bidList/delete/{} page", id);

        BidList bidList = bidListService.getBidlistById(id);
        if (bidList.getBidListId() == id) {
            bidListService.deleteBidList(bidList);
            logger.info("Bid list with id {} has been deleted.", id);
        }

        model.addAttribute("bidLists", bidListService.getBidLists());

        return "redirect:/bidList/list";
    }
}
