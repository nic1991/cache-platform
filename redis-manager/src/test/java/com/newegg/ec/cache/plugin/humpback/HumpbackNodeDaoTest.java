package com.newegg.ec.cache.plugin.humpback;

import com.newegg.ec.cache.Application;
import com.newegg.ec.cache.app.dao.IClusterCheckRuleDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by gl49 on 2018/5/8.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class HumpbackNodeDaoTest {
    @Resource
    private IHumpbackNodeDao humpbackNodeDao;

    @Test
    public void testHumbackNodeList(){
        List list = humpbackNodeDao.getHumbackNodeList("admin");
        System.out.println( list );
    }

    @Test
    public void testAddHumbackNode(){
        HumpbackNode humpbackNode = new HumpbackNode();
        humpbackNode.setClusterName("fdas");
        humpbackNode.setContainerName("fdas");
        humpbackNode.setGroup("admin");
        humpbackNode.setImage("fffffffff");
        humpbackNode.setIp("127.0.0.1");
        humpbackNode.setPort(8081);
        humpbackNodeDao.addHumbackNode( humpbackNode );
    }

    @Test
    public void testRemoveHumpbackNode(){
        int id = 1;
        humpbackNodeDao.removeHumbackNode( id );
    }

    @Test
    public void testGetCluster(){
        HumpbackNode humpbackNode = humpbackNodeDao.getHumpbackNode( 2 );
        System.out.println( humpbackNode );
    }
}
