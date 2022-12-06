package com.poseidon.poseidon;

import com.poseidon.poseidon.controllers.LoginController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ApplicationTests {

    @Autowired
    private LoginController controller;

    @Test
    void contextLoad() {
        assertThat(controller).isNotNull();
    }
}
