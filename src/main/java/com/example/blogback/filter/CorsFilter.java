package com.example.blogback.filter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 配置跨域
 */
@WebFilter(urlPatterns = {"/*"})
//@Order(-1)
@Component
public class CorsFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("实例化了");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("看看我被用了吗");

        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;
        String requestURI = request.getRequestURI();

        // 不使用*，自动适配跨域域名，避免携带Cookie时失效
        String origin = request.getHeader("Origin");
        if (StringUtils.isNotBlank(origin)) {
            response.setHeader("Access-Control-Allow-Origin", origin);
        }

        // 自适应所有自定义头
        String headers = request.getHeader("Access-Control-Request-Headers");
        System.out.println(request.getHeader("token"));
        System.out.println(request.getHeader("long_token"));

        if (StringUtils.isNotBlank(headers)) {
            response.setHeader("Access-Control-Allow-Headers", headers);
            response.setHeader("Access-Control-Expose-Headers", "*,long_token,token"); // 修改这行
        } else {
            response.setHeader("Access-Control-Allow-Headers", "Content-Type, token,long_token");
        }
        response.setHeader("Access-Control-Expose-Headers", "*,long_token,token");

        // 允许跨域的请求方法类型
        response.setHeader("Access-Control-Allow-Methods", "POST,GET,DELETE,OPTIONS,PUT");
        // 预检命令（OPTIONS）缓存时间，单位：秒
        response.setHeader("Access-Control-Max-Age", "3600");
        // 明确许可客户端发送Cookie，不允许删除字段即可
        response.setHeader("Access-Control-Allow-Credentials", "true");


        filterChain.doFilter(request, response);
    }

    private boolean isExcludedPath(String requestURI) {
        // 在这里添加判断逻辑，根据实际需求判断是否为排除路径
        return requestURI.startsWith("/api/pre");
    }

    @Override
    public void destroy() {

    }
}


//@WebFilter(urlPatterns = {"/**"})
//@Component
//public class CorsFilter implements Filter {
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//        System.out.println("实例化了");
//
//    }
//
//    @Override
//    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain) throws IOException, ServletException {
//        System.out.println("看看我被用了吗");
//
//        HttpServletResponse response = (HttpServletResponse) res;
//        HttpServletRequest request = (HttpServletRequest) req;
//
//        // 不使用*，自动适配跨域域名，避免携带Cookie时失效
//        String origin = request.getHeader("Origin");
//        if(StringUtils.isNotBlank(origin)) {
//            response.setHeader("Access-Control-Allow-Origin", origin);
//        }
//
//        // 自适应所有自定义头
//        String headers = request.getHeader("Access-Control-Request-Headers");
//        if(StringUtils.isNotBlank(headers)) {
//            response.setHeader("Access-Control-Allow-Headers", headers);
//            response.setHeader("Access-Control-Expose-Headers", "*,long_token,token"); // 修改这行
//        }
//        else response.setHeader("Access-Control-Allow-Headers","Content-Type, token,long_token");
//        //response.setHeader("Access-Control-Allow-Headers","Content-Type, token,long_token");
//        response.setHeader("Access-Control-Expose-Headers", "*,long_token,token");
//
//        // 允许跨域的请求方法类型
//        response.setHeader("Access-Control-Allow-Methods", "POST,GET,DELETE,OPTIONS,PUT");
//        // 预检命令（OPTIONS）缓存时间，单位：秒
//
//
//        response.setHeader("Access-Control-Max-Age", "3600");
//        // 明确许可客户端发送Cookie，不允许删除字段即可
//        response.setHeader("Access-Control-Allow-Credentials", "true");
//
//        filterChain.doFilter(request, response);
//    }
//
//    @Override
//    public void destroy() {
//
//    }
//}


