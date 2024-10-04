package com.example.blogback.domain.other;


import com.example.blogback.domain.Collection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleCollect {

    private Collection collection;

    private boolean isCollected;

}
