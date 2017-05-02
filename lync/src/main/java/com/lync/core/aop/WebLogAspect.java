package com.lync.core.aop;


import com.lync.core.constant.Const;
import com.lync.core.shiro.ShiroKit;
import com.lync.core.toolbox.kit.JsonKit;
import com.lync.core.toolbox.result.Result;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@Aspect
@Component

public class WebLogAspect {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private static final ThreadLocal<Map> map = new ThreadLocal<Map>( ) {
        @Override
        protected Map initialValue() {
            return new LinkedHashMap( );
        }
    };
    @Pointcut("execution(public * com.lync.controller..*.*(..))")
    public void webLog(){

    }
    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String uri = request.getRequestURI();
        map.get( ).put("uri",uri);
        String method = joinPoint.getSignature().getName();
        //获取参数
        Map<String, String[]> parameterMap = request.getParameterMap();
        map.get( ).put("requestParam",JsonKit.toJsonObj(parameterMap));
        WebLogAspect.map.get( ).put( "requestBody",JsonKit.toJsonObj(joinPoint.getArgs()));
        map.get( ).put("clientIp",request.getRemoteAddr());
        map.get( ).put("server", request.getLocalAddr());
        map.get( ).put("serverPort",request.getServerPort());
        map.get( ).put("requestMethod",request.getMethod().toUpperCase());
        map.get( ).put("method",method);
    }

    @AfterReturning(returning = "result", pointcut = "webLog()")
    public void doAfterReturning(Result result) throws Throwable {
        map.get().put("user", ShiroKit.getUser().getUsername());
        map.get().put("response", result);
        map.get().put("logTime", Const.TIMESTAMP);
        logger.info("======="+JsonKit.toJson(map.get()));
    }


}
