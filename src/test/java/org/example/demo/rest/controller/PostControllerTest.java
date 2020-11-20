package org.example.demo.rest.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.example.demo.rest.config.RestTemplateConfig;
import org.example.demo.rest.domain.Member;
import org.example.demo.rest.domain.Post;
import org.example.demo.rest.repository.MemberMapper;
import org.example.demo.rest.repository.PostMapper;
import org.example.demo.rest.service.TranslationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.AutoConfigureMybatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@RunWith(SpringRunner.class)
@WebMvcTest(PostController.class)
@AutoConfigureMybatis
@Import({TranslationService.class, RestTemplateConfig.class})
public class PostControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PostMapper postMapper;
    @Autowired
    private MemberMapper memberMapper;

    private Member member;
    private Post post;
    @Before
    public void setUp() throws Exception {
        member = new Member("zero","taeyeon","1111");
        memberMapper.save(member);
        post = new Post(member.getMemberId(),"번역할 내용 입니다", LocalDateTime.now().withNano(0));
        postMapper.save(post);
    }

    @Test
    public void getPostList() throws Exception {
        ResultActions actions = mockMvc.perform(get("/post"))
                .andDo(print());
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].postId",is(1)))
                .andExpect(jsonPath("$[0].content",is("content")));
    }

    @Test
    public void getPost() throws Exception {
        //when
        ResultActions actions = mockMvc.perform(get("/post/{postId}",1L))
                .andDo(print());
        //then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("postId",is(1)));
    }

    @Test
    public void getTranslatedPost() throws Exception {
        //when
        ResultActions actions = mockMvc.perform(get("/post/{postId}/{lang}",1L,"en"))
                .andDo(print());
        //then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("postId",is(1)));
    }
    @Test
    public void insertPost() throws Exception {
        Post post1 = new Post(member.getMemberId(),"content2");
        ResultActions actions = mockMvc.perform(post("/post")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsBytes(post1)))
                .andDo(print());
        String postId = actions.andReturn().getResponse().getContentAsString();
        log.info("post : "+postMapper.findOneById(Long.valueOf(postId)));

    }


    @Test
    public void updatePost() throws Exception {
        Post post1 = new Post("내용변경");
        //when
        ResultActions actions = mockMvc.perform(put("/post/{postId}",1L)
                .contentType("application/json")
                .content(objectMapper.writeValueAsBytes(post1)))
                .andDo(print());
        //then
        actions.andExpect(status().isOk());
        log.info("post update result :"+postMapper.findOneById(1L).toString());

    }

    @Test
    public void deletePost() throws Exception {
        //when
        ResultActions actions = mockMvc.perform(delete("/post/{postId}",1L))
                .andDo(print());
        //then
        actions.andExpect(status().isOk());
        Assertions.assertNull(postMapper.findOneById(1L));
    }
}