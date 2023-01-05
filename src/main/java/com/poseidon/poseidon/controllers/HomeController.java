package com.poseidon.poseidon.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * class which manage the home page.
 */
@Controller
public class HomeController {

    /**
     * Method to loading the root page
     *
     * @param model
     * @return String The URI to load
     */
    @RequestMapping("/")
    public String home(Model model) {
        return "home";
    }

    /**
     * Method to loading the bidList page
     *
     * @param model
     * @return String with redirect to bidList/list page.
     */
    @RequestMapping("/admin/home")
    public String adminHome(Model model) {
        return "redirect:/bidList/list";
    }


}
