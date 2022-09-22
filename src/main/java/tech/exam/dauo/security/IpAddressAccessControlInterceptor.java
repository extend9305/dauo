package tech.exam.dauo.security;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import tech.exam.dauo.config.ConfigProperties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class IpAddressAccessControlInterceptor implements HandlerInterceptor {
    @Autowired
    ConfigProperties configProperties;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info(request.getRemoteAddr());

        System.out.println(configProperties.getIp());

        //List<String> list = configProperties.getIp();


        return true;
    }
}