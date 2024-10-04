package com.example.blogback.domain.other;

import com.example.blogback.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {

    private Integer articles;

    private Integer love;

    private Integer follow;

    private Integer fans;

    private Boolean isFollow;
}
