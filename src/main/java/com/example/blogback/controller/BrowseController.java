package com.example.blogback.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.blogback.common.R;
import com.example.blogback.dao.BrowseDao;
import com.example.blogback.domain.Browse;
import com.example.blogback.domain.other.BrowseGroup;
import com.example.blogback.domain.other.BrowseHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@ResponseBody
@RequestMapping("/api/browse")
public class BrowseController {

    @Autowired
    private BrowseDao browseDao;

    @PostMapping
    public R addBrowse(@RequestBody Browse browse){
        browse.setDate(new Date());
        int insert = browseDao.insert(browse);

        if(insert>0) return R.success(browse);
        else return R.error("失败");
    }

    @GetMapping("/history/{userId}")
    public R getHistory(@PathVariable Integer userId){
        ArrayList<BrowseGroup> browseGroups = Common.getBrowseHistory(userId);

        return R.success(browseGroups);
    }

}
