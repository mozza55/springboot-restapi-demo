package org.example.demo.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.example.demo.rest.domain.Member;
import org.example.demo.rest.repository.MemberMapper;
import org.example.demo.rest.service.MemberService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.AutoConfigureMybatis;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@RunWith(SpringRunner.class)
@WebMvcTest({MemberController.class})
@AutoConfigureMybatis
@Import(MemberService.class)
public class MemberControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MemberMapper memberMapper;



//    @Before
//    public void setUp() throws Exception {
//        Member member = new Member("zero","taeyeon","1111");
//        memberMapper.save(member);
//        Member member1 = new Member("spark","ty","1111");
//        memberMapper.save(member1);
//    }

    @Test
    public void joinMember() throws Exception {
// request param
//        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
//        params.add("userId","fine");
//        params.add("name","ty");
//        params.add("password","1111");
                Member member = new Member("fine","ty","1111");
        log.info("ob Mapper :"+objectMapper.writeValueAsBytes(member));
        //when
        ResultActions actions = mockMvc.perform(post("/member")
                        .contentType("application/json")
                        .accept("application/json")
                        .content(objectMapper.writeValueAsBytes(member)))
//                        .content("{\n" +
//                                "    \"userId\" :\"fine\",\n" +
//                                "    \"name\" :\"ty\",\n" +
//                                "    \"password\" :\"1111\"\n" +
//                                "}"))
                .andDo(print());
        Member m = memberMapper.findOneByUserId("fine");
        //then
        actions.andExpect(status().isOk())
                .andExpect(content().string(m.getMemberId().toString()));
    }

    @Test
    public void getMemberList_test() throws Exception {
        //when
        ResultActions actions = mockMvc.perform(get("/member"))
                .andDo(print());
        //then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$[*]",hasSize(2)))
                .andExpect(jsonPath("$[0].userId",is("zero")))
                .andExpect(jsonPath("$[0].name",is("taeyeon")))
                .andExpect(jsonPath("$[1].userId",is("spark")));
    }


    @Test
    public void getMember() throws Exception {
        //when
        ResultActions actions = mockMvc.perform(get("/member/{memberId}",1L))
                .andDo(print());
        //then
        actions.andExpect(status().isOk());
    }


    @Test
    public void updateMember() throws Exception {
        Member member = new Member("spark","kingty","1111");
        //when
        ResultActions actions = mockMvc.perform(put("/member/{memberId}",2L)
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsBytes(member)))
                .andDo(print());
        //then
        actions.andExpect(status().isOk());
        log.info("member update result :"+memberMapper.findOneById(2L).toString());
    }

    @Test
    public void deleteMember() throws Exception {
        //when
        ResultActions actions = mockMvc.perform(delete("/member/{memberId}",2L))
                .andDo(print());
        //then
        actions.andExpect(status().isOk());
        Assertions.assertNull(memberMapper.findOneById(2L));
    }
}