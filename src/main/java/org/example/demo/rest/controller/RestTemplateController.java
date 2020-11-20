package org.example.demo.rest.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.demo.rest.domain.Member;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RestController
@RequestMapping("/api")
public class RestTemplateController {

    private RestTemplate restTemplate;

    public RestTemplateController() {
        restTemplate = new RestTemplate();
    }

    @GetMapping()
    public void test(){
        String url = "http://localhost:8080/api/test";
        Member member = restTemplate.getForObject(url, Member.class);
        log.info("api result : "+ member.toString());
    }
    @GetMapping(value = "/test", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Member testapi(){
        Member member = new Member("id","name","pwd");
        return member;
    }

}
