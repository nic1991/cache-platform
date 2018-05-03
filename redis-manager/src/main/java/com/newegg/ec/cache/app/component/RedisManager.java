package com.newegg.ec.cache.app.component;

import com.newegg.ec.cache.app.util.JedisUtil;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by lzz on 2018/4/20.
 */
@Component
public class RedisManager {
    public Map<String, String> getClusterInfo(String ip, int port){
        Map<String, String> res = JedisUtil.getClusterInfo(ip, port);
        return res;
    }

    public Map<String, String> getMapInfo(String ip, int port){
        Map<String, String> res = JedisUtil.getMapInfo(ip, port);
        return res;
    }
}
