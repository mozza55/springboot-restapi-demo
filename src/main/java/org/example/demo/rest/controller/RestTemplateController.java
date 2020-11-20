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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Slf4j
@RestController
@RequestMapping("/api")
public class RestTemplateController {

    private RestTemplate restTemplate;

    public RestTemplateController() {

        restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>();
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_FORM_URLENCODED));

        converters.add(mappingJackson2HttpMessageConverter);
        converters.add(new StringHttpMessageConverter());
        converters.add(new FormHttpMessageConverter());
        restTemplate.setMessageConverters(converters);
    }

    @GetMapping()
    public void test(){
        String url = "http://localhost:8080/api/test";
        Map<String,String > map = new HashMap<String,String >();
        map.put("memberId","1");
        Member member = restTemplate.getForObject(url, Member.class,map);
        log.info("api result : "+ member.toString());
    }
    @GetMapping(value = "/test")
    public ResponseEntity<Member> testapi(){
        Member member = new Member("id","name","pwd");
        return new ResponseEntity<Member>(member,HttpStatus.OK);
    }

}
