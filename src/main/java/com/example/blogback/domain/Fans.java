package com.example.blogback.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("fans")
public class Fans {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("fans_id")
    private Integer fansId;

    @TableField("user_id")
    private Integer userId;
}
