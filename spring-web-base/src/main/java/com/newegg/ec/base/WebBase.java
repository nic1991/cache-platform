package com.newegg.ec.base;

import com.newegg.ec.base.filter.AuthenticationFIlter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import javax.servlet.Filter;


/**
 * Created by jn50 on 2018/1/27.
 */
@SpringBootApplication
@MapperScan("com.newegg.ec.base.dao.mysql")
public class WebBase {

    public static void main(String[] args) {
        SpringApplication.run(WebBase.class, args);
    }

    @Bean
    public FilterRegistrationBean someFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(authenticationFilter());
        registration.addUrlPatterns("/rest/*");
        registration.addInitParameter("paramName", "paramValue");
        registration.setName("sessionFilter");
        return registration;
    }

    @Bean(name = "sessionFilter")
    public Filter authenticationFilter() {
        return new AuthenticationFIlter();
    }
}
