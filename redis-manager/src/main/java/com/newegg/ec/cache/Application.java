package com.newegg.ec.cache;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by gl49 on 2018/4/20.
 */
@SpringBootApplication
@MapperScan(basePackages={"com.newegg.ec.cache.dao"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
