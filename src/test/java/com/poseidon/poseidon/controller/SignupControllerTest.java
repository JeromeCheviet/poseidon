package com.poseidon.poseidon.controller;

import com.poseidon.poseidon.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = "/schema.sql")
@Sql(scripts = "/delete_data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class SignupControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    private void setUp() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    void getSignup_shouldReturnIsOk() throws Exception {
        mvc.perform(get("/signup"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("signup"));
    }

    @Test
    void postValidateSignup_shouldReturnIsFound() throws Exception {
        User user = new User();
        user.setUsername("newuser");
        user.setFullname("New User");
        user.setPassword("MyUs3rP@sswOrd01!");
        user.setRole("USER");

        mvc.perform(post("/signup/validate")
                        .flashAttr("user", user))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/bidList/list"));
    }

    @Test
    void postValidateSignup_shouldRedirect() throws Exception {
        User user = new User();
        user.setUsername("newuser");
        user.setFullname("New User");
        user.setPassword("weak");
        user.setRole("USER");

        mvc.perform(post("/signup/validate")
                        .flashAttr("user", user))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("signup"));
    }

}
