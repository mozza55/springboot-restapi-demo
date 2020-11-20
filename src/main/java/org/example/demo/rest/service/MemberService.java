package org.example.demo.rest.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.demo.rest.domain.Member;
import org.example.demo.rest.repository.MemberMapper;
import org.example.demo.rest.repository.PostMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberMapper memberMapper;
    private final PostMapper postMapper;

    public List<Member> finda(){
        System.out.println("dfasfsf");
        log.info("mapper :"+memberMapper.getClass());
        return memberMapper.findAll();
    }

    public Long join(Member member){
        memberMapper.save(member);
        return member.getMemberId();
    }

    public void delete(Long memberId){
        memberMapper.delete(memberId);
        postMapper.deleteByMember(memberId);
    }
}
