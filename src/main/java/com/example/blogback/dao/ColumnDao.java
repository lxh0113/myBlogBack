package com.example.blogback.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.blogback.domain.Column;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ColumnDao extends BaseMapper<Column> {
}
