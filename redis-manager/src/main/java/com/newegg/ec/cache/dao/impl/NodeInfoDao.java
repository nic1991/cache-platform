package com.newegg.ec.cache.dao.impl;

import com.newegg.ec.cache.core.mysql.MysqlTable;
import com.newegg.ec.cache.core.mysql.MysqlUtil;
import com.newegg.ec.cache.dao.INodeInfoDao;
import com.newegg.ec.cache.model.NodeInfo;
import com.newegg.ec.cache.model.NodeInfoReqParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by gl49 on 2018/4/21.
 */
@Component
public class NodeInfoDao implements INodeInfoDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean createTable(String tableName) {
        boolean res = true;
        try {
            Class claz = NodeInfo.class;
            MysqlTable mysqlTable = (MysqlTable) claz.getAnnotation( MysqlTable.class );
            MysqlUtil mysqlUtil = new MysqlUtil();
            String createTable = mysqlUtil.createTableStr( claz, mysqlTable, tableName);
            jdbcTemplate.execute( createTable );
        }catch (Exception e){
            res = false;
        }
        return res;
    }

    @Override
    public boolean add(String tableName, NodeInfo nodeInfo) {
        return false;
    }

    @Override
    public boolean remove(String tableName, int id) {
        return false;
    }

    @Override
    public NodeInfo getNodeInfo(String tableName, int id) {
        return null;
    }

    @Override
    public NodeInfo getNodeInfoList(NodeInfoReqParam param) {
        return null;
    }


}
