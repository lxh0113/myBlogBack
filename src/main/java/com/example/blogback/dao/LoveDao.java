package com.example.blogback.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.blogback.domain.Love;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LoveDao extends BaseMapper<Love> {
}
