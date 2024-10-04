package com.example.blogback.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("article")
public class Article {

    @TableId(type= IdType.AUTO)
    private Integer id;

    @TableField("user_id")
    private Integer userId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;

    private String title;

    private String content;

    private String url;

    private Integer status;

    @TableField("category_id")
    private Integer categoryId;

    @TableField("column_id")
    private Integer columnId;

    private String kind;

    private String brief;
}
