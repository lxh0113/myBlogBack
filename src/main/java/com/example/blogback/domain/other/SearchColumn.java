package com.example.blogback.domain.other;

import com.example.blogback.domain.Article;
import com.example.blogback.domain.Column;
import com.example.blogback.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchColumn {

    private User user;

    private Column column;

    private List<Article> articles;
}
