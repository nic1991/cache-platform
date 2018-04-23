package com.newegg.ec.cache.dao;

import com.newegg.ec.cache.Application;
import com.newegg.ec.cache.model.NodeInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by lzz on 2018/4/23.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class NodeInfoDaoTest {
    @Autowired
    private INodeInfoDao nodeInfoDao;

    @Test
    public void testAdd(){
        NodeInfo nodeInfo = new NodeInfo();
        nodeInfo.setConnectedClients(1000);
        nodeInfo.setBlockedClients(10000);
        nodeInfoDao.add("hello", nodeInfo);
        NodeInfo nodeInfo1 = nodeInfoDao.getNodeInfo( "hello", 1 );
        System.out.println( nodeInfo1 );
    }
}
