package com.newegg.ec.cache.app.util;

import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Map;

/**
 * Created by gl49 on 2018/4/21.
 */
public class JedisUtilTest {
    @Test
    public void test(){
        Map<String, Map> res = JedisUtil.getClusterNodes("172.16.35.75", 8028);
        List<Map<String, String>> ress = JedisUtil.dbInfo("172.16.35.75", 8028);
        System.out.println( ress );
    }

    @Test
    public void test454(){
        int size = JedisUtil.dbSize("10.16.46.196", 8700);
        System.out.println( size );
    }

    @Test
    public void testConfig(){
        Map map = JedisUtil.getRedisConfig("10.16.46.192", 8008);
        System.out.println( map );
    }

    @Test
    public void testDivice(){
        int logSize = 10000;
        int size = logSize/3;
        System.out.println( size );
    }

}
