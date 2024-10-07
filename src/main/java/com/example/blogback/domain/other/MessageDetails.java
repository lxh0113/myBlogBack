package com.example.blogback.domain.other;

import com.example.blogback.domain.Message;
import com.example.blogback.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDetails {

    private Message message;

    private User sender;

    private User receiver;
}
