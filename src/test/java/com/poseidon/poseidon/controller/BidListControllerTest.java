package com.poseidon.poseidon.controller;

import com.poseidon.poseidon.domain.BidList;
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
public class BidListControllerTest {

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
    void getBidListList_shouldReturnUnauthenticated() throws Exception {
        mvc.perform(get("/bidList/list"))
                .andDo(print())
                .andExpect(unauthenticated());
    }

    @WithMockUser("user")
    @Test
    void getBidListList_shouldReturnIsOk() throws Exception {
        mvc.perform(get("/bidList/list"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser("user")
    @Test
    void getBidListAdd_shouldReturnIsOk() throws Exception {
        mvc.perform(get("/bidList/add"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser("user")
    @Test
    void getBidListUpdate_shouldReturnIsOk() throws Exception {
        mvc.perform(get("/bidList/update/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/update"));
    }

    @WithMockUser("user")
    @Test
    void getBidListUpdate_shouldReturnIsNotFound() throws Exception {
        mvc.perform(get("/bidList/update/100"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(view().name("404"));
    }

    @WithMockUser("user")
    @Test
    void getBidListDelete_shouldReturnIsFound() throws Exception {
        mvc.perform(get("/bidList/delete/2"))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/bidList/list"));
    }

    @WithMockUser("user")
    @Test
    void getBidListDelete_shouldReturnIsNotFound() throws Exception {
        mvc.perform(get("/bidList/delete/200"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(view().name("404"));
    }

    @WithMockUser("user")
    @Test
    void postBidListValidate_shouldReturnIsFound() throws Exception {
        BidList bidList = new BidList();
        bidList.setAccount("New Account");
        bidList.setType("New Type");
        bidList.setBidQuantity(15d);

        mvc.perform(post("/bidList/validate")
                        .flashAttr("bidList", bidList))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/bidList/list"));
    }

    @WithMockUser("user")
    @Test
    void postBidListValidate_shouldRedirect() throws Exception {
        BidList bidList = new BidList();
        bidList.setAccount("New Bad Account");

        mvc.perform(post("/bidList/validate")
                        .flashAttr("bidList", bidList))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"));
    }

    @WithMockUser("user")
    @Test
    void postBidListUpdate_shouldReturnIsFound() throws Exception {
        BidList bidList = new BidList();
        bidList.setBidListId(1);
        bidList.setAccount("account 1");
        bidList.setType("type 1");
        bidList.setBidQuantity(25d);

        mvc.perform(post("/bidList/update/1")
                        .flashAttr("bidList", bidList))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/bidList/list"));
    }

    @WithMockUser("user")
    @Test
    void postBidListUpdate_shouldRedirect() throws Exception {
        BidList bidList = new BidList();
        bidList.setBidListId(1);

        mvc.perform(post("/bidList/update/1")
                        .flashAttr("bidList", bidList))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/update"));
    }

}
