package tech.exam.dauo.security;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import tech.exam.dauo.config.ConfigProperties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class IpAddressAccessControlInterceptor implements HandlerInterceptor {
    @Autowired
    ConfigProperties configProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String clientIP = getClientIpAddr(request);
        String[] ips = configProperties.getIp();

        for (String ip:ips) {
            if(clientIP.equals(ip)) return true;
        }

        return false;
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