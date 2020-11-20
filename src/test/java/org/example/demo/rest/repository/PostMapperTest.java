package org.example.demo.rest.repository;

import org.example.demo.rest.domain.Member;
import org.example.demo.rest.domain.Post;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@MybatisTest
public class PostMapperTest {

    @Autowired
    private PostMapper postMapper;
    @Autowired
    private MemberMapper memberMapper;

    private Member member;
    @Before
    public void setUp() throws Exception {
        member = new Member("aaa","bbb","1234");
        Long saveId = memberMapper.save(member);
    }

    @Test
    public void save_and_find() {
        Post post = new Post(member.getMemberId(),"내용입니다", LocalDateTime.now().withNano(0));
        postMapper.save(post);
        Post find = postMapper.findOneById(post.getPostId());
        Assert.assertNotNull("저장 오류", find);
        Assert.assertEquals("저장한 Post 데이터가 일치하지 않습니다", post,find);
    }

    @Test
    public void update(){
        Post post = new Post(member.getMemberId(),"내용입니다", LocalDateTime.now().withNano(0));
        postMapper.save(post);
        post.setContent("OMG!!");
        postMapper.update(post);
        Assert.assertEquals("업데이트 오류",post, postMapper.findOneById(post.getPostId()));
    }

    @Test
    public void delete() {
        Post post = new Post(member.getMemberId(),"내용입니다", LocalDateTime.now().withNano(0));
        postMapper.save(post);
        postMapper.delete(post.getPostId());
        Assert.assertNull("삭제 오류",postMapper.findOneById(post.getPostId()));
    }
}