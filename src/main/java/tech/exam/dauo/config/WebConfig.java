package tech.exam.dauo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import tech.exam.dauo.security.IpAddressAccessControlInterceptor;
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(ipAddressAccessControlInterceptor())
                .addPathPatterns("/dauo/api/**");
    }

    @Bean
    public IpAddressAccessControlInterceptor ipAddressAccessControlInterceptor() {
        return new IpAddressAccessControlInterceptor();
    }
}
