package org.example.demo.rest.controller;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class IndexController {
    @Autowired
    private RestTemplate restTemplate;
    @GetMapping("/")
    public String index(){
        return "정상 실행중";
    }

}
