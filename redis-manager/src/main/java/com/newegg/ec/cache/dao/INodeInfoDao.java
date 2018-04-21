package com.newegg.ec.cache.dao;

import com.newegg.ec.cache.model.NodeInfo;
import com.newegg.ec.cache.model.NodeInfoReqParam;

/**
 * Created by gl49 on 2018/4/21.
 */
public interface INodeInfoDao {
    boolean createTable(String tableName);
    boolean add(String tableName, NodeInfo nodeInfo);
    boolean remove(String tableName, int id);
    NodeInfo getNodeInfo(String tableName, int id);
    NodeInfo getNodeInfoList(NodeInfoReqParam param);
}
