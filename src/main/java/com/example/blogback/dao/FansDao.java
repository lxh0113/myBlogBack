package com.example.blogback.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.blogback.domain.Fans;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FansDao extends BaseMapper<Fans> {
}
