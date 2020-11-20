package org.example.demo.rest.repository;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.LocalDateTimeTypeHandler;
import org.example.demo.rest.domain.Post;

import java.util.List;

@Mapper
public interface PostMapper {
    @Insert("insert into Post(member_id, content, created_at) values(#{memberId},#{content},#{createdAt, typeHandler=org.apache.ibatis.type.LocalDateTimeTypeHandler})")
    @Options(useGeneratedKeys = true, keyProperty = "postId")
    public Long save(Post post);


    @Select("select * from Post")
    @Results({
            @Result(property = "createdAt", column = "created_at", typeHandler = LocalDateTimeTypeHandler.class)
    })
    public List<Post> findAll();

    @Select("select * from Post where post_id = #{postId}")
    @Results({
            //@Result(property = "postId", column = "post_id"),
            //@Result(property = "memberId", column = "member_id"),
            @Result(property = "createdAt", column = "created_at", typeHandler = LocalDateTimeTypeHandler.class)
    })
    public Post findOneById(Long postId);

    @Update("update post set content=#{content} where post_id =#{postId}")
    public void update(Post post);

    @Delete("delete from Post where post_id = #{postId}")
    public void delete(Long postId);

    @Delete("delete from Post where member_id = #{memberId}")
    public void deleteByMember(Long memberId);
}
