package com.example.blogback.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("cooperate_article")
public class CooperateArticle {
    @TableId(type=IdType.AUTO)
    private Integer id;
    private String title;
    private String content;
    private Date time;
    private String version;
    @TableField("saved_by")
    private Integer savedBy;
    @TableField("created_by")
    private Integer createdBy;
    private String role;
}
