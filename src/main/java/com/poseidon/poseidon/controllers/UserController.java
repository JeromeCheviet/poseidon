package com.poseidon.poseidon.controllers;

import com.poseidon.poseidon.domain.User;
import com.poseidon.poseidon.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

/**
 * Class which manage the User pages. Multiple API are present to see, add, update and delete Users.
 * Only accounts with the 'ADMIN' authority can access to these pages.
 */
@Controller
public class UserController {
    private final static Logger logger = LogManager.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    /**
     * Method to loading the main page of User operations.
     *
     * @param model An object which contain all users.
     * @return String The URI to load.
     */
    @RequestMapping("/user/list")
    public String home(Model model) {
        logger.debug("Access /user/list page");
        Iterable<User> users = userService.getUserList();

        model.addAttribute("users", users);
        return "user/list";
    }

    /**
     * Method to loading adding User form.
     *
     * @param user Object which contain an empty user.
     * @return String the URI to load
     */
    @GetMapping("/user/add")
    public String addUser(User user) {
        logger.debug("Access /user/add page");
        return "user/add";
    }

    /**
     * Method to post a new user
     * <br>
     * If no error in form, the password is encrypting and the new user is saving in database
     *
     * @param user   An object which contain a new user
     * @param result The result of validating user
     * @param model  An object which contain the list of all users.
     * @return String If the new user is valid, redirect to user/list, else load the adding form.
     */
    @PostMapping("/user/validate")
    public String validate(@Valid User user, BindingResult result, Model model) {
        logger.debug("Post validate user form to add new user");
        if (!result.hasErrors()) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));

            userService.addUser(user);
            logger.info("New user added");

            model.addAttribute("users", userService.getUserList());

            return "redirect:/user/list";
        }
        logger.info("Add form not valid");
        return "user/add";
    }

    /**
     * Method to load the updating form.
     *
     * @param id    The user ID to update
     * @param model Object which contain the user to update
     * @return String If user exist into database, launch form with data of User. Else, load the user page.
     */
    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        logger.debug("Access /user/update/{} page", id);

        User user = userService.getUserById(id);

        if (user.getId() == id) {
            user.setPassword("");
            model.addAttribute("user", user);
            return "user/update";
        }

        model.addAttribute("users", userService.getUserList());
        return "user/list";
    }

    /**
     * Method to post an updated user
     * <br>
     * If no error in form, the password is encrypting, the User ID is set in the object
     * and the updated user is saving in database
     *
     * @param id     The user ID to update
     * @param user   Object which contain the user to update
     * @param result The result of validating usre
     * @param model  An object which contain the list of all users.
     * @return String If the updated user is valid, redirect to user/list, else load the updating form.
     */
    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid User user,
                             BindingResult result, Model model) {
        logger.debug("Post a valid form to update an existing user");
        if (result.hasErrors()) {
            logger.info("Update form not valid");
            return "user/update";
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        user.setId(id);

        userService.updateUser(id, user);
        logger.info("User with id {} has been updated", id);
        model.addAttribute("users", userService.getUserList());

        return "redirect:/user/list";
    }

    /**
     * Method to deleting a user
     * <br>
     * If user exist, it will be deleted, else a Data not found exception is throw.
     *
     * @param id    The user ID to update
     * @param model An object which contain the list of all users.
     * @return String to redirect to user page.
     */
    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
        logger.debug("Access /user/delete/{} page", id);

        User user = userService.getUserById(id);
        if (user.getId() == id) {
            userService.deleteUser(user);
            logger.info("User with id {} has been deleted.", id);
        }

        model.addAttribute("users", userService.getUserList());

        return "redirect:/user/list";
    }
}
