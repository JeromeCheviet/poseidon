package com.poseidon.poseidon.service;

import com.poseidon.poseidon.domain.User;
import com.poseidon.poseidon.repositories.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public User getUserByUsername(String username) {
        logger.debug("Get User with username : {}", username);
        return userRepository.findByUsername(username);
    }
}
