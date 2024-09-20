package com.example.blogback.utils.Email;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Random;


public class SendCaptcha {
    public static String precaptcha;
    public static String email;
    public String yzm ;

    public static String getcaptcha() {
        Random captcha = new Random();
        String captcha1 = "1234567890abcdefghijklmnopqrstuvwxwzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder captcha2 = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int a = captcha.nextInt(58);
            captcha2.append(captcha1.charAt(a));
        }
        System.out.println(captcha2);
        precaptcha = captcha2.toString();
        return captcha2.toString();
    }

    public String getYzm(String email) throws Exception {
        SendCaptcha.email = email;
        //创建对象
        Properties pro = new Properties();
        //设置主机
        pro.setProperty("mail.host", "smtp.qq.com");
        //设置传输协议
        pro.setProperty("mail.transport.protocol", "smtp");
        //设置允许邮箱授权认证
        pro.setProperty("mail.smtp.auth", "true");
        pro.setProperty("mail.smtp.ssl.enable","true");
        InternetAddress receive = new InternetAddress(email);
        //uauaryxehzhacjai
        //邮箱授权认证，认证器

        //创建认证器对象
        Auth auth = new Auth();
        //获取session对象
        Session session = Session.getInstance(pro, auth);

        //获取连接
        Transport ts = session.getTransport();
        //连接服务器
        ts.connect("smtp.qq.com", "3358654275@qq.com", "nnanlekngblpdajc");
        //创建邮箱对象
        MimeMessage message = new MimeMessage(session);
        //设置发件人地址
        message.setFrom(new InternetAddress("3358654275@qq.com"));
        //收件人地址
        message.setRecipient(Message.RecipientType.TO, receive);
        //群发
        //设置邮件主题
        message.setSubject("验证码");
        yzm=getcaptcha();
        //设置邮件内容
        message.setContent("要完成你的 博客 帐户的设置，确保这是你的电子邮件地址。" + "\n" +
                "要验证电子邮件地址，请使用此验证码: " + yzm + "\n" +
                "如果你没有请求此代码，可放心忽略这封电子邮件。别人可能错误地键入了你的电子邮件地址，并且为了你的账号安全请勿将此验证码发给其他人\n"
                +"此验证码5分钟内有效"+"\n"+
                "谢谢!" + "\n" +
                "饿了么", "text/html;charset=utf-8");
        //发送邮件
        ts.sendMessage(message, message.getAllRecipients());
        //关闭连接
        ts.close();
        System.out.println("发送完成");
        return yzm;
    }
}
