package com.example.blogback.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.blogback.domain.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MessageDao extends BaseMapper<Message> {

    @Select("SELECT l.id FROM love l JOIN article a ON l.article_id = a.id WHERE a.user_id = #{userId}")
    List<Integer> findUsersWhoLikedArticle(int userId);


    @Select("SELECT c.id FROM collect c JOIN article a ON c.article_id = a.id WHERE a.user_id = #{userId}")
    List<Integer> findUsersWhoCollectedArticle(int userId);

    @Select("SELECT c.id FROM comment c JOIN article a ON c.article_id = a.id WHERE a.user_id = #{userId}")
    List<Integer> findUsersWhoCommentedArticle(int userId);

    @Select("SELECT DISTINCT CASE WHEN receiver_id = #{userId} THEN sender_id  ELSE receiver_id END AS unique_id FROM message WHERE sender_id = #{userId} OR receiver_id = #{userId}")
    List<Integer> findAllMessageFriends(int userId);
}
