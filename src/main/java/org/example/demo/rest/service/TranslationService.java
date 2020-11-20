package org.example.demo.rest.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class TranslationService {
    private final RestTemplate restTemplate;
    private String url="https://dapi.kakao.com/v2/translation/translate";
    @Value("${external.kakaoapi.key}")
    private String key;


    public String Translate(String targetLang,String targetText) {
        String result = null;
        UriComponents builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("src_lang","kr")
                .queryParam("target_lang",targetLang)
                .queryParam("query",targetText)
                .build(false);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization","KakaoAK "+key);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        ResponseEntity<String> response = restTemplate.exchange(builder.toString(), HttpMethod.GET, new HttpEntity<>(headers), String.class);

        // POST 방식으로 body도 보낼 때
        //Map<String,String> body = new HashMap<>();
        //body.put("query",text);
        //HttpEntity<Map<String,String >> entity = new HttpEntity<>(body,headers);
        //ResponseEntity<String> response = restTemplate.postForEntity(builder.toString(), new HttpEntity<>(body,headers), String.class);

        try {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(response.getBody());
            JSONArray translatedText =(JSONArray) jsonObject.get("translated_text");
            List<String> str = (List<String>) translatedText.get(0);
            result = str.stream()
                                .collect(Collectors.joining(" "));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  result;
    }
}
