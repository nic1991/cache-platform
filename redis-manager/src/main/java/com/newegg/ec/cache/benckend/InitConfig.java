package com.newegg.ec.cache.benckend;

import com.newegg.ec.cache.core.mysql.MysqlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gl49 on 2018/4/21.
 */
@Component
public class InitConfig implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        initMysqlTable();
    }

    /**
     * 初始化 mysql 表
     */
    public void initMysqlTable(){
        List<String> packages = new ArrayList();
        packages.add( "com.newegg.ec.cache" );
        List<String> sqlList = MysqlUtil.initMysqlTable( packages );
        for( String createSql : sqlList ){
            jdbcTemplate.execute( createSql );
        }
    }
}
