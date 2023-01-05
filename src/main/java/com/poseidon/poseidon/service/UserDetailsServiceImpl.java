package com.poseidon.poseidon.service;

import com.poseidon.poseidon.domain.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

/**
 * Class to search login user in database
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger logger = LogManager.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private UserService userService;

    /**
     * Method which search if user exist in database. If it's true, the username, password and authority
     * is put in an object use by Spring Security.
     *
     * @param username String Username to search
     * @return User's connect information in an object
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.debug("Login with username {}", username);
        User user = Optional.ofNullable(userService.getUserByUsername(username))
                .orElseThrow(() -> new UsernameNotFoundException("User not found : " + username));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Arrays.asList(new SimpleGrantedAuthority(user.getRole()))
        );
    }

}
