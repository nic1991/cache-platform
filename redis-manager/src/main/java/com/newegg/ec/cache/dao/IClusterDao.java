package com.newegg.ec.cache.dao;

import com.newegg.ec.cache.model.Cluster;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Created by gl49 on 2018/4/20.
 */
@Repository
public interface IClusterDao {
    List<Cluster> getClusterList();
    Cluster getCluster(int id);
    void removeCluster(int id);
    void addCluster(Cluster cluster);
}
