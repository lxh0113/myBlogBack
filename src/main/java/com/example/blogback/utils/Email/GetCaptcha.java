package com.example.blogback.utils.Email;

public class GetCaptcha {

    //   private Socket socket;
    public String captcha;
    public String email;

    public GetCaptcha() {
    }

    public void getCaptcha(String email) throws Exception {
        this.email = email;
        Captcha();
    }

    private void Captcha() throws Exception {
        System.out.println(email);
        //SendCaptcha sendCaptcha = new SendCaptcha();
        //captcha = sendCaptcha.getYzm(email);
    }

}
