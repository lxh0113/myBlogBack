package com.example.blogback.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.blogback.common.R;
import com.example.blogback.dao.ColumnDao;
import com.example.blogback.domain.Column;
import com.example.blogback.domain.Comment;
import com.example.blogback.domain.other.SearchColumn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@ResponseBody
@RestController
@RequestMapping("/api/column")
public class ColumnController {

    @Autowired
    private ColumnDao columnDao;

    @GetMapping("/{userId}")
    public R getColumns(@PathVariable Integer userId){
        QueryWrapper<Column> columnQueryWrapper=new QueryWrapper<>();
        columnQueryWrapper.eq("user_id",userId);

        List<Column> columns = columnDao.selectList(columnQueryWrapper);

        return R.success(columns);
    }


    @PostMapping
    public R addColumn(@RequestBody Column column){
        int insert = columnDao.insert(column);
        if(insert>0) return R.success("新增成功");
        else return R.error("新增失败");
    }

    @PutMapping
    public R changeColumn(@RequestBody Column column){
        int update = columnDao.updateById(column);
        if(update>0) return R.success("修改成功");
        else return R.error("修改失败");
    }

    @DeleteMapping("/{id}")
    public R deleteColumn(@PathVariable Integer id)
    {
        int i = columnDao.deleteById(id);

        if(i>0) return R.success("删除成功");
        else  return R.error("删除失败");
    }

    @GetMapping("/searchInput")
    public R searchColumn(@RequestParam String searchInput){

        ArrayList<SearchColumn> searchColumns = Common.searchColumn(searchInput);

        return R.success(searchColumns);
    }
}
