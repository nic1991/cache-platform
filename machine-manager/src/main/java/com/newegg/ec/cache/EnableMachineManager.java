package com.newegg.ec.cache;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import java.lang.annotation.*;

/**
 * Created by jn50 on 2018/5/2.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ComponentScan(basePackages = {"com.newegg.ec.base","com.newegg.ec.cache"})
@MapperScan(basePackages = {"com.newegg.ec.base.dao.mysql","com.newegg.ec.cache.dao"})
public @interface EnableMachineManager {
}
