package com.newegg.ec.cache.logic;

import com.newegg.ec.cache.dao.IClusterDao;
import com.newegg.ec.cache.dao.IUserDao;
import com.newegg.ec.cache.model.Cluster;
import com.newegg.ec.cache.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by gl49 on 2018/4/21.
 */
@Component
public class ClusterLogic {
    @Autowired
    private IClusterDao clusterDao;

    public Cluster getCluster(int id){
        return clusterDao.getCluster( id );
    }

    public List<Cluster> getClusterList(){
        return clusterDao.getClusterList();
    }

    public boolean removeCluster(int id){
        boolean res = false;
        try {
            clusterDao.removeCluster( id );
            res = true;
        }catch (Exception e){

        }
        return res;
    }

    public boolean addCluster(Cluster cluster){
        boolean res = false;
        try {
            clusterDao.addCluster( cluster );
            res = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return res;
    }
}