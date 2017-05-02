package com.lync.core.filter;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by breeze on 2016/8/26.
 */
@Component
@WebFilter(filterName="CORSFilter",urlPatterns="/*")
public class CORSFilter implements Filter {
    private static final Logger L = Logger.getLogger(CORSFilter.class);
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
               L.info("过滤器启动");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS,PUT,DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
//        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization, Accept,X-Requested-With,sessionKey");
        if(request.getMethod().equalsIgnoreCase("OPTIONS")){
            response.setHeader("Access-Control-Allow-Headers",request.getHeader("Access-Control-Request-Headers"));
        }
        chain.doFilter(req, res);
    }

    @Override
    public void destroy() {

    }
}
