package tech.exam.dauo.security;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import tech.exam.dauo.config.ConfigProperties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class IpAddressAccessControlInterceptor implements HandlerInterceptor {
    @Autowired
    ConfigProperties configProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        long currentTime = System.currentTimeMillis();
        request.setAttribute("request_time", currentTime);

        String clientIP = getClientIpAddr(request);
        String[] ips = configProperties.getIp();

        boolean isAuth = false;
        for (String ip:ips) {
            if(clientIP.equals(ip)) isAuth = true;
        }

        if(!isAuth){
            throw new AccessDeniedException("허용되지 않은 IP 입니다.");
        }

        return true;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        long currentTime = System.currentTimeMillis();

        long beginTime = (long )request.getAttribute("request_time");

        long processedTime = currentTime - beginTime;

        log.info(String.format("request time :  %s , response time : %s , process time : %s", beginTime, currentTime , processedTime));
    }

    public String getClientIpAddr(HttpServletRequest request){
        String ip=request.getHeader("X-Forwarded-For");

    if(ip==null||ip.length()==0||"unknown".equalsIgnoreCase(ip)){
        ip=request.getHeader("Proxy-Client-IP");
    }
    if(ip==null||ip.length()==0||"unknown".equalsIgnoreCase(ip)){
        ip=request.getHeader("WL-Proxy-Client-IP");
    }
    if(ip==null||ip.length()==0||"unknown".equalsIgnoreCase(ip)){
        ip=request.getHeader("HTTP_CLIENT_IP");
    }
    if(ip==null||ip.length()==0||"unknown".equalsIgnoreCase(ip)){
        ip=request.getHeader("HTTP_X_FORWARDED_FOR");
    }
    if(ip==null||ip.length()==0||"unknown".equalsIgnoreCase(ip)){
        ip=request.getRemoteAddr();
    }

        return ip;
    }
}