package com.newegg.ec.cache.app.logic;

import com.newegg.ec.cache.app.component.RedisManager;
import com.newegg.ec.cache.app.dao.IClusterDao;
import com.newegg.ec.cache.app.dao.INodeInfoDao;
import com.newegg.ec.cache.app.model.RedisQueryParam;
import com.newegg.ec.cache.app.dao.impl.NodeInfoDao;
import com.newegg.ec.cache.app.model.Cluster;
import com.newegg.ec.cache.app.model.Common;
import com.newegg.ec.cache.app.model.Host;
import com.newegg.ec.cache.app.util.JedisUtil;
import com.newegg.ec.cache.app.util.NetUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gl49 on 2018/4/21.
 */
@Component
public class ClusterLogic {
    @Autowired
    private IClusterDao clusterDao;
    @Autowired
    private NodeInfoDao nodeInfoTable;
    @Resource
    private RedisManager redisManager;

    public Cluster getCluster(int id){
        return clusterDao.getCluster( id );
    }

    public Object query(RedisQueryParam redisQueryParam){
        return redisManager.query( redisQueryParam );
    }

    public List<Cluster> getClusterList(String group){
        return clusterDao.getClusterList( group );
    }

    public boolean removeCluster(int id){
        boolean res = false;
        try {
            clusterDao.removeCluster(id);
            String tableName = Common.NODE_INFO_TABLE_FORMAT + id;
            nodeInfoTable.dropTable( tableName );
            res = true;
        }catch (Exception e){

        }
        return res;
    }

    public Map<String, Integer> getClusterListInfo(String userGroup) {
        Map<String, Integer> clusterListInfo = new HashMap<>();
        int clusterOkNumber = 0;
        int clusterFailNumber = 0;
        if (StringUtils.isNotBlank(userGroup)){
            List<Cluster> clusterList = clusterDao.getClusterList(userGroup);
            if (clusterList != null && clusterList.size() > 0){
                clusterListInfo.put(Common.CLUSTER_NUMBER, clusterList.size());
                for (Cluster cluster : clusterList){
                    if (getClusterState(cluster.getId())) {
                        clusterOkNumber++;
                    } else {
                        clusterFailNumber++;
                    }
                }
                clusterListInfo.put(Common.CLUSTER_OK_NUMBER, clusterOkNumber);
                clusterListInfo.put(Common.CLUSTER_FAIL_NUMBER, clusterFailNumber);
            } else {
                clusterListInfo.put(Common.CLUSTER_NUMBER, 0);
                clusterListInfo.put(Common.CLUSTER_OK_NUMBER, 0);
                clusterListInfo.put(Common.CLUSTER_FAIL_NUMBER, 0);
            }
        }
        return null;
    }

    public boolean addCluster(Cluster cluster){
        boolean res = false;
        try {
            int row = clusterDao.addCluster(cluster);
            if (row > 0){
                cluster.setAddress(cluster.getAddress());
                cluster.setUserGroup(cluster.getUserGroup());
                cluster.setClusterName(cluster.getClusterName());
                nodeInfoTable.createTable(Common.NODE_INFO_TABLE_FORMAT + cluster.getId());
                res = true;
            } else {
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return res;
    }

    public Map<String, String> getClusterInfo(String ip, int port){
        return redisManager.getClusterInfo(ip, port);
    }

    public Map<String, String> getClusterInfo(String address){
        List<Host> host = NetUtil.getHostByAddress( address );
        return redisManager.getClusterInfo(host.get(0).getIp(), host.get(0).getPort());
    }

    public Map<String, String> getNodeInfo(String address){
        Host host = NetUtil.getHostPassAddress( address );
        return redisManager.getMapInfo(host.getIp(), host.getPort());
    }

    public Map<String, String> getRedisConfig(String address){
        Host host = NetUtil.getHostPassAddress( address );
        return redisManager.getRedisConfig(host.getIp(), host.getPort() );
    }

    public List<Map<String, String>> nodeList(String address){
        Host host = NetUtil.getHostPassAddress( address );
        List<Map<String, String>> list = JedisUtil.nodeList( host.getIp(), host.getPort() );
        return list;
    }

    public Host getClusterHost(int id) {
        Cluster cluster = getCluster(id);
        String addressStr = cluster.getAddress();
        Host host = NetUtil.getHostPassAddress( addressStr );
        return host;
    }

    public boolean getClusterState(int id){
        Host host = getClusterHost(id);
        final Map<String, String> clusterInfo = getClusterInfo(host.getIp(), host.getPort());
        System.out.println(clusterInfo);
        String state = clusterInfo.get(Common.CLUSTER_STATE);
        return  "ok".equals(state);
    }

    public Map<String,Map> detailNodeList(String address) {
        Host host = NetUtil.getHostPassAddress( address );
        Map<String, Map> result = JedisUtil.getClusterNodes( host.getIp(), host.getPort() );
        return result;
    }


}