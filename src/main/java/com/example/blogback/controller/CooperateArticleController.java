package com.example.blogback.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.blogback.common.R;
import com.example.blogback.dao.CooperateArticleDao;
import com.example.blogback.domain.CooperateArticle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/cooperate")
@ResponseBody
public class CooperateArticleController {

    @Autowired
    private CooperateArticleDao cooperateArticleDao;

    @PostMapping("/add/{userId}")
    public R addNewArticle(@PathVariable Integer userId){

        CooperateArticle cooperateArticle=new CooperateArticle();
        cooperateArticle.setSavedBy(userId);
        cooperateArticle.setTime(new Date());
        cooperateArticle.setCreatedBy(userId);
        cooperateArticle.setRole("view");
        int insert = cooperateArticleDao.insert(cooperateArticle);
        if(insert>0) return R.success(cooperateArticle.getId());
        else return R.error("新增失败");
    }

    @PostMapping("/save")
    public R updateArticle(@RequestBody CooperateArticle cooperateArticle){
        int i = cooperateArticleDao.updateById(cooperateArticle);
        if(i>0) return R.success("更新成功");
        else return R.error("更新失败");
    }

    @DeleteMapping("/{id}")
    public R deleteArticle(@PathVariable Integer id){
        int i = cooperateArticleDao.deleteById(id);
        if(i>0) return R.success("删除成功");
        else return R.error("删除失败");
    }

    @GetMapping("/history")
    public R getHistoryArticles(@RequestParam Integer userId,@RequestParam Integer current,@RequestParam Integer size){
        IPage<CooperateArticle> iPage=new Page<>(current,size);
        QueryWrapper<CooperateArticle> cooperateArticleQueryWrapper=new QueryWrapper<>();
        cooperateArticleQueryWrapper.eq("created_by",userId);
        IPage<CooperateArticle> res = cooperateArticleDao.selectPage(iPage, cooperateArticleQueryWrapper);
        Map<String,Object> data=new HashMap<>();
        data.put("records",res.getRecords());
        data.put("total",res.getTotal());
        data.put("current",res.getCurrent());
        return R.success(data);
    }

    @GetMapping("/details/{id}")
    public R getCooperateArticle(@PathVariable Integer id){
        CooperateArticle cooperateArticle = cooperateArticleDao.selectById(id);
        return R.success(cooperateArticle);
    }

}
