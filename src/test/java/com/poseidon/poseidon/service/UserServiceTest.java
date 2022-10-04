package com.poseidon.poseidon.service;

import com.poseidon.poseidon.domain.User;
import com.poseidon.poseidon.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService = new UserServiceImpl();

    @Mock
    private UserRepository userRepository;

    @Mock
    private User expectedUser;

    @BeforeEach
    private void setUp() {
        int expectedUserId = 1;
        String expectedUsername = "john";
        String expectedPassword = "123456";
        String expectedFullname = "John";
        String expectedRole = "ADMIN";

        expectedUser = new User();
        expectedUser.setId(expectedUserId);
        expectedUser.setUsername(expectedUsername);
        expectedUser.setPassword(expectedPassword);
        expectedUser.setFullname(expectedFullname);
        expectedUser.setRole(expectedRole);

    }

    @Test
    void testGetUserByUsername() {
        String username = "john";

        when(userRepository.findByUsername(username)).thenReturn(expectedUser);

        User actualUser = userService.getUserByUsername(username);

        assertEquals(username, actualUser.getUsername());
        verify(userRepository, times(1)).findByUsername(username);
    }

}
