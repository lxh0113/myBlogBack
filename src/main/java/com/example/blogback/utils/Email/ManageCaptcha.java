package com.example.blogback.utils.Email;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

public class ManageCaptcha {
    private static ConcurrentHashMap<String, EmailCaptcha> map = new ConcurrentHashMap<>();

    public static EmailCaptcha GetcaptchaByEmail(String email, Date date)
    {
        //需要传递参数时间，因此来判断是否过期
        EmailCaptcha newemailcaptcha=map.get(email);

        if(newemailcaptcha==null) return newemailcaptcha;

        if((date.getTime()-newemailcaptcha.getDate().getTime())>1000*60*5)
        {
            //此时验证码过期了，直接移除此邮箱的验证码
            map.remove(email);
            //置空，因为后续需要返回
            newemailcaptcha.setCaptcha("");
            newemailcaptcha.setEmail("");
        }
        return newemailcaptcha;
    }
    public static void AddCaptchaByEmail(EmailCaptcha emailCaptcha)
    {
        if(map.containsKey(emailCaptcha.getEmail()))
        {
            //如果有则置空
            map.remove(emailCaptcha.getEmail());
            //放入
            map.put(emailCaptcha.getEmail(),emailCaptcha);
        }
        else
        {
            map.put(emailCaptcha.getEmail(),emailCaptcha);
        }
    }
    public static void RemoveCaptchaByEmail(String email)
    {
        map.remove(email);
    }
}
