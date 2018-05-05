package com.newegg.ec.cache;

import com.newegg.ec.base.filter.AuthenticationFIlter;
import com.newegg.ec.base.util.Constant;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import javax.servlet.Filter;

/**
 * Created by jn50 on 2018/3/14.
 */
@EnableAutoConfiguration
@EnableMachineManager
@EnableTransactionManagement
public class MachineManagerServer {

    public static void main(String[] args) {
        Constant.APPNAME="MC";
        SpringApplication.run(MachineManagerServer.class, args);
    }

    @Bean
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
    }

}
