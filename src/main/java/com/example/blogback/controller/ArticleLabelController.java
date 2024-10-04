package com.example.blogback.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.blogback.common.R;
import com.example.blogback.dao.ArticleLabelDao;
import com.example.blogback.domain.ArticleLabel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@ResponseBody
@RestController
@RequestMapping("/api/articleLabel")
public class ArticleLabelController {

    @Autowired
    private ArticleLabelDao articleLabelDao;


    @PostMapping
    public R changeArticleLabel(@RequestBody List<ArticleLabel> articleLabels){
        Integer articleId=articleLabels.get(0).getArticleId();

        QueryWrapper<ArticleLabel> articleLabelQueryWrapper=new QueryWrapper<>();
        articleLabelQueryWrapper.eq("article_id",articleId);

        articleLabelDao.delete(articleLabelQueryWrapper);

//        删除重新加入
        for (ArticleLabel articleLabel : articleLabels) {
            int insert = articleLabelDao.insert(articleLabel);
            if(insert<=0) return R.error("更改失败");
        }

        return R.success(articleLabels);
    }


}
