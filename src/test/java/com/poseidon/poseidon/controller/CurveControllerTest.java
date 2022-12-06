package com.poseidon.poseidon.controller;

import com.poseidon.poseidon.domain.CurvePoint;
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
public class CurveControllerTest {

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
    void getCurvePointList_shouldReturnUnauthenticated() throws Exception {
        mvc.perform(get("/curvePoint/list"))
                .andDo(print())
                .andExpect(unauthenticated());
    }

    @WithMockUser("user")
    @Test
    void getCurvePointList_shouldReturnIsOk() throws Exception {
        mvc.perform(get("/curvePoint/list"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser("user")
    @Test
    void getCurvePointAdd_shouldReturnIsOk() throws Exception {
        mvc.perform(get("/curvePoint/add"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser("user")
    @Test
    void getCurvePointUpdate_shouldReturnIsOk() throws Exception {
        mvc.perform(get("/curvePoint/update/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/update"));
    }

    @WithMockUser("user")
    @Test
    void getCurvePointUpdate_shouldReturnIsNotFound() throws Exception {
        mvc.perform(get("/curvePoint/update/100"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(view().name("404"));
    }

    @WithMockUser("user")
    @Test
    void getCurvePointDelete_shouldReturnIsFound() throws Exception {
        mvc.perform(get("/curvePoint/delete/2"))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/curvePoint/list"));
    }

    @WithMockUser("user")
    @Test
    void getCurvePointDelete_shouldReturnIsNotFound() throws Exception {
        mvc.perform(get("/curvePoint/delete/200"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(view().name("404"));
    }

    @WithMockUser("user")
    @Test
    void postCurvePointValidate_shouldReturnIsOk() throws Exception {
        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setCurveId(100);
        curvePoint.setTerm(8.3);
        curvePoint.setValue(50d);

        mvc.perform(post("/curvePoint/validate")
                        .flashAttr("curvePoint", curvePoint))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/list"));
    }

    @WithMockUser("user")
    @Test
    void postCurvePointValidate_shouldRedirect() throws Exception {
        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setCurveId(200);

        mvc.perform(post("/curvePoint/validate")
                        .flashAttr("curvePoint", curvePoint))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"));
    }

    @WithMockUser("user")
    @Test
    void postCurvePointUpdate_shouldReturnIsFound() throws Exception {
        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setId(1);
        curvePoint.setCurveId(1);
        curvePoint.setTerm(1.5);
        curvePoint.setValue(8.7);


        mvc.perform(post("/curvePoint/update/1")
                        .flashAttr("curvePoint", curvePoint))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/curvePoint/list"));
    }

    @WithMockUser("user")
    @Test
    void postBidListUpdate_shouldRedirect() throws Exception {
        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setId(1);

        mvc.perform(post("/curvePoint/update/1")
                        .flashAttr("curvePoint", curvePoint))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/update"));
    }

}
