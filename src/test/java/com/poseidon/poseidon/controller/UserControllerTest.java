package com.poseidon.poseidon.controller;

import com.poseidon.poseidon.domain.BidList;
import com.poseidon.poseidon.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = {"/schema.sql", "/insert_data.sql"})
@Sql(scripts = "/delete_data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UserControllerTest {

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
    void getUserList_shouldReturnUnauthenticated() throws Exception {
        mvc.perform(get("/user/list"))
                .andDo(print())
                .andExpect(unauthenticated());
    }

    @WithMockUser("user")
    @Test
    void getUserList_shouldReturnIsForbidden() throws Exception {
        mvc.perform(get("/user/list"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @WithMockUser(value = "admin", authorities = "ADMIN")
    @Test
    void getUserList_shouldReturnIsOk() throws Exception {
        mvc.perform(get("/user/list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("user/list"));
    }

    @WithMockUser(value = "admin", authorities = "ADMIN")
    @Test
    void getUserAdd_shouldReturnIsOk() throws Exception {
        mvc.perform(get("/user/add"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser(value = "admin", authorities = "ADMIN")
    @Test
    void getUserUpdate_shouldReturnIsOk() throws Exception {
        mvc.perform(get("/user/update/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"));
    }

    @WithMockUser(value = "admin", authorities = "ADMIN")
    @Test
    void getUserUpdate_shouldReturnIsNotFound() throws Exception {
        mvc.perform(get("/user/update/100"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(view().name("404"));
    }

    @WithMockUser(value = "admin", authorities = "ADMIN")
    @Test
    void getUserDelete_shouldReturnIsFound() throws Exception {
        mvc.perform(get("/user/delete/2"))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/user/list"));
    }

    @WithMockUser(value = "admin", authorities = "ADMIN")
    @Test
    void getUserDelete_shouldReturnIsNotFound() throws Exception {
        mvc.perform(get("/user/delete/200"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(view().name("404"));
    }

    @WithMockUser(value = "admin", authorities = "ADMIN")
    @Test
    void postUserValidate_shouldReturnIsFound() throws Exception {
        User user = new User();
        user.setUsername("newuser");
        user.setFullname("New User");
        user.setPassword("MyUs3rP@sswOrd01!");
        user.setRole("ADMIN");

        mvc.perform(post("/user/validate")
                        .flashAttr("user", user))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/user/list"));
    }

    @WithMockUser(value = "admin", authorities = "ADMIN")
    @Test
    void postUserValidate_shouldRedirect() throws Exception {
        User user = new User();
        user.setUsername("newbaduser");

        mvc.perform(post("/user/validate")
                        .flashAttr("user", user))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"));
    }

    @WithMockUser(value = "admin", authorities = "ADMIN")
    @Test
    void postUserUpdate_shouldReturnIsFound() throws Exception {
        User user = new User();
        user.setId(1);
        user.setUsername("user1");
        user.setFullname("User 1");
        user.setPassword("MyUs3rP@sswOrd01");
        user.setRole("USER");

        mvc.perform(post("/user/update/1")
                        .flashAttr("user", user))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/user/list"));
    }

    @WithMockUser(value = "admin", authorities = "ADMIN")
    @Test
    void postUserUpdate_shouldRedirect() throws Exception {
        BidList bidList = new BidList();
        bidList.setBidListId(1);

        mvc.perform(post("/bidList/update/1")
                        .flashAttr("bidList", bidList))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/update"));
    }

}
