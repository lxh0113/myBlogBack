package com.example.blogback.utils;

import io.jsonwebtoken.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class JwtUtils {

    private static final long time=1000*60*60*24*2;

    private static final long long_time=1000*60*60*24*5;

//    byte[] keyBytes = Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded();
    private static final String signature="mistletoeKingNoxHeliosAresBearPuddingcdnbsaufbdajskfhiusabdfjnasbdhisabdjsakbdjsxnausofhdcisbfcdhsgfbcvdhskbcdsiyfgchdsbfdsyifgdsbfhdsbfayifegyfbdhzxcblhkbduisf";

    public static String getUseridBytoken(String token)
    {
        String userid=null;
        JwtParser jwtParser=Jwts.parser();
        Jws<Claims> claimsJws = jwtParser.setSigningKey(signature).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();

        userid= (String) claims.get("userid");
        return userid;
    }

    public static int getIsStoreByToken(String token)
    {
        int isadmin=0;
        JwtParser jwtParser=Jwts.parser();
        Jws<Claims> claimsJws = jwtParser.setSigningKey(signature).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();

        isadmin= (int) claims.get("status");
        return isadmin;
    }

    public static Map<String,String> getToken(String userid,int isStore)
    {
        Map<String,String> tokenmap=new HashMap<>();
        JwtBuilder jwtBuilder= Jwts.builder();
        String token=jwtBuilder
                . setHeaderParam("type","JWT")
                .setHeaderParam("alg","HS256")
//                载荷
                .claim("userid",userid)
                .claim("status",isStore)
                .setSubject("login")
                .setExpiration(new Date(System.currentTimeMillis()+time))
                .setId(UUID.randomUUID().toString())
//                签名
                .signWith(SignatureAlgorithm.HS256,signature)
//               拼接
                .compact();

        String long_token=jwtBuilder
                . setHeaderParam("type","JWT")
                .setHeaderParam("alg","HS256")
//                载荷
                .claim("userid",userid)
                .claim("status",isStore)
                .setSubject("login")
                .setExpiration(new Date(System.currentTimeMillis()+long_time))
                .setId(UUID.randomUUID().toString())
//                签名
                .signWith(SignatureAlgorithm.HS256,signature)
//               拼接
                .compact();

        tokenmap.put("token",token);
        tokenmap.put("long_token",long_token);
        return tokenmap;
    }

    public static int istoken(String token,String long_token)
    {
        if(long_token==null||long_token.equals(""))
        {
//            需要重新设置token值
            return 0;
        }
        Jws<Claims> claimsJws=null;
        try{
            claimsJws= Jwts.parser().setSigningKey(signature).parseClaimsJws(long_token);

            Claims body = claimsJws.getBody();
            Integer status= (Integer) body.get("status");
            String userId= (String) body.get("userid");
            System.out.println(userId+"我们看看是否被冻结");

//            if(status>=20) throw new Exception("账户被冻结");


        }catch (ExpiredJwtException  e)
        {
            e.printStackTrace();
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }

        if(token==null||token.equals(""))
        {
            return 1;
        }
        try{
            claimsJws= Jwts.parser().setSigningKey(signature).parseClaimsJws(token);
        }catch (JwtException  e) {
            e.printStackTrace();
            return 1;
        }
        return 2;
    }

    private boolean isExcludedPath(String requestURI) {
        // 在这里添加判断逻辑，根据实际需求判断是否为排除路径
        return requestURI.startsWith("/api/pre/");
    }
//
//    public String jwt(String userid,int isadmin)
//    {
//        //要设置俩个token，返回一个map吧，long_token和token
//        //然后就是把这个map返回到map里面去
//        JwtBuilder jwtBuilder= Jwts.builder();
//        String jwtToken=jwtBuilder
////            这里是头
//                . setHeaderParam("typ","JWT")
//                .setHeaderParam("alg","HS256")
////            这里是载荷
//                .claim("userid",userid)
//                .claim("isadmin",isadmin)
//                .setSubject("login")
//                .setExpiration(new Date(System.currentTimeMillis()+time))
//                .setId(UUID.randomUUID().toString())
////            这里是签名部分
//                .signWith(SignatureAlgorithm.HS256,signature)
////          这里是拼接
//                .compact();
//
//        System.out.println(jwtToken);
//        return jwtToken;
//    }
//
//    public static void main(String[] args) {
//        demo1 d=new demo1();
//        String token=d.jwt("1234567890",1);
//
//        System.out.println(token);
//
//        System.out.println(d.parse(token));
//    }
//
//    public boolean parse(String token)
//    {
////        String token="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6InRvbSIsInJvbGUiOiJhZG1pbiIsInN1YiI6ImFkbWluLXRlc3QiLCJleHAiOjE2ODk0OTE2ODAsImp0aSI6IjFlNmEzZGY5LTJjMGUtNDUxYS05OTEwLTEwMjM3YjE1MmNiOSJ9.R6_AziwTl6G1zoHR10r_Z26qPTfq8uL5VOKTSvrfAYs";
//        JwtParser jwtParser=Jwts.parser();
//        Jws<Claims> claimsJws = jwtParser.setSigningKey(signature).parseClaimsJws(token);
//        Claims claims = claimsJws.getBody();
//        System.out.println(claims.get("username"));
//        System.out.println(claims.get("role"));
//        System.out.println(claims.get("id"));
//        System.out.println(claims.getSubject());
//        System.out.println(claims.getExpiration());
//
//        Date date=claims.getExpiration();
//
//        System.out.println(Jwts.parser().isSigned(token));
//
//        return new Date(System.currentTimeMillis()).after(date);
//        //返回时间是否在之后
//    }
}
