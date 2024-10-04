package com.example.blogback.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.blogback.common.R;
import com.example.blogback.dao.ArticleDao;
import com.example.blogback.domain.Article;
import com.example.blogback.domain.other.ArticleInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ResponseBody
@RestController
@RequestMapping("/api/article")
public class ArticleController {

    @Autowired
    private ArticleDao articleDao;

    @GetMapping("/search/{id}")
    public R getArticle(@PathVariable Integer id){
       ArticleInfo articleInfo=Common.getArticleEditById(id);

       if(articleInfo!=null) return R.success(articleInfo);
       else return R.error("获取失败");
    }


    /**/
    @GetMapping("/{userId}")
    public R getArticlesByUserId(@PathVariable Integer userId){
        ArrayList<ArticleInfo> list=new ArrayList<>();

        QueryWrapper<Article> articleQueryWrapper=new QueryWrapper<>();
        articleQueryWrapper.eq("user_id",userId);

        List<Article> articles = articleDao.selectList(articleQueryWrapper);
        for (int i=0;i<articles.size();i++){
            list.add(Common.getArticleInfo(articles.get(i).getId(),userId));
        }

        return R.success(list);
    }

    @GetMapping("/draft/{userId}")
    public R getDraftArticle(@PathVariable Integer userId){
        ArrayList<ArticleInfo> list=new ArrayList<>();

        QueryWrapper<Article> articleQueryWrapper=new QueryWrapper<>();
        articleQueryWrapper.eq("user_id",userId)
                .eq("status",0);

        List<Article> articles = articleDao.selectList(articleQueryWrapper);
        for (int i=0;i<articles.size();i++){
            list.add(Common.getArticleInfo(articles.get(i).getId(),userId));
        }

        return R.success(list);
    }

    @GetMapping("/audit/{userId}")
    public R getAuditArticle(@PathVariable Integer userId){
        ArrayList<ArticleInfo> list=new ArrayList<>();

        QueryWrapper<Article> articleQueryWrapper=new QueryWrapper<>();
        articleQueryWrapper.eq("user_id",userId)
                .eq("status",1);

        List<Article> articles = articleDao.selectList(articleQueryWrapper);
        for (int i=0;i<articles.size();i++){
            list.add(Common.getArticleInfo(articles.get(i).getId(),userId));
        }

        return R.success(list);
    }

    @GetMapping("/{userId}/{id}")
    public R getArticleById(@PathVariable Integer id,@PathVariable Integer userId){
        ArticleInfo articleInfo = Common.getArticleInfo(id,userId);
        return R.success(articleInfo);
    }

    @PostMapping("/save")
    private R saveArticle(@RequestBody Article article){

        if(article.getId()!=null)
        {
            int i = articleDao.updateById(article);

            if(i>0)
            {
                return R.success(article);
            }
            else return R.error("更新失败");
        }
        else{
            int insert = articleDao.insert(article);

            if(insert>0)
            {
                return R.success(article);
            }
            else return R.error("更新成功");
        }
    }

    @PostMapping("/publish")
    private R publish(@RequestBody Article article){
        article.setDate(new Date());
        if(article.getId()==null)
        {
            article.setStatus(1);
            int insert = articleDao.insert(article);
            if(insert>0){
                return R.success(article);
            }
            else return R.error("发布失败");

        }
        else {
            article.setStatus(1);

            int i = articleDao.updateById(article);
            if(i>0) return R.success(article);
            else return R.error("发布失败");
        }
    }

    @GetMapping("/byColumn/{id}")
    public R getArticleByColumn (@PathVariable Integer id){
        QueryWrapper<Article> articleQueryWrapper=new QueryWrapper<>();
        articleQueryWrapper.eq("column_id",id);

        List<Article> articles = articleDao.selectList(articleQueryWrapper);
        return R.success(articles);
    }


    @GetMapping("/recommend/{userId}")
    public R getRecommend(@PathVariable Integer userId){
        ArrayList<ArticleInfo> allArticle = Common.getAllArticle(userId);
        return R.success(allArticle);
    }

    @GetMapping("/follow/{userId}")
    public R getFollow(@PathVariable Integer userId){

        ArrayList<ArticleInfo> allArticles=Common.getAllFollowArticles(userId);
        return R.success(allArticles);
    }

    @GetMapping("/new/{userId}")
    public R getNew(@PathVariable Integer userId){

        QueryWrapper<Article> articleQueryWrapper=new QueryWrapper<>();
        articleQueryWrapper.eq("status",2)
                .groupBy("date","title","content","id","user_id","url","status","category_id","column_id","kind","brief");

        List<Article> articles = articleDao.selectList(articleQueryWrapper);

        ArrayList<ArticleInfo> articleInfos=new ArrayList<>();
        for (Article article : articles) {
            articleInfos.add(Common.getArticleInfo(article.getId(),userId));
        }

        return R.success(articleInfos);
    }

    @GetMapping("/hot/{userId}")
    public R getHot(@PathVariable Integer userId){

        ArrayList<ArticleInfo> hotArticles = Common.getHotArticles(userId);
        return R.success(hotArticles);
    }

    @GetMapping("/searchInput")
    public R searchArticle(@RequestParam String searchInput,@RequestParam  Integer userId){

        QueryWrapper<Article> articleQueryWrapper=new QueryWrapper<>();
        articleQueryWrapper.like("title",searchInput)
                .or()
                .like("brief",searchInput);

        List<Article> articles = articleDao.selectList(articleQueryWrapper);

        ArrayList<ArticleInfo> articleInfos=new ArrayList<>();
        for (Article article : articles) {
            ArticleInfo articleInfo = Common.getArticleInfo(article.getId(), userId);
            articleInfos.add(articleInfo);
        }

        return R.success(articleInfos);
    }
}
