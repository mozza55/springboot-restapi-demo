package org.example.demo.rest.repository;

import lombok.extern.slf4j.Slf4j;
import org.example.demo.rest.domain.Member;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;
@Slf4j
@RunWith(SpringRunner.class)
@MybatisTest
@Import(MemberDao.class)
public class MemberDaoTest {
    @Autowired
    private MemberDao memberDao;
    @Test
    public void selectList() {
        List<Member> memberList = memberDao.selectList();
        log.info(memberList.get(0).toString());
        Assert.assertNotNull(memberList.get(0));
    }
}