package com.newegg.ec.cache.logic;

import com.newegg.ec.cache.dao.INodeInfoDao;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;

/**
 * Created by gl49 on 2018/4/21.
 */
@Component
public class MonitorLogic {
    @Resource
    private INodeInfoDao nodeInfoDao;

    public boolean addCluster(String tableName){
        return nodeInfoDao.createTable( tableName );
    }
}
