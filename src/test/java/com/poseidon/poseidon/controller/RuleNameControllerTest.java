package com.poseidon.poseidon.controller;

import com.poseidon.poseidon.domain.BidList;
import com.poseidon.poseidon.domain.RuleName;
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
public class RuleNameControllerTest {

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
    void getRuleNameList_shouldReturnUnauthenticated() throws Exception {
        mvc.perform(get("/ruleName/list"))
                .andDo(print())
                .andExpect(unauthenticated());
    }

    @WithMockUser("user")
    @Test
    void getRuleNameList_shouldReturnIsOk() throws Exception {
        mvc.perform(get("/ruleName/list"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser("user")
    @Test
    void getRuleNameAdd_shouldReturnIsOk() throws Exception {
        mvc.perform(get("/ruleName/add"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser("user")
    @Test
    void getRuleNameUpdate_shouldReturnIsOk() throws Exception {
        mvc.perform(get("/ruleName/update/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/update"));
    }

    @WithMockUser("user")
    @Test
    void getRuleNameUpdate_shouldReturnIsNotFound() throws Exception {
        mvc.perform(get("/ruleName/update/100"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(view().name("404"));
    }

    @WithMockUser("user")
    @Test
    void getRuleNameDelete_shouldReturnIsFound() throws Exception {
        mvc.perform(get("/ruleName/delete/2"))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/ruleName/list"));
    }

    @WithMockUser("user")
    @Test
    void getRuleNameDelete_shouldReturnIsNotFound() throws Exception {
        mvc.perform(get("/ruleName/delete/200"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(view().name("404"));
    }

    @WithMockUser("user")
    @Test
    void postRuleNameValidate_shouldReturnIsFound() throws Exception {
        RuleName ruleName = new RuleName();
        ruleName.setName("New Name");
        ruleName.setDescription("New Description");
        ruleName.setTemplate("New Template");
        ruleName.setJson("New Json");
        ruleName.setSqlStr("New Sql");
        ruleName.setSqlPart("New Sql Part");

        mvc.perform(post("/ruleName/validate")
                        .flashAttr("ruleName", ruleName))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/ruleName/list"));
    }

    @WithMockUser("user")
    @Test
    void postRuleNameValidate_shouldRedirect() throws Exception {
        RuleName ruleName = new RuleName();
        ruleName.setName("New Bad Name");

        mvc.perform(post("/ruleName/validate")
                        .flashAttr("ruleName", ruleName))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"));
    }

    @WithMockUser("user")
    @Test
    void postRuleNameUpdate_shouldReturnIsFound() throws Exception {
        RuleName ruleName = new RuleName();
        ruleName.setId(1);
        ruleName.setName("Update name");
        ruleName.setDescription("description 1");
        ruleName.setTemplate("template 1");
        ruleName.setJson("json 1");
        ruleName.setSqlStr("sql 1");
        ruleName.setSqlPart("sql part 1");

        mvc.perform(post("/ruleName/update/1")
                        .flashAttr("ruleName", ruleName))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/ruleName/list"));
    }

    @WithMockUser("user")
    @Test
    void postRuleNameUpdate_shouldRedirect() throws Exception {
        RuleName ruleName = new RuleName();
        ruleName.setId(1);

        mvc.perform(post("/ruleName/update/1")
                        .flashAttr("ruleName", ruleName))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/update"));
    }

}
