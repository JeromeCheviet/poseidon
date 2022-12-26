package com.poseidon.poseidon.service;

import com.poseidon.poseidon.domain.User;

/**
 * Interface link to User operations
 */
public interface UserService {

    /**
     * Get one User by his username
     *
     * @param username String Username to find
     * @return An object which contain the user
     */
    User getUserByUsername(String username);
}
