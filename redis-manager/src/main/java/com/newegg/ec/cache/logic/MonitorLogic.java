package com.newegg.ec.cache.logic;

import com.newegg.ec.cache.dao.INodeInfoDao;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
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
    private INodeInfoDao nodeInfoDao;

    public boolean addCluster(String tableName){
        return nodeInfoDao.createTable( tableName );
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
