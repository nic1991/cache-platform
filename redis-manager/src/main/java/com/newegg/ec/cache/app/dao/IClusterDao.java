package com.newegg.ec.cache.app.dao;

import com.newegg.ec.cache.app.model.Cluster;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Created by gl49 on 2018/4/20.
 */
@Repository
public interface IClusterDao {
    List<Cluster> getClusterList(String userGroup);

    Cluster getCluster(int id);

    void removeCluster(int id, String clusterName);

    void addCluster(Cluster cluster);
}
