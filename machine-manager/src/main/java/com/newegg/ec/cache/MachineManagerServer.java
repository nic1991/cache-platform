package com.newegg.ec.cache;

import com.newegg.ec.base.filter.AuthenticationFIlter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.servlet.Filter;


/**
 * Created by jn50 on 2018/3/14.
 */
@SpringBootApplication
 @ComponentScan(basePackages={"com.newegg.ec.base","com.newegg.ec.cache"})
@MapperScan(basePackages={"com.newegg.ec.base.dao.mysql","com.newegg.ec.cache.dao"})
@EnableScheduling //开启定时job
@EnableTransactionManagement
public class MachineManagerServer {

    public static void main(String[] args) {
        SpringApplication.run(MachineManagerServer.class, args);
    }

   /* @Bean
    public FilterRegistrationBean someFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(authenticationFilter());
        registration.addUrlPatterns("/rest");
        registration.addInitParameter("paramName", "paramValue");
        registration.setName("sessionFilter");
        return registration;
    }

    @Bean(name = "sessionFilter")
    public Filter authenticationFilter() {
        return new AuthenticationFIlter();
    }*/

}
