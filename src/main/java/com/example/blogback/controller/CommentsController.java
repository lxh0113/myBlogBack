package com.example.blogback.controller;

import com.example.blogback.common.R;
import com.example.blogback.dao.CommentDao;
import com.example.blogback.domain.Comment;
import com.example.blogback.domain.other.CommentInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@ResponseBody
@RestController
@RequestMapping("/api/comments")
public class CommentsController {

    @Autowired
    private CommentDao commentDao;

    @GetMapping("/{articleId}")
    public R getComments(@PathVariable Integer articleId){
        ArrayList<CommentInfo> allComments = Common.getAllComments(articleId);
        return R.success(allComments);
    }

    @PostMapping
    public R addComment(@RequestBody Comment comment)
    {
        int insert = commentDao.insert(comment);
        if(insert<=0) return R.error("插入失败");
        else return R.success(comment);
    }

    @DeleteMapping("/{id}")
    public R deleteComment(@PathVariable Integer id){
        System.out.println(id);
        int i = commentDao.deleteById(id);

        if(i<=0) return R.error("删除失败");
        else return R.success("删除成功");
    }



}
