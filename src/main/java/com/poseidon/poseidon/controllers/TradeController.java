package com.poseidon.poseidon.controllers;

import com.poseidon.poseidon.domain.Trade;
import com.poseidon.poseidon.service.TradeService;
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
public class TradeController {
    private static final Logger logger = LogManager.getLogger(TradeController.class);

    @Autowired
    private TradeService tradeService;

    @RequestMapping("/trade/list")
    public String home(Model model)
    {
        logger.debug("Access /trade/list page.");
        Iterable<Trade> trades = tradeService.getTradeList();

        model.addAttribute("trades", trades);

        return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addUser(Trade bid) {
        logger.debug("Access /trade/add page.");
        return "trade/add";
    }

    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) {
        logger.debug("Post valid form to add new trade");
        if (result.hasErrors()) {
            logger.info("Add form not valid !");
            return "trade/add";
        }

        tradeService.addTrade(trade);
        logger.info("New trade added");
        model.addAttribute("trades", tradeService.getTradeList());

        return "redirect:/trade/list";
    }

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        logger.debug("Access /trade/update/{} page.", id);
        Trade trade = tradeService.getTradeById(id);
        if (trade.getTradeId() == id) {
            model.addAttribute("trade", trade);
            return "trade/update";
        }
        logger.info("No data found in database for id {}", id);
        return "trade/list";
    }

    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
                             BindingResult result, Model model) {
        logger.debug("Post valid form to update an existing trade");
        if (result.hasErrors()) {
            logger.info("Update form not valid !");
            return "trade/update";
        }

        tradeService.updateTrade(id, trade);
        logger.info("Trade with id {} has been updated", id);
        model.addAttribute("trades", tradeService.getTradeList());

        return "redirect:/trade/list";
    }

    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        logger.debug("Access /trade/delete/{} page.", id);
        Trade trade = tradeService.getTradeById(id);

        if (trade.getTradeId() == id) {
            tradeService.deleteTrade(trade);
            logger.info("Trade with id {} has been deleted", id);
        }

        model.addAttribute("trades", tradeService.getTradeList());

        return "redirect:/trade/list";
    }
}
