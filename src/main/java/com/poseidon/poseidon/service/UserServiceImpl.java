package com.poseidon.poseidon.service;

import com.poseidon.poseidon.domain.User;
import com.poseidon.poseidon.exception.DataNotDeletedException;
import com.poseidon.poseidon.exception.DataNotFoundException;
import com.poseidon.poseidon.repositories.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Class to interact with User table data
 */
@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LogManager.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<User> getUserList() {
        logger.debug("Get all users");

        return userRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User getUserByUsername(String username) {
        logger.debug("Get User with username : {}", username);
        return userRepository.findByUsername(username);
    }

    /**
     * {@inheritDoc}
     *
     * <br>If no User is found, a private exception is throw.
     */
    @Override
    public User getUserById(int userId) {
        logger.debug("Get user with id : {}", userId);

        return userRepository.findById(userId).orElseThrow(
                () -> new DataNotFoundException("User with id " + userId + " not found")
        );
    }

    @Override
    public void deleteUser(User user) {
        int id = user.getId();
        logger.debug("Delete user with id : {}", id);

        userRepository.delete(user);

        Optional<User> deletedUser = userRepository.findById(id);
        if (deletedUser.isPresent())
            throw new DataNotDeletedException("User with id " + id + " has not been deleted");
    }

    @Override
    public void addUser(User user) {
        logger.debug("Add new user : {}", user.getUsername());

        userRepository.save(user);
    }

    @Override
    public void updateUser(int existingUserId, User user) {
        logger.debug("Updating user with id {} and username {}", existingUserId, user.getUsername());
        user.setId(existingUserId);
        userRepository.save(user);
    }
}
