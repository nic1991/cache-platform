package com.newegg.ec.cache.app.dao;

import com.newegg.ec.cache.Application;
import com.newegg.ec.cache.app.dao.impl.NodeInfoDao;
import com.newegg.ec.cache.app.model.Cluster;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by gl49 on 2018/4/24.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class ClusterDaoTest {
    @Autowired
    private IClusterDao clusterDao;
    @Resource
    private NodeInfoDao nodeInfoTable;

    @Test
    public void addTest(){
        Cluster cluster = new Cluster();
        cluster.setAddress("10.16.46.192:8008");
        cluster.setUserGroup("admin");
        cluster.setClusterName("ssspark");
        clusterDao.addCluster(cluster);
        nodeInfoTable.createTable("node_info_" + cluster.getId());
    }

    @Test
    public void getClusterTest(){
        Cluster cluster = clusterDao.getCluster(1);
        System.out.println( cluster );
    }

    @Test
    public void getClusterListTest(){
        List<Cluster> clusterList = clusterDao.getClusterList("admin2");
        System.out.println( clusterList );
    }

    @Test
    public void removeClusterTest(){
        clusterDao.removeCluster(1, "");
    }
}
