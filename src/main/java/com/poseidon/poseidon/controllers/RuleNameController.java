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
import java.util.Optional;

@Controller
public class RuleNameController {
    private final static Logger logger = LogManager.getLogger(RuleNameController.class);

    @Autowired
    private RuleNameService ruleNameService;

    @RequestMapping("/ruleName/list")
    public String home(Model model)
    {
        logger.debug("Access /ruleName/list page.");
        Iterable<RuleName> ruleNames = ruleNameService.getRuleNames();

        model.addAttribute("ruleNames", ruleNames);

        return "ruleName/list";
    }

    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleName bid) {
        logger.debug("Access /ruleName/add");
        return "ruleName/add";
    }

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

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        logger.debug("Access /ruleName/update/{}", id);
        Optional<RuleName> ruleName = ruleNameService.getRuleNameById(id);
        if (ruleName.isPresent()) {
            model.addAttribute("ruleName", ruleName.get());
            return "ruleName/update";
        }

        logger.info("No data found in database for id {}", id);
        return "ruleName/list";
    }

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

    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        logger.debug("Access /ruleName/delete/{} page.", id);
        Optional<RuleName> ruleName = ruleNameService.getRuleNameById(id);

        if (ruleName.isPresent()) {
            ruleNameService.deleteRuleName(ruleName.get());
            logger.info("Rule name with id {} has been deleted.", id);
        }

        model.addAttribute("ruleNames", ruleNameService.getRuleNames());

        return "redirect:/ruleName/list";
    }
}
