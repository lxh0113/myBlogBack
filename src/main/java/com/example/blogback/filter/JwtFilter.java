package com.example.blogback.filter;

import com.example.blogback.utils.JwtUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


@WebFilter(
//        urlPatterns = {"/api/category/*", "/api/user/*", "/api/store/*", "/api/goods/*",
//                "/api/flavors/*", "/api/district/*",
//                "/api/collection/*", "/api/comments/*", "/api/message/*",
//                "/api/shopcart/*","/api/manage/*"},
        initParams = @WebInitParam(name = "excludedPaths", value = "/api/pre/*")
)
public class JwtFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {
        //在这里判断token值即可
        System.out.println("判断token值");

//        HttpServletRequest request = (HttpServletRequest) req;

        try {
            HttpServletRequest request=(HttpServletRequest)servletRequest;
            HttpServletResponse response= (HttpServletResponse) servletResponse;
            String requestURI = request.getRequestURI();

            if (isExcludedPath(requestURI)) {
//                System.out.println("登录操作不管");
                // 如果是排除路径，直接放行，不调用chain.doFilter(request, response)
                filterChain.doFilter(request, response);
                return;
            }

            String token=request.getHeader("token");
            String long_token=request.getHeader("long_token");

            System.out.println(token);
            System.out.println(long_token);

//            String token=request.getHeader("token");
//            String long_token=request.getHeader("long_token");
            int res=0;
            try {
                res=JwtUtils.istoken(token,long_token);
            }catch (Exception e)
            {
                System.out.println("过期了,所以不执行");
                e.printStackTrace();
                System.out.println(res);
                throw e;
            }
            if(res==-1)
            {
                System.out.println("该用户已经被冻结");
                response.setStatus(401);
//                response.sendRedirect("http://localhost:8081/login");
            }
            if(res==0)
            {
                //token已经过期
                System.out.println("token已经过期");
                response.setStatus(298);
//                response.sendRedirect("http://localhost:8081/login");
                response.getWriter().write("登录过期，请重试");
            }
            else if(res==1)
            {
//            短的过期了需要刷新
                System.out.println("需要刷新token");
                String userid=JwtUtils.getUseridBytoken(request.getHeader("long_token"));
                int isadmin=JwtUtils.getIsStoreByToken(request.getHeader("long_token"));

                Map<String,String> map=JwtUtils.getToken(userid,isadmin);
                response.setHeader("token", (String) map.get("token"));
                response.setHeader("long_token", (String) map.get("long_token"));

                filterChain.doFilter(request,response);
            }
            else
            {
//            继续执行操作
                filterChain.doFilter(request,response);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {

    }

    private boolean isExcludedPath(String requestURI) {
        // 在这里添加判断逻辑，根据实际需求判断是否为排除路径
        return requestURI.startsWith("/api/pre/");
    }

}

