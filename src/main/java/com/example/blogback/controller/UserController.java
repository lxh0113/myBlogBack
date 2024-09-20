package com.example.blogback.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.blogback.common.R;
import com.example.blogback.dao.UserDao;
import com.example.blogback.domain.User;
import com.example.blogback.utils.Email.EmailCaptcha;
import com.example.blogback.utils.Email.ManageCaptcha;
import com.example.blogback.utils.Email.SendCaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Objects;

@ResponseBody
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserDao userDao;

    @PostMapping("/login")
    public R login(@RequestParam Integer id,@RequestParam String password){

        System.out.println(id+','+password);

        User user=userDao.selectById(id);

        if(user==null)
        {
            return R.error("该用户不存在");
        }
        else if(!user.getPassword().equals(password))
        {
            return R.error("密码错误");
        }

        return R.success(user);
    }

    @GetMapping("/getCaptcha/{email}")
    public  R getCaptcha(@PathVariable String email){

        System.out.println(email);

        SendCaptcha sendCaptcha=new SendCaptcha();
        String yzm=null;
        try {
            yzm = sendCaptcha.getYzm(email);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        EmailCaptcha emailCaptcha=new EmailCaptcha(email,yzm,new Date());
        ManageCaptcha.AddCaptchaByEmail(emailCaptcha);

        return R.success("发送成功");
    }

    @PostMapping("/register")
    public R register(@RequestParam String email,@RequestParam String password,@RequestParam String captcha ){

        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.select().eq("email",email);

        User user=userDao.selectOne(queryWrapper);

        if(user!=null){
            return R.error("已有账号！");
        }

        EmailCaptcha emailCaptcha=ManageCaptcha.GetcaptchaByEmail(email,new Date());

        System.out.println(captcha);
        System.out.println(emailCaptcha.getCaptcha());

        if(emailCaptcha!=null && emailCaptcha.getCaptcha().equals(captcha))
        {
            User newUser=new User(null,"博客小兵",password,0,null,0,"这个人很懒，什么都没用留下",email);

            int k=userDao.insert(newUser);

            if(k>0)
            {
                return R.success(newUser);
            }
            else return R.error("注册失败");
        }
        else {
            return R.error("验证码错误");
        }

    }
}
