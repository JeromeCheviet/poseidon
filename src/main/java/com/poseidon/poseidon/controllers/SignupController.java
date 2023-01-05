package com.poseidon.poseidon.controllers;

import com.poseidon.poseidon.domain.User;
import com.poseidon.poseidon.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

/**
 * Class which manage the Signup page.
 */
@Controller
public class SignupController {
    private static final Logger logger = LogManager.getLogger(SignupController.class);

    @Autowired
    private UserService userService;

    /**
     * Method to laoding signup form
     *
     * @param user Object which contain an empty user
     * @return String the URI to load
     */
    @GetMapping("/signup")
    public String signupUser(User user) {
        logger.debug("Access /signup page");
        return "signup";
    }

    /**
     * Method to post a new user
     * <br>
     * The password is encoding and to be sure everybody can not be set ADMIN role, the role is force
     * to be 'USER' before saving user in database.
     *
     * @param user   An object which contain a new user
     * @param result The result of validating user
     * @return String If the new user is valide, redirect to bidList/list, else load signup form.
     */
    @PostMapping("/signup/validate")
    private String validateSignup(@Valid User user, BindingResult result) {
        logger.debug("Post validate user");
        if (result.hasErrors()) {
            logger.debug("Form not valid");
            return "signup";
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole("USER");

        userService.addUser(user);
        logger.info("User {} has been added", user.getUsername());

        return "redirect:/bidList/list";
    }
}
