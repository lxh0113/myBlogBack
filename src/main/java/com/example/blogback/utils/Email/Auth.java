package com.example.blogback.utils.Email;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class Auth extends Authenticator {
    //认证器类
    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication("3358654275@qq.com", "nnanlekngblpdajc");
    }

}
