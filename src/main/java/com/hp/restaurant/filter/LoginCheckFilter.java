package com.hp.restaurant.filter;

import com.alibaba.fastjson.JSON;
import com.hp.restaurant.common.BaseContext;
import com.hp.restaurant.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;

        // do not need process these urls
        String requestURI = request.getRequestURI();
        String[] urls = new String[] {
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/frontend/**"
        };
        boolean check = check(urls, requestURI);

        if(check) {
            filterChain.doFilter(request, response);
            return;
        }

        // if it needs to process, check if user login
        if(request.getSession().getAttribute("employee") != null) {
            Long id = (Long)request.getSession().getAttribute("employee");
            BaseContext.setCurrentId(id);
            filterChain.doFilter(request, response);
            return;
        }

        // if user not login, return login page
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
    }
    
    public boolean check(String[] urls, String requestURI) {
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestURI);
            if(match) return true;
        }
        return false;
    }
}
