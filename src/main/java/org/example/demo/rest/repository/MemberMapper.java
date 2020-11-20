package org.example.demo.rest.repository;

import org.apache.ibatis.annotations.*;
import org.example.demo.rest.domain.Member;

import java.util.List;

@Mapper
public interface MemberMapper {
    @Insert("insert into Member(user_id, name, password) values(#{userId},#{name},#{password})")
    @Options(useGeneratedKeys = true, keyProperty = "memberId")
    public Long save(Member member);

   // @Select("select * from Member")
    public List<Member> findAll();

    @Select("select * from Member where member_id = #{memberId}")
//    @Results({
//            @Result(property = "memberId", column = "member_id"),
//            @Result(property = "userId", column = "user_id")
//    })
    public Member findOneById(Long memberId);
    @Select("select * from Member where user_id = #{userId}")
    public Member findOneByUserId(String userId);

    @Update("update member set name = #{name}, password=#{password} where member_id = #{memberId}")
    public void update(Member member);

    @Delete("delete from Member where member_id = #{memberId}")
    public void delete(Long memberId);

}
