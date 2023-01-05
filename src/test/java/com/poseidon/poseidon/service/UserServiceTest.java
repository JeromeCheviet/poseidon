package com.poseidon.poseidon.service;

import com.poseidon.poseidon.domain.User;
import com.poseidon.poseidon.exception.DataNotDeletedException;
import com.poseidon.poseidon.exception.DataNotFoundException;
import com.poseidon.poseidon.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

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
    void testGetUsers() {
        List<User> expectedUserList = new ArrayList<>();
        expectedUserList.add(expectedUser);

        when(userRepository.findAll()).thenReturn(expectedUserList);

        Iterable<User> actualUserList = userService.getUserList();

        assertEquals(expectedUserList, actualUserList);
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testGetUserByUsername() {
        String username = "john";

        when(userRepository.findByUsername(username)).thenReturn(expectedUser);

        User actualUser = userService.getUserByUsername(username);

        assertEquals(username, actualUser.getUsername());
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    void testGetUserById() {
        when(userRepository.findById(1)).thenReturn(Optional.ofNullable(expectedUser));

        User actualUser = userService.getUserById(1);

        assertEquals(expectedUser, actualUser);
        verify(userRepository, times(1)).findById(1);

    }

    @Test
    void testUserById_whenEmpty_returnException() {
        when(userRepository.findById(200)).thenReturn(Optional.empty());

        Throwable exception = assertThrows(DataNotFoundException.class, () -> {
            userService.getUserById(200);
        });

        assertEquals("User with id 200 not found", exception.getMessage());
        verify(userRepository, times(1)).findById(200);
    }

    @Test
    void testAddUser() {
        when(userRepository.save(any(User.class))).thenReturn(expectedUser);

        userService.addUser(expectedUser);

        verify(userRepository, times(1)).save(expectedUser);
    }

    @Test
    void testUpdateUser() {
        expectedUser.setRole("USER");
        int actualUserId = 1;
        when(userRepository.save(expectedUser)).thenReturn(expectedUser);

        userService.updateUser(actualUserId, expectedUser);

        assertEquals("USER", expectedUser.getRole());
        assertEquals(actualUserId, expectedUser.getId());
        verify(userRepository, times(1)).save(expectedUser);
    }

    @Test
    void testDeleteUser() {
        doNothing().when(userRepository).delete(expectedUser);
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        assertDoesNotThrow(
                () -> userService.deleteUser(expectedUser)
        );

        verify(userRepository, times(1)).delete(expectedUser);
        verify(userRepository, times(1)).findById(1);
    }

    @Test
    void testDeleteUser_whenUserIsPresent_returnException() {
        doNothing().when(userRepository).delete(expectedUser);
        when(userRepository.findById(1)).thenReturn(Optional.ofNullable(expectedUser));

        Throwable exception = assertThrows(DataNotDeletedException.class, () -> {
            userService.deleteUser(expectedUser);
        });

        assertEquals("User with id 1 has not been deleted", exception.getMessage());
        verify(userRepository, times(1)).delete(expectedUser);
        verify(userRepository, times(1)).findById(1);
    }
}
