package com.poseidon.poseidon.controller;

import com.poseidon.poseidon.domain.BidList;
import com.poseidon.poseidon.domain.Trade;
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
public class TradeControllerTest {

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
    void getTradeList_shouldReturnUnauthenticated() throws Exception {
        mvc.perform(get("/trade/list"))
                .andDo(print())
                .andExpect(unauthenticated());
    }

    @WithMockUser("user")
    @Test
    void getTradeList_shouldReturnIsOk() throws Exception {
        mvc.perform(get("/trade/list"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser("user")
    @Test
    void getTradeAdd_shouldReturnIsOk() throws Exception {
        mvc.perform(get("/trade/add"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser("user")
    @Test
    void getTradeUpdate_shouldReturnIsOk() throws Exception {
        mvc.perform(get("/trade/update/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("trade/update"));
    }

    @WithMockUser("user")
    @Test
    void getTradeUpdate_shouldReturnIsNotFound() throws Exception {
        mvc.perform(get("/trade/update/100"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(view().name("404"));
    }

    @WithMockUser("user")
    @Test
    void getTradeDelete_shouldReturnIsFound() throws Exception {
        mvc.perform(get("/trade/delete/2"))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/trade/list"));
    }

    @WithMockUser("user")
    @Test
    void getTradeDelete_shouldReturnIsNotFound() throws Exception {
        mvc.perform(get("/trade/delete/200"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(view().name("404"));
    }

    @WithMockUser("user")
    @Test
    void postTradeValidate_shouldReturnIsFound() throws Exception {
        Trade trade = new Trade();
        trade.setAccount("New Account");
        trade.setType("New Type");
        trade.setBuyQuantity(5.9);

        mvc.perform(post("/trade/validate")
                        .flashAttr("trade", trade))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/trade/list"));
    }

    @WithMockUser("user")
    @Test
    void postTradeValidate_shouldRedirect() throws Exception {
        Trade trade = new Trade();
        trade.setAccount("New Bad Account");

        mvc.perform(post("/trade/validate")
                        .flashAttr("trade", trade))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"));
    }

    @WithMockUser("user")
    @Test
    void postTradeUpdate_shouldReturnIsFound() throws Exception {
        Trade trade = new Trade();
        trade.setTradeId(1);
        trade.setAccount("update account");
        trade.setType("type 1");
        trade.setBuyQuantity(1d);

        mvc.perform(post("/trade/update/1")
                        .flashAttr("trade", trade))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/trade/list"));
    }

    @WithMockUser("user")
    @Test
    void postTradeUpdate_shouldRedirect() throws Exception {
        Trade trade = new Trade();
        trade.setTradeId(1);

        mvc.perform(post("/trade/update/1")
                        .flashAttr("trade", trade))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("trade/update"));
    }
}
