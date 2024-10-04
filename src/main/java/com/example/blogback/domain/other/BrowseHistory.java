package com.example.blogback.domain.other;

import com.example.blogback.domain.Article;
import com.example.blogback.domain.Browse;
import com.example.blogback.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrowseHistory {

    private Browse browse;

    private Article article;

    private User user;
}
