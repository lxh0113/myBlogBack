package com.example.blogback.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.blogback.common.R;
import com.example.blogback.dao.LoveDao;
import com.example.blogback.domain.Love;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@ResponseBody
@RestController
@RequestMapping("/api/love")
public class LoveController {

    @Autowired
    private LoveDao loveDao;

    @PostMapping
    public R addLove(@RequestBody Love love){
        int insert = loveDao.insert(love);

        if(insert>0) return R.success(love);
        else return R.error("点赞失败");

    }

    @DeleteMapping("/{userId}/{articleId}")
    public R deleteLove(@PathVariable Integer userId,@PathVariable Integer articleId){

        QueryWrapper<Love> loveQueryWrapper=new QueryWrapper<>();
        loveQueryWrapper.eq("user_id",userId)
                .eq("article_id",articleId);

        int delete = loveDao.delete(loveQueryWrapper);
        if(delete>0) return R.success("删除成功");
        else return R.error("取消点赞失败");
    }

}
