package com.newegg.ec.cache.benckend.monitor;

import com.newegg.ec.cache.dao.IClusterDao;
import com.newegg.ec.cache.dao.INodeInfoDao;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by lzz on 2018/4/20.
 */
@Component
public class RedisInfoSchedule {
    @Resource
    private IClusterDao clusterDao;
    @Resource
    private INodeInfoDao nodeInfoDao;

    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
        System.out.println( "The time is now aaa");

    }
}
