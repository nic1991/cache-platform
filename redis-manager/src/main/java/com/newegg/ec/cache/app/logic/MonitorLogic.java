package com.newegg.ec.cache.app.logic;

import com.newegg.ec.cache.app.dao.INodeInfoDao;
import com.newegg.ec.cache.app.dao.impl.NodeInfoDao;
import com.newegg.ec.cache.app.model.NodeInfo;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.util.Slowlog;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by gl49 on 2018/4/21.
 */
@Component
public class MonitorLogic {
    @Resource
    private INodeInfoDao nodeDao;

    public List<NodeInfo> getGroupNodeInfo(String tableName,int startTime,int endTime, String host, String type, String date){
        return nodeDao.getGroupNodeInfo(tableName, startTime, endTime, host, type, date);
    }

    public List<Map> getMaxField(String tableName, int startTime, int endTime, String key, int limit){
        return nodeDao.getMaxField(tableName, startTime, endTime, key, limit);
    }

    public List<Map> getMinField(String tableName, int startTime, int endTime, String key, int limit){
        return nodeDao.getMinField(tableName, startTime, endTime, key, limit);
    }

    public String getAvgField(String tableName, int startTime, int endTime, String host, String key){
        return nodeDao.getAvgField(tableName, startTime, endTime, host, key);
    }

    public String getAllField(String tableName, int startTime, int endTime, String key){
        return nodeDao.getAllField(tableName, startTime, endTime, key);
    }

    public NodeInfo getLastNodeInfo(String tableName, int startTime, int endTime, String host){
        return nodeDao.getLastNodeInfo(tableName, startTime, endTime, host);
    }

    public JSONObject getSlowLogs(JSONObject reqObject) {
        String ip = reqObject.getString("ip");
        int port = reqObject.getInt("port");
        List<Map> cmdList = new ArrayList<>();
        Jedis jedis = null;
        try {
            jedis = new Jedis(ip, port);
            List<Slowlog> slowList;
            if( reqObject.containsKey("onlyone") ){
                slowList = jedis.slowlogGet(150);
            }else{
                slowList = jedis.slowlogGet(5);
            }
            for( Slowlog log : slowList){
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm");
                Date date = new Date( log.getTimeStamp() * 1000 );
                String slowDate = simpleDateFormat.format(date);
                long runTime = log.getExecutionTime() / 1000;
                List<String> args = log.getArgs();
                Map item = new HashMap<>();
                String type = args.get(0);
                List<String> commands = args.subList(1, args.size());
                String command = StringUtils.join(commands, " ");
                item.put("type", type);
                item.put("command", command);
                item.put("runTime", runTime);
                item.put("slowDate", slowDate);
                cmdList.add(item);
            }
        }catch (Exception e){

        }finally {
            if( jedis != null ){
                jedis.close();
            }
        }
        JSONObject result = new JSONObject();
        result.put("result", cmdList);
        return result;
    }
}
