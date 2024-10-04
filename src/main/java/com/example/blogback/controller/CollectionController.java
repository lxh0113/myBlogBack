package com.example.blogback.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.blogback.common.R;
import com.example.blogback.dao.CollectionDao;
import com.example.blogback.domain.Collection;
import com.example.blogback.domain.other.CollectionContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@ResponseBody
@RequestMapping("/api/collection")
public class CollectionController {

    @Autowired
    private CollectionDao collectionDao;

    @PostMapping
    public R addCollection(@RequestBody Collection collection){
        int insert = collectionDao.insert(collection);

        if(insert>0) return R.success(collection);
        else return R.error("添加失败");
    }

    @DeleteMapping("/{id}")
    public R deleteCollection(@PathVariable Integer id){
        int i = collectionDao.deleteById(id);

        if(i>0) return R.success("删除成功");
        else return R.error("删除失败");
    }

    @PutMapping
    public R changeCollection(@RequestBody Collection collection){
        int i = collectionDao.updateById(collection);

        if(i>0) return R.success(collection);
        else return R.error("修改失败");
    }

    @GetMapping("/{userId}")
    public R getCollectionByUserId(@PathVariable Integer userId){
        QueryWrapper<Collection> collectionQueryWrapper=new QueryWrapper<>();
        collectionQueryWrapper.eq("user_id",userId);

        List<Collection> collections = collectionDao.selectList(collectionQueryWrapper);

        return R.success(collections);
    }

    @GetMapping("/content/{userId}")
    public R getContentCollection(@PathVariable Integer userId){
        ArrayList<CollectionContent> contentCollections = Common.getContentCollections(userId);

        return R.success(contentCollections);

    }

}
