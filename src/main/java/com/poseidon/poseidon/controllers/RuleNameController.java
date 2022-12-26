package com.poseidon.poseidon.controllers;

import com.poseidon.poseidon.domain.RuleName;
import com.poseidon.poseidon.service.RuleNameService;
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
 * Class which manage the RuleName pages. Multiple API are present to see, add, update and delete rule names.
 */
@Controller
public class RuleNameController {
    private final static Logger logger = LogManager.getLogger(RuleNameController.class);

    @Autowired
    private RuleNameService ruleNameService;

    /**
     * Method to loading the main page of Rule Name operations.
     *
     * @param model An object which contain all rule names.
     * @return String The URI to load.
     */
    @RequestMapping("/ruleName/list")
    public String home(Model model) {
        logger.debug("Access /ruleName/list page.");
        Iterable<RuleName> ruleNames = ruleNameService.getRuleNames();

        model.addAttribute("ruleNames", ruleNames);

        return "ruleName/list";
    }

    /**
     * Method to loading adding Rule names form.
     *
     * @param ruleName Object which contain an empty ruleName.
     * @return String the URI to load
     */
    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleName ruleName) {
        logger.debug("Access /ruleName/add");
        return "ruleName/add";
    }

    /**
     * Method to post a new Rule name
     *
     * @param ruleName An object which contain a new ruleName
     * @param result   The result of validating Rule name
     * @param model    An object which contain the list of all rule names.
     * @return String If the new rule name is valid, redirect to ruleName/list, else load the adding form.
     */
    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
        logger.debug("Post valid form to add new rule name");
        if (result.hasErrors()) {
            logger.info("Add form not valid !");
            return "ruleName/add";
        }

        ruleNameService.addRuleName(ruleName);
        logger.info("New rule name added");
        model.addAttribute("rulenames", ruleNameService.getRuleNames());

        return "redirect:/ruleName/list";
    }

    /**
     * Method to load the updating form.
     *
     * @param id    The ruleName ID to update
     * @param model Object which contain the rule name to update
     * @return String If rule name exist into database, launch form with data of Rule name. Else, load the ruleName List page.
     */
    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        logger.debug("Access /ruleName/update/{}", id);
        RuleName ruleName = ruleNameService.getRuleNameById(id);
        if (ruleName.getId() == id) {
            model.addAttribute("ruleName", ruleName);
            return "ruleName/update";
        }

        logger.info("No data found in database for id {}", id);
        return "ruleName/list";
    }

    /**
     * Method to post an updated rule name
     *
     * @param id       The ruleName ID to update
     * @param ruleName Object which contain the rule name to update
     * @param result   The result of validating Rule name
     * @param model    An object which contain the list of all rule names.
     * @return String If the updated rule name is valid, redirect to ruleName/list, else load the updating form.
     */
    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
                                 BindingResult result, Model model) {
        logger.debug("Post valid form to update existing rule name");
        if (result.hasErrors()) {
            logger.info("Update form not valid !");
            return "ruleName/update";
        }

        ruleNameService.updateRuleName(id, ruleName);
        logger.info("Rule name with {} has been updated.", id);
        model.addAttribute("rulenames", ruleNameService.getRuleNames());

        return "redirect:/ruleName/list";
    }

    /**
     * Method to deleting a Rule name
     * <br>
     * If rule name exist, it will send to RuleNameService class to be deleted.
     *
     * @param id    The rule name ID to update
     * @param model An object which contain the list of all rule names.
     * @return String to redirect to Rule name List page.
     */
    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        logger.debug("Access /ruleName/delete/{} page.", id);
        RuleName ruleName = ruleNameService.getRuleNameById(id);

        if (ruleName.getId() == id) {
            ruleNameService.deleteRuleName(ruleName);
            logger.info("Rule name with id {} has been deleted.", id);
        }

        model.addAttribute("ruleNames", ruleNameService.getRuleNames());

        return "redirect:/ruleName/list";
    }
}
