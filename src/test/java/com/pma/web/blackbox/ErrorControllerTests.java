package com.pma.web.blackbox;

import org.junit.jupiter.api.RepeatedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@WebMvcTest(ErrorController.class)
public class ErrorControllerTests {

    @Autowired
    private MockMvc mockMvc;

    private static final int TEST_REPETITIONS = 1;

    @RepeatedTest(TEST_REPETITIONS)
    public void errorCodeTest() throws Exception{

        this.mockMvc
                .perform(get("/wrong/url"))
                .andExpect(status().is4xxClientError());
    }

    @RepeatedTest(TEST_REPETITIONS)
    public void pageNotFound() throws Exception{

        this.mockMvc
                .perform(get("/page-not-found"))
                .andExpect(status().isOk())
                .andExpect(view().name("pageNotFound"));
    }
}
