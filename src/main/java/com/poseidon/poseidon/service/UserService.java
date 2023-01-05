package com.poseidon.poseidon.service;

import com.poseidon.poseidon.domain.User;

/**
 * Interface link to User operations
 */
public interface UserService {

    /**
     * Get all users
     *
     * @return an iterable list contains all users
     */
    Iterable<User> getUserList();

    /**
     * Get one User by his username
     *
     * @param username String Username to find
     * @return An object which contain the user
     */
    User getUserByUsername(String username);

    /**
     * Get one User by his ID.
     *
     * @param userId int ID number
     * @return An object which contain the User.
     */
    User getUserById(int userId);

    /**
     * Delete one User
     *
     * @param user An object which contain the User to delete
     */
    void deleteUser(User user);

    /**
     * Add one User
     *
     * @param user An object which contain the User to add
     */
    void addUser(User user);

    /**
     * Update one User
     *
     * @param existingUserId Int ID number of User to update
     * @param user           An object which contain User's data to update.
     */
    void updateUser(int existingUserId, User user);

}
