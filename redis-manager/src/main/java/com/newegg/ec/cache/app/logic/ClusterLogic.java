package com.newegg.ec.cache.app.logic;

import com.newegg.ec.cache.app.component.RedisManager;
import com.newegg.ec.cache.app.dao.IClusterDao;
import com.newegg.ec.cache.app.model.Cluster;
import com.newegg.ec.cache.app.model.Host;
import com.newegg.ec.cache.app.util.NetUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by gl49 on 2018/4/21.
 */
@Component
public class ClusterLogic {
    @Autowired
    private IClusterDao clusterDao;
    @Resource
    private RedisManager redisManager;

    public Cluster getCluster(int id){
        return clusterDao.getCluster( id );
    }

    public List<Cluster> getClusterList(String group){
        return clusterDao.getClusterList( group );
    }

    public boolean removeCluster(int id, String clusterName){
        boolean res = false;
        try {
            clusterDao.removeCluster(id);
            res = true;
        }catch (Exception e){

        }
        return res;
    }

    public boolean addCluster(Cluster cluster){
        boolean res = false;
        try {
            int row = clusterDao.addCluster(cluster);
            if (row > 0){
                cluster.setAddress(cluster.getAddress());
                cluster.setUserGroup(cluster.getUserGroup());
                cluster.setClusterName(cluster.getClusterName());
                clusterDao.addCluster(cluster);
                /*nodeInfoTable.createTable("node_info_" + cluster.getClusterName());*/
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
}