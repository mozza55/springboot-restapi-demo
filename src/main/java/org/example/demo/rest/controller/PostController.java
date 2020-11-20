package org.example.demo.rest.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.demo.rest.domain.Post;
import org.example.demo.rest.repository.PostMapper;
import org.example.demo.rest.service.TranslationService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostMapper postMapper;
    private final TranslationService translationService;

    @GetMapping()
    public List<Post> getPostList(){
        return postMapper.findAll();
    }

    @GetMapping("/{postId}")
    public Post getPost(@PathVariable Long postId){
        return postMapper.findOneById(postId);
    }

    @PostMapping()
    public Long insertPost(@RequestBody Post post){
        post.setCreatedAt(LocalDateTime.now().withNano(0));
        postMapper.save(post);
        return  post.getPostId();
    }

    @PutMapping("/{postId}")
    public void updatePost(@PathVariable Long postId, @RequestBody Post post){
        //post.setCreatedAt(LocalDateTime.now().withNano(0));
        post.setPostId(postId);
        postMapper.update(post);
    }

    @DeleteMapping("/{postId}")
    public void deletePost(@PathVariable Long postId){
        postMapper.delete(postId);
    }

    @GetMapping("/{postId}/{lang}")
    public Post translatePost(@PathVariable Long postId, @PathVariable String lang){
        Post post = postMapper.findOneById(postId);
        String transContent = translationService.Translate(lang, post.getContent());
        post.setContent(transContent);
        return post;
    }

}
