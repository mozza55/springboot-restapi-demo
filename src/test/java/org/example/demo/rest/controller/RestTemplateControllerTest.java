package org.example.demo.rest.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.demo.rest.config.RestTemplateConfig;
import org.example.demo.rest.service.TranslationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.AutoConfigureMybatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RestTemplateControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void test1() throws Exception {
        ResultActions actions = mockMvc.perform(get("/api"))
                .andDo(print());
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].postId",is(1)))
                .andExpect(jsonPath("$[0].content",is("content")));
    }
}