package com.newegg.ec.cache.dao.impl;

import com.newegg.ec.cache.core.mysql.MysqlUtil;
import com.newegg.ec.cache.model.NodeInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by gl49 on 2018/4/21.
 */
@Component
public class NodeInfoDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean createTable(String tableName) {
        boolean res = true;
        try {
            Class claz = NodeInfo.class;
            MysqlUtil mysqlUtil = new MysqlUtil();
            String createTable = mysqlUtil.createTableSql( claz, tableName);
            jdbcTemplate.execute( createTable );
        }catch (Exception e){
            res = false;
        }
        return res;
    }


}
