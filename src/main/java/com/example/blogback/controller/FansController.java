package com.example.blogback.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.blogback.common.R;
import com.example.blogback.dao.FansDao;
import com.example.blogback.domain.Fans;
import com.example.blogback.domain.other.UserDetails;
import com.example.blogback.domain.other.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@ResponseBody
@RequestMapping("/api/fans")
public class FansController {

    @Autowired
    private FansDao fansDao;

    @GetMapping("follow/{userId}")
    public R getMyFollows(@PathVariable Integer userId){
        QueryWrapper<Fans> fansQueryWrapper=new QueryWrapper<>();
        fansQueryWrapper.eq("fans_id",userId);

        ArrayList<UserDetails> list=new ArrayList<>();
        List<Fans> fans = fansDao.selectList(fansQueryWrapper);
        for (Fans fan : fans) {
            UserDetails userDetails=Common.getUserDetails(userId,fan.getUserId());
            list.add(userDetails);
        }

        return R.success(list);
    }

    @GetMapping("/fans/{userId}")
    public R getMyFans(@PathVariable Integer userId){
        QueryWrapper<Fans> fansQueryWrapper=new QueryWrapper<>();
        fansQueryWrapper.eq("user_id",userId);

        ArrayList<UserDetails> list=new ArrayList<>();
        List<Fans> fans = fansDao.selectList(fansQueryWrapper);
        for (Fans fan : fans) {
            UserDetails userDetails=Common.getUserDetails(userId,fan.getFansId());
            list.add(userDetails);
        }

        return R.success(list);
    }

    @PostMapping("/{userId}/{fansId}")
    public R addFans(@PathVariable Integer userId,@PathVariable Integer fansId){
        int insert = fansDao.insert(new Fans(null, fansId, userId));

        if(insert<=0) return R.error("添加失败");
        else return R.success("添加成功");
    }

    @DeleteMapping("/{userId}/{fansId}")
    public R deleteFans(@PathVariable Integer userId,@PathVariable Integer fansId){
        QueryWrapper<Fans> fansQueryWrapper=new QueryWrapper<>();
        fansQueryWrapper.eq("user_id",userId)
                .eq("fans_id",fansId);

        int i = fansDao.delete(fansQueryWrapper);

        if(i<=0) return R.error("删除失败");
        else return R.success("删除成功");
    }
}
