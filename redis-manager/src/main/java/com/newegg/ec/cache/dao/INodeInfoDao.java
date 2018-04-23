package com.newegg.ec.cache.dao;

import com.newegg.ec.cache.model.NodeInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by gl49 on 2018/4/21.
 */
@Repository
public interface INodeInfoDao {
    boolean add(@Param("tableName") String tableName, @Param("q") NodeInfo nodeInfo);
    NodeInfo getNodeInfo(@Param("tableName") String tableName, @Param("id") int id);
}
