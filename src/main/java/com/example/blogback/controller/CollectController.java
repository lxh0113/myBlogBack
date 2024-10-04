package com.example.blogback.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.blogback.common.R;
import com.example.blogback.dao.CollectDao;
import com.example.blogback.domain.Article;
import com.example.blogback.domain.Collect;
import com.example.blogback.domain.Collection;
import com.example.blogback.domain.other.ArticleCollect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@ResponseBody
@RestController
@RequestMapping("/api/collect")
public class CollectController {

    @Autowired
    private CollectDao collectDao;

    @PostMapping
    public R addCollect(@RequestBody Collect collect){
        int insert = collectDao.insert(collect);

        if(insert>0) return R.success(collect);
        else return R.error("收藏失败");
    }

    @GetMapping("/article")
    public R getArticleCollect(@RequestParam Integer userId,@RequestParam Integer articleId){
//        筛选出来
        ArrayList<ArticleCollect> articleCollect = Common.getArticleCollect(userId, articleId);

        return R.success(articleCollect);
    }

    @DeleteMapping("/{userId}/{articleId}")
    public R deleteCollect(@PathVariable Integer userId,@PathVariable Integer articleId){
        QueryWrapper<Collect> collectQueryWrapper=new QueryWrapper<>();
        collectQueryWrapper.eq("user_id",userId)
                .eq("article_id",articleId);

        int delete = collectDao.delete(collectQueryWrapper);
        if(delete>0) return R.success("取消收藏成功");
        else return R.error("取消收藏失败");
    }

    @PostMapping("/article")
    public R collectionOfArticle(@RequestBody Collect collect){
//        修改,先删除，再新增
        QueryWrapper<Collect> collectQueryWrapper=new QueryWrapper<>();
        collectQueryWrapper.eq("article_id",collect.getArticleId())
                .eq("user_id",collect.getUserId());

        int delete = collectDao.delete(collectQueryWrapper);

        int insert = collectDao.insert(collect);

         if(insert>0) return R.success(collect);
         else return R.error("收藏失败");

    }

    @GetMapping("/collection/{id}")
    public R getCollectByCollectionId(@PathVariable Integer id){
        QueryWrapper<Collect> collectQueryWrapper=new QueryWrapper<>();
        collectQueryWrapper.eq("collection_id",id);

        List<Collect> collects = collectDao.selectList(collectQueryWrapper);

        List<Article> articles=new ArrayList<>();
        for (Collect collect : collects) {
            articles.add(Common.getArticleById(collect.getArticleId()));
        }

        return R.success(articles);
    }
}
