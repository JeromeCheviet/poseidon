package com.poseidon.poseidon.controller;

import com.poseidon.poseidon.domain.Rating;
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
public class RatingControllerTest {

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
    void getRatingList_shouldReturnUnauthenticated() throws Exception {
        mvc.perform(get("/rating/list"))
                .andDo(print())
                .andExpect(unauthenticated());
    }

    @WithMockUser("user")
    @Test
    void getRatingList_shouldReturnIsOk() throws Exception {
        mvc.perform(get("/rating/list"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser("user")
    @Test
    void getRatingAdd_shouldReturnIsOk() throws Exception {
        mvc.perform(get("/rating/add"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser("user")
    @Test
    void getRatingUpdate_shouldReturnIsOk() throws Exception {
        mvc.perform(get("/rating/update/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("rating/update"));
    }

    @WithMockUser("user")
    @Test
    void getRatingUpdate_shouldReturnIsNotFound() throws Exception {
        mvc.perform(get("/rating/update/100"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(view().name("404"));
    }

    @WithMockUser("user")
    @Test
    void getRatingDelete_shouldReturnIsFound() throws Exception {
        mvc.perform(get("/rating/delete/2"))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/rating/list"));
    }

    @WithMockUser("user")
    @Test
    void getRatingDelete_shouldReturnIsNotFound() throws Exception {
        mvc.perform(get("/rating/delete/200"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(view().name("404"));
    }

    @WithMockUser("user")
    @Test
    void postRatingValidate_shouldReturnIsFound() throws Exception {
        Rating rating = new Rating();
        rating.setMoodysRating("New Moodys");
        rating.setFitchRating("New Fitch");
        rating.setSandPRating("New Sand");
        rating.setOrderNumber(90);

        mvc.perform(post("/rating/validate")
                        .flashAttr("rating", rating))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/rating/list"));
    }

    @WithMockUser("user")
    @Test
    void postRatingValidate_shouldRedirect() throws Exception {
        Rating rating = new Rating();
        rating.setMoodysRating("New Bad Moodys");

        mvc.perform(post("/rating/validate")
                        .flashAttr("rating", rating))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"));
    }

    @WithMockUser("user")
    @Test
    void postRatingUpdate_shouldReturnIsFound() throws Exception {
        Rating rating = new Rating();
        rating.setId(1);
        rating.setMoodysRating("update moodys 1");
        rating.setFitchRating("fitch 1");
        rating.setSandPRating("sand 1");
        rating.setOrderNumber(1);

        mvc.perform(post("/rating/update/1")
                        .flashAttr("rating", rating))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/rating/list"));
    }

    @WithMockUser("user")
    @Test
    void postBidListUpdate_shouldRedirect() throws Exception {
        Rating rating = new Rating();
        rating.setId(1);

        mvc.perform(post("/rating/update/1")
                        .flashAttr("rating", rating))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("rating/update"));
    }

}
