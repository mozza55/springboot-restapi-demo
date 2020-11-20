package org.example.demo.rest.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.demo.rest.domain.Member;
import org.example.demo.rest.repository.MemberDao;
import org.example.demo.rest.repository.MemberMapper;
import org.example.demo.rest.service.MemberService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    //private final MemberDao memberDao;
    private final MemberService memberService;
    private final MemberMapper memberMapper;

    @GetMapping()
    public List<Member> getMemberList(){
        return memberMapper.findAll();
    }

    @GetMapping("/{memberId}")
    public Member getMember(@PathVariable Long memberId){
        return memberMapper.findOneById(memberId);
    }

    @PostMapping(produces = "application/json", consumes = "application/json")
    public Long joinMember(@RequestBody Member member){
        memberMapper.save(member);
        return member.getMemberId();
    }


    @PutMapping("/{memberId}")
    public void updateMember(@PathVariable Long memberId, @RequestBody Member member){
        member.setMemberId(memberId);
        memberMapper.update(member);
    }

    @DeleteMapping("/{memberId}")
    public void deleteMember(@PathVariable Long memberId){
        log.info("mock: "+memberService.getClass());
        memberService.delete(memberId);
    }


}
