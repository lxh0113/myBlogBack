package com.example.blogback.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.blogback.common.R;
import com.example.blogback.dao.LabelDao;
import com.example.blogback.domain.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@ResponseBody
@RestController
@RequestMapping("/api/label")
public class LabelController {

    @Autowired
    private LabelDao labelDao;

    @GetMapping
    public R getAllLabels(){
        QueryWrapper<Label> labelQueryWrapper=new QueryWrapper<>();
        labelQueryWrapper.eq("user_id",1);

        List<Label> labels = labelDao.selectList(labelQueryWrapper);

        return R.success(labels);
    }

    @PostMapping
    public R addNewLabels(@RequestBody Label label){
        int insert = labelDao.insert(label);

        if(insert>0) return R.success("添加成功");
        else return R.error("添加失败");
    }

    @PutMapping
    public R changeLabel(@RequestBody Label label){
        int i = labelDao.updateById(label);

        if(i>0) return R.success("修改成功");
        else return R.error("修改失败");
    }

    @DeleteMapping("/{id}")
    public R deleteLabel(@PathVariable Integer id){
        int i = labelDao.deleteById(id);

        if(i>0) return R.success("删除成功");
        else return R.error("删除失败");
    }
}
