package com.poseidon.poseidon.controllers;

import com.poseidon.poseidon.domain.User;
import com.poseidon.poseidon.repositories.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class SignupController {
    private static final Logger logger = LogManager.getLogger(SignupController.class);

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/signup")
    public String signupUser(User user) {
        logger.debug("Access /signup page");
        return "signup";
    }

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

        userRepository.save(user);
        logger.info("User {} has been added", user.getUsername());

        return "bidList/list";
    }
}
