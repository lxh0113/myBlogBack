package com.example.blogback.controller;

import com.example.blogback.common.R;
import com.example.blogback.dao.MessageDao;
import com.example.blogback.domain.Comment;
import com.example.blogback.domain.Message;
import com.example.blogback.domain.other.MessageCommon;
import com.example.blogback.domain.other.MessageDetails;
import com.example.blogback.domain.other.MessageFriend;
import com.example.blogback.domain.other.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@ResponseBody
@RequestMapping("/api/message")
public class MessageController {

    @Autowired
    private MessageDao messageDao;

    @GetMapping("/{userId}")
    public R getNewFans(@PathVariable Integer userId){
        ArrayList<UserDetails> fans = Common.getFans(userId);

        return R.success(fans);
    }

    @GetMapping("/like/{userId}")
    public R getLikes(@PathVariable Integer userId){
        ArrayList<MessageCommon> likes = Common.getLikes(userId);
        return R.success(likes);
    }

    @GetMapping("/collect/{userId}")
    public R getCollect(@PathVariable Integer userId){
        ArrayList<MessageCommon> collects = Common.getCollects(userId);
        return R.success(collects);
    }

    @GetMapping("/comments/{userId}")
    public R getComments(@PathVariable Integer userId){
        ArrayList<MessageCommon> comments = Common.getComments(userId);
        return R.success(comments);
    }

    @GetMapping("/myMessage/{userId}")
    public R getMessages(@PathVariable Integer userId){
        ArrayList<MessageFriend> messages = Common.getMessages(userId);
        return R.success(messages);
    }

    @PostMapping
    public R sendMessage(@RequestBody Message message){
        int insert = messageDao.insert(message);

        if(insert>0) return R.success(message);
        else return R.error("发送失败");
    }
}
