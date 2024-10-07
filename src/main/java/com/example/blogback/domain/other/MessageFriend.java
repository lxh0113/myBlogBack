package com.example.blogback.domain.other;

import com.example.blogback.domain.Message;
import com.example.blogback.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageFriend {

    private User user;

    private List<Message> messages;
}
