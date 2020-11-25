package org.example.demo.rest.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.demo.rest.domain.Member;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.lang.Nullable;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Slf4j
@RestController
@RequestMapping("/api")
public class RestTemplateController {

    @GetMapping(value = "/test" )
    public ResponseEntity<List<Member>> testGet(){
        List<Member> memberList = new ArrayList<>();
        memberList.add(new Member("id1","name1","1111"));
        memberList.add(new Member("id2","name2","1111"));
        log.info("member List : {}",memberList);
        return new ResponseEntity<List<Member>>(memberList,HttpStatus.OK);
    }

    @GetMapping(value = "/test/{memberId}" )
    public ResponseEntity<Member> testGet(@PathVariable Long memberId, @RequestParam String name){
        log.info("name {}",name);
        Member member = new Member("id","noname","pwd");
        member.setMemberId(memberId);
        member.setName(name);
        return new ResponseEntity<Member>(member,HttpStatus.OK);
    }

    @PostMapping(value = "/test" )
    public ResponseEntity<Member> testPost(@RequestBody Member member,
                                           @Nullable @RequestHeader("Authorization") String accept){
        log.info("member {}",member);
        log.info("header Authorization {}",accept);
        member.setMemberId(11L);
        return new ResponseEntity<Member>(member,HttpStatus.CREATED);
    }

    @PutMapping(value = "/test/{memberId}")
    public ResponseEntity testPut(@PathVariable Long memberId, @RequestBody Member member){
        log.info("memberId {}",memberId);
        log.info("member {}",member);
        //자원 수정 작업
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping(value = "/test/{memberId}")
    public ResponseEntity testDelete(@PathVariable Long memberId){
        log.info("memberId {}",memberId);
        //자원 제거 작업
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping(value = "/test/message",consumes =MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<Member> testPost_message(@RequestBody MultiValueMap<String,String> data,
                                                   @Nullable @RequestHeader("content-type") String contentType){
        log.info("member {}",data);
        log.info("header content-type {}",contentType);
        Member member = new Member("id1","name1","1111");
        member.setMemberId(11L);
        member.setName("탱구");
        return new ResponseEntity<Member>(member,HttpStatus.CREATED);
    }
}
