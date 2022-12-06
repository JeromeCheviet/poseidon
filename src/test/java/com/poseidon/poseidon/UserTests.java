package com.poseidon.poseidon;

import com.poseidon.poseidon.domain.User;
import com.poseidon.poseidon.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Sql(scripts = "/schema.sql")
class UserTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    void userTest() {
        User user = new User();
        user.setUsername("user");
        user.setFullname("User Full Name");
        user.setPassword("myP@sswd1");
        user.setRole("USER");

        // Save
        user = userRepository.save(user);
        assertNotNull(user.getId());
        assertTrue(user.getPassword().equals("myP@sswd1"));

        // Update
        user.setPassword("mynewP@sswd2");
        user = userRepository.save(user);
        assertTrue(user.getPassword().equals("mynewP@sswd2"));

        // Find
        Iterable<User> users = userRepository.findAll();
        List<User> listUser = new ArrayList<>();
        users.forEach(listUser::add);
        assertTrue(listUser.size() > 0);

        // Delete
        Integer id = user.getId();
        userRepository.delete(user);
        Optional<User> userList = userRepository.findById(id);
        assertFalse(userList.isPresent());
    }
}
