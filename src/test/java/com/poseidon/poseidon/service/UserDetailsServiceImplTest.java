package com.poseidon.poseidon.service;

import com.poseidon.poseidon.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService = new UserDetailsServiceImpl();

    @Mock
    private UserService userService;

    @Mock
    private User expectedUser;

    @BeforeEach
    private void setUp() {
        int expectedId = 100;
        String expectedUsername = "john";
        String expectedPassword = "password";
        String expectedFullname = "John Doe";
        String expectedRole = "USER";

        expectedUser = new User();
        expectedUser.setId(expectedId);
        expectedUser.setUsername(expectedUsername);
        expectedUser.setPassword(expectedPassword);
        expectedUser.setFullname(expectedFullname);
        expectedUser.setRole(expectedRole);
    }

    @Test
    void testLoadByUsername_withExistingUser() {
        String username = expectedUser.getUsername();
        when(userService.getUserByUsername(username)).thenReturn(expectedUser);

        UserDetails actualUserDetails = userDetailsService.loadUserByUsername(username);

        assertNotNull(actualUserDetails);
        assertEquals(expectedUser.getUsername(), actualUserDetails.getUsername());
        verify(userService, times(1)).getUserByUsername(username);

    }

    @Test
    void testLoadByUsername_returnException_whenUserNotExist() {
        String username = "Jade";
        User expectedEmptyUser = null;
        when(userService.getUserByUsername(username)).thenThrow(UsernameNotFoundException.class);

        assertThrows(UsernameNotFoundException.class, () -> {
            userService.getUserByUsername(username);
        });

        verify(userService, times(1)).getUserByUsername(username);
    }

}