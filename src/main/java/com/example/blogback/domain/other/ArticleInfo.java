package com.example.blogback.domain.other;

import com.example.blogback.domain.Article;
import com.example.blogback.domain.Column;
import com.example.blogback.domain.Label;
import com.example.blogback.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleInfo {

    private Article article;

    private Integer browse;

    private Integer collect;

    private Integer love;

    private Integer comments;

    private List<Label> labels;

    private Column column;

    private boolean userIsCollect;

    private boolean userIsLove;

    private boolean userIsComment;

    private boolean userIsFollow;

    private User user;

}
