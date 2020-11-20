package org.example.demo.rest.repository;

import lombok.extern.slf4j.Slf4j;
import org.example.demo.rest.domain.Member;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@MybatisTest
public class MemberMapperTest {

    @Autowired
    private MemberMapper memberMapper;
    private Member member;

    @Before
    public void setUp() throws Exception {
        log.info("mapper class : "+memberMapper.getClass().toString());
    }

    @Test
    public void DB_조회_컬럼명_Test(){
        Member member = new Member("a","bbb","1234");
        Long saveId = memberMapper.save(member);
        Member findMember = memberMapper.findOneById(saveId);
        log.info(findMember.toString());
        Assert.assertEquals(saveId,findMember.getMemberId());
    }

    @Test
    public void save_Get_InsertId() {
        //Long MemberId = 4L;
        Member member = new Member("aa","bbb","1234");
        memberMapper.save(member);
        log.info("member_id : "+member.getMemberId());
        Assert.assertNotNull(member.getMemberId());
        // 값 비교
        Assert.assertEquals("저장 오류",member, memberMapper.findOneById(member.getMemberId()));
        // 참조값 비교
        //Assert.assertSame(member, memberMapper.findOneById(member.getMemberId()));
    }

    @Test
    public void delete() {
        Member member = new Member("aaa","bbb","1234");
        memberMapper.save(member);
        Assert.assertNotNull("저장 오류",memberMapper.findOneById(member.getMemberId()));
        memberMapper.delete(member.getMemberId());
        Assert.assertNull("삭제 오류",memberMapper.findOneById(member.getMemberId()));
    }

    @Test
    public void update() {
        Member member = new Member("bbb","bbb","1234");
        memberMapper.save(member);
        member.setName("OMG");
        memberMapper.update(member);
        Assert.assertEquals("업데이트 오류",member,memberMapper.findOneById(member.getMemberId()));
    }

    @Test
    public void findAll(){
        Member member = new Member("ccc","bbb","1234");
        memberMapper.save(member);
        List<Member> memberList = memberMapper.findAll();
        log.info("find all : "+memberList.get(0).toString());
    }
}