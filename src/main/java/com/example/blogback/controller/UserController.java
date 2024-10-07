package com.example.blogback.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.blogback.common.R;
import com.example.blogback.dao.UserDao;
import com.example.blogback.domain.User;
import com.example.blogback.domain.other.UserDetails;
import com.example.blogback.domain.other.UserInfo;
import com.example.blogback.utils.Email.EmailCaptcha;
import com.example.blogback.utils.Email.ManageCaptcha;
import com.example.blogback.utils.Email.SendCaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

    @GetMapping("/{userId}")
    public R getUser(@PathVariable Integer userId){
        User user=Common.getUserById(userId);
        user.setPassword("");

        return R.success(user);
    }

    @GetMapping("/info/{userId}")
    public R getUserInfo(@PathVariable Integer userId){
        UserInfo userInfo=Common.getUserInfoById(userId);

        return R.success(userInfo);
    }

    @PostMapping("/change")
    public R changeProfile(@RequestBody User user){

        if(user.getPassword()!=null||user.getPassword()!=""){
            User oldUser=userDao.selectById(user.getId());
            user.setPassword(oldUser.getPassword());
        }

        int update = userDao.updateById(user);
        if(update>0) return R.success(user);
        else return R.error("修改失败");
    }

    @GetMapping("/searchInput")
    public R searchUser(@RequestParam String searchInput,@RequestParam  Integer userId){

        QueryWrapper<User> userQueryWrapper=new QueryWrapper<>();
        userQueryWrapper.like("id",searchInput)
                .or()
                .like("intro",searchInput)
                .or()
                .like("email",searchInput)
                .ne("id",userId)
        ;

        List<User> users = userDao.selectList(userQueryWrapper);

        ArrayList<UserDetails> userDetails=new ArrayList<>();
        for (User user : users) {
            userDetails.add(Common.getUserDetails(userId,user.getId()));
        }

        return R.success(userDetails);
    }


    @GetMapping("/admin")
    public R adminGetUserInfo(@RequestParam Integer userId,@RequestParam String word){
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq(userId!=0&&userId!=null,"id",userId)
                .like(word!=null&&word!="","username",word);


        List<User> users = userDao.selectList(queryWrapper);

        ArrayList<UserDetails> arrayList=new ArrayList<>();
        for (User user : users) {
            arrayList.add(new UserDetails(user,Common.getUserInfoById(user.getId())));
        }

        return R.success(arrayList);
    }


}
