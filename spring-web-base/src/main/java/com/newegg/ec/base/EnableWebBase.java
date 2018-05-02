package com.newegg.ec.base;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;

import java.lang.annotation.*;

/**
 * Created by jn50 on 2018/5/2.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ComponentScan(basePackages={"com.newegg.ec.base"})
@MapperScan(basePackages={"com.newegg.ec.base.dao.mysql"})
public @interface EnableWebBase {
}
