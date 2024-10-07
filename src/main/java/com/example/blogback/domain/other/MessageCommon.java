package com.example.blogback.domain.other;

import com.example.blogback.domain.Article;
import com.example.blogback.domain.Comment;
import com.example.blogback.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageCommon {

    private User user;

    private String kind;

    private Comment comment;

    private Article article;
}
