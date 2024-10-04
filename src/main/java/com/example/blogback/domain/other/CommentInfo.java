package com.example.blogback.domain.other;

import com.example.blogback.domain.Comment;
import com.example.blogback.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentInfo {
    
    private Comment comment;

    private User user;

    private String receiverName;

//    子数组
    private List<CommentInfo> sonComments;
}
