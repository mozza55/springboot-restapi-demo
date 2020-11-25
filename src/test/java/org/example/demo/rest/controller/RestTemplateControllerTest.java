package org.example.demo.rest.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.example.demo.rest.config.RestTemplateConfig;
import org.example.demo.rest.domain.Member;
import org.example.demo.rest.service.TranslationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.AutoConfigureMybatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    ObjectMapper objectMapper;
    private RestTemplate restTemplate;

    @Before
    public void setUp() throws Exception {
        restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
//        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
//        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_FORM_URLENCODED));
//        restTemplate.getMessageConverters()
//                .add(mappingJackson2HttpMessageConverter);
    }

    @Test
    public void test_getFor() throws Exception {
        String URI = "http://localhost:8080/api/test/{memberId}?name={name}";
        Map<String, String> params = new HashMap<String, String>();
        params.put("memberId","10");
        params.put("name","김태연★");
        Member member =restTemplate.getForObject(URI,Member.class,params);
        log.info("member : {}",member);
    }
    @Test
    public void test_getFor_uriBuilder() throws Exception {
        String URI = "http://localhost:8080/api/test/{memberId}?name={name}";
        Map<String, String> params = new HashMap<String, String>();
        params.put("memberId","10");
        params.put("name","kimty");
        //Member member =restTemplate.getForObject(URL,Member.class,params);
        URI = "http://localhost:8080/api/test";
        Map<String, String> param = new HashMap<String, String>();
        param.put("memberId","10");
        UriComponents builder = UriComponentsBuilder.fromHttpUrl(URI)
                .path("/{memberId}")
                .queryParam("name","김태연★")
                //.build(false);
                //.encode() //UTF-8 encoding toUri()로 URI타입을 넘기는 경우 사용
                .buildAndExpand(param);
        //한글이 포함된 경우
        // toString()으로 인자를 넘기는 경우는 내부적으로 인코딩이 이뤄지기 때문에 encoding을 안해줘도 되는듯.. 근데 내부라는게 어딘지 모르겠음 ㅎㅎ
        // toUri()로 URI타입의 인자를 넘기는 경우는 builder에서 encoding을 해줘야 한다.
        // UriComponentsBuilder.....encode()...; 디폴트 UTF-8로 인코딩 해줌
        log.info("toUri :{}",builder.toUri());
        log.info("toString :{}",builder.toString());
        Member member = restTemplate.getForObject(builder.toUri(),Member.class);
        //Member member = restTemplate.getForEntity(builder.toUri(),Member.class).getBody();
        //restTemplate.getForEntity(builder.toUri(),Member.class).getStatusCode();
        log.info("member : {}",member);
    }

    @Test
    public void test_postFor(){
        String URI = "http://localhost:8080/api/test";
        Member member = new Member("gg_ty","김태연","0309");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization","test key");
        Member result ;
        //result = restTemplate.postForObject(URI,member,Member.class);
        result = restTemplate.postForObject(URI,new HttpEntity<>(member,headers),Member.class);
        //ResponseEntity<Member> responseEntity = restTemplate.postForEntity(URI, member, Member.class);
        //ResponseEntity<Member> responseEntity = restTemplate.postForEntity(URI, new HttpEntity<>(member,headers), Member.class);
        //result = responseEntity.getBody();
        log.info("member : {}",result);
    }

    @Test
    public void test_put(){
        String URI = "http://localhost:8080/api/test/{memberId}";
        Member member = new Member("gg_ty","김태연","0309");
        restTemplate.put(URI,member,11L);
    }

    @Test
    public void test_delete(){
        String URI = "http://localhost:8080/api/test/{memberId}";
        restTemplate.delete(URI,11L);
    }

    @Test
    public void test_exchange(){
        String URI = "http://localhost:8080/api/test";
        Member member = new Member("gg_ty","김태연","0309");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization","test key");
        Member result ;
        result= restTemplate.exchange(URI, HttpMethod.POST, new HttpEntity<>(member,headers),Member.class).getBody();
        log.info("member : {}",result);
    }
    @Test
    public void test_exchange_put(){
        String URI = "http://localhost:8080/api/test/{memberId}";
        Member member = new Member("gg_ty","김태연","0309");
        Map<String,String> params = new HashMap<>();
        params.put("memberId","11");
        ResponseEntity<String> result = restTemplate.exchange(URI, HttpMethod.PUT, new HttpEntity<>(member), String.class, params);
        log.info("member : {}",result);
    }

    @Test
    public void test_exchange_get(){
        String URI = "http://localhost:8080/api/test";
        List<Member> memberList= restTemplate.exchange(URI, HttpMethod.GET,null,new ParameterizedTypeReference<List<Member>>(){}).getBody();
        log.info("member List: {}",memberList);

    }

    @Test
    public void test_messageConversion(){
        String URI = "http://localhost:8080/api/test/message";
        Member member = new Member("gg_ty","김태연","0309");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization","test key");
        //headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Lists.list(MediaType.APPLICATION_JSON));
        MultiValueMap<String,String> body = new LinkedMultiValueMap<>();
//        body.add("userId","gg_ty");
//        body.add("name","김태연");
//        body.add("password","0309");
        Map<String, String> maps = objectMapper.convertValue(member, new TypeReference<Map<String, String>>() {});
        body.setAll(maps);
        ResponseEntity<Member> responseEntity = restTemplate.exchange(URI, HttpMethod.POST, new HttpEntity<>(body, headers), Member.class);
        Member result = responseEntity.getBody();
        log.info("member : {}",responseEntity);
    }
}