package com.example.blogback.utils.Email;

import java.util.Date;

public class EmailCaptcha {
    private String email;
    private String captcha;
    private Date date;

    public EmailCaptcha(String email, String captcha, Date date) {
        this.email = email;
        this.captcha = captcha;
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
