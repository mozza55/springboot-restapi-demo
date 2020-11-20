package org.example.demo.rest.repository;

import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSession;
import org.example.demo.rest.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberDao {

    private final SqlSession sqlSession;
    // spring-mybatis 모듈의 구현체인 SqlSessionTemplate를 사용

    public List<Member> selectList(){
        return sqlSession.selectList("org.example.demo.rest.repository.MemberMapper.findAll");
    }
}
