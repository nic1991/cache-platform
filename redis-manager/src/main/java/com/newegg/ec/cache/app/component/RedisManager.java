package com.newegg.ec.cache.app.component;

import com.newegg.ec.cache.app.component.redis.IRedis;
import com.newegg.ec.cache.app.component.redis.JedisClusterClient;
import com.newegg.ec.cache.app.component.redis.JedisMasterSlaveClient;
import com.newegg.ec.cache.app.component.redis.RedisClientBase;
import com.newegg.ec.cache.app.model.Host;
import com.newegg.ec.cache.app.model.RedisQueryParam;
import com.newegg.ec.cache.app.util.JedisUtil;
import com.newegg.ec.cache.app.util.NetUtil;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by lzz on 2018/4/20.
 */
@Component
public class RedisManager {
    public IRedis factory(String address){
        IRedis redis;
        Host host = NetUtil.getHostPassAddress( address );
        String ip  = host.getIp();
        int port = host.getPort();
        int version = JedisUtil.getRedisVersion( ip, port );
        if( version > 2 ){
            redis = new JedisClusterClient( ip, port );
        }else{
            redis = new JedisMasterSlaveClient( ip, port );
        }
        return redis;
    }

    public Object query(RedisQueryParam redisQueryParam) {
        Object res = null;
        IRedis redis = factory( redisQueryParam.getAddress() );
        if( !redisQueryParam.getKey().equals("*") ){
            res = redis.getRedisValue( redisQueryParam.getDb(), redisQueryParam.getKey());
        }
        if( null == res ){
            res = redis.scanRedis( redisQueryParam.getDb(), redisQueryParam.getKey() );
        }
        redis.close();
        return res;
    }

    public Map<String, String> getClusterInfo(String ip, int port){
        Map<String, String> res = JedisUtil.getClusterInfo(ip, port);
        return res;
    }

    public Map<String, String> getMapInfo(String ip, int port){
        Map<String, String> res = JedisUtil.getMapInfo(ip, port);
        return res;
    }

    public Map<String, String> getRedisConfig(String ip, int port){
        Map<String, String> res = JedisUtil.getRedisConfig(ip, port);
        return res;
    }


}
