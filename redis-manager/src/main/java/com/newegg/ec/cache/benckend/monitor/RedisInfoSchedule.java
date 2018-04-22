package com.newegg.ec.cache.benckend.monitor;

import com.newegg.ec.cache.core.logger.CommonLogger;
import com.newegg.ec.cache.dao.IClusterDao;
import com.newegg.ec.cache.dao.INodeInfoDao;
import com.newegg.ec.cache.dao.impl.NodeInfoDao;
import com.newegg.ec.cache.model.NodeInfo;
import com.newegg.ec.cache.util.DateUtil;
import com.newegg.ec.cache.util.JedisUtil;
import com.newegg.ec.cache.util.NetUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

    public static CommonLogger logger = new CommonLogger( RedisInfoSchedule.class );
    private static final int JEDIS_TIMEOUT = 1000;
    private static ExecutorService threadPool = Executors.newFixedThreadPool(200);


    public static class DeleteMonitor extends TimerTask {
        public DeleteMonitor(){}
        @Override
        public void run() {
            try {
                startDeleteData();
            }catch (Exception e ){

            }
        }
    }


    private static void startDeleteData(){
        List<String> tableList = getMonitorTables();
        int sevcenTime = DateUtil.getTime() - 7*24*60*60;
        for(String tableName : tableList){
            try {
                String sql = "delete from " + tableName + " where add_time < " + sevcenTime;
                //MysqlUtil.delete( sql );
            }catch (Exception e){
                //logger.error( e );
            }
        }
    }

    /**
     * 获取监控表
     * @return
     */
    private static  List<String>  getMonitorTables(){
        /*
        List<String> tables = new ArrayList<>();
        try{
            List<Map> list = MysqlUtil.getTables();
            for(Map table : list){
                String tableName = (String) table.get("Tables_in_redis_monitor");
                if( tableName.contains("node_info") ){
                    tables.add( tableName );
                }
            }
        }catch (Exception e){
            logger.error( e );
        }
        return tables;
        */
        return null;
    }

    public static NodeInfo getNodeMonitorInfo(String strInfo) {
        NodeInfo o = null;
        try {
            o = new NodeInfo();
            Field[] fields=o.getClass().getDeclaredFields();
            BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(strInfo.getBytes(Charset.forName("utf8"))), Charset.forName("utf8")));
            String line;
            while ( (line = br.readLine()) != null ) {
                String[] arr = line.split(":");
                if( arr.length == 2 ){
                    if( arr[0].contains("db") ){
                        continue;
                    }
                    for(Field f : fields){
                        f.setAccessible(true);
                        if( arr[0].equals( f.getName() ) ){
                            String type = f.getGenericType().toString();
                            if( type.equals("class java.lang.String") ){
                                f.set( o, String.valueOf(arr[1]) );
                            }else if( "int".equals(type)){
                                f.set( o, Integer.valueOf(arr[1]) );
                            }else if( "long".equals(type)){
                                f.set( o, Long.valueOf(arr[1]) );
                            }else if( "float".equals( type ) ){
                                f.set( o, Float.valueOf(arr[1]) );
                            }
                        }
                    }
                }
            }
            processDb(strInfo, o);
        }catch (Exception e){

        }
        return o;
    }

    private static void processDb(String strInfo, NodeInfo o) {
        List<Map<String, String>> resDb = JedisUtil.dbInfo( strInfo );
        long keys = 0;
        long avg_ttl = 0;
        long expires = 0;
        int size = resDb.size();
        for(int i = 0; i < size; i++){
            Map<String , String> item = resDb.get( i );
            long tempKeys = Long.parseLong(item.get("keys"));
            long tempAvgTTl = Long.parseLong(item.get("avg_ttl"));
            long tempExpires = Long.parseLong(item.get("expires"));
            keys += tempKeys;
            avg_ttl += tempAvgTTl;
            expires += tempExpires;
        }
        if( size > 0 ){
            avg_ttl = avg_ttl / size;
            expires = expires / size;
        }
        o.setTotalKeys( keys );
        o.setExpires( expires );
        o.setAvgTtl( avg_ttl );
    }

    public static NodeInfo changeNodeInfoTime(NodeInfo info) {
        info.setAddTime(DateUtil.getTime() );
        info.setDay( DateUtil.getDay() );
        info.setHour( DateUtil.getHour() );
        info.setMinute( DateUtil.getMinute());
        return info;
    }

    private static class InfoProducer implements Runnable {
        private String clusterid;
        private  List<Map<String, String>> nodeList;
        private CountDownLatch countDownLatch;
        public InfoProducer (String clusterid, List<Map<String, String>> nodeList, CountDownLatch countDownLatch) {
            this.clusterid  = clusterid;
            this.nodeList = nodeList;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            try {
                String ip= this.nodeList.get(0).get("ip");
                long pingTime = NetUtil.pingTime( ip.trim() );
                for(Map<String, String> node : this.nodeList){
                    Jedis jedis = null;
                    try {
                        String nodeIp = node.get("ip");
                        int nodePort = Integer.parseInt(node.get("port"));
                        StopWatch stopWatch = new StopWatch();
                        stopWatch.start();
                        jedis = new Jedis(nodeIp, nodePort, JEDIS_TIMEOUT);
                        String strInfo = jedis.info();
                        stopWatch.stop();
                        long responTime = stopWatch.getTotalTimeMillis();
                        NodeInfo info = getNodeMonitorInfo(strInfo);
                        info.setResponseTime( responTimeProcess(pingTime, responTime) );
                        NodeInfo resInfo = changeNodeInfoTime( info );
                        resInfo.setClusterid( this.clusterid );
                        resInfo.setHost( nodeIp +":" + nodePort);
                        resInfo.setIp( nodeIp );
                        resInfo.setPort( nodePort );
                        // 添加到数据库中
                        NodeInfoDao dao = new NodeInfoDao();
                        dao.add( this.clusterid, resInfo );
                    }catch ( Exception e ){

                    }finally {
                        if( null != jedis ){
                            jedis.close();
                        }
                    }
                }
            }catch ( Exception e ){

            }finally {
                countDownLatch.countDown();
            }
        }

        private long responTimeProcess(long pingTime, long responTime) {
            pingTime = 2 * pingTime;
            long redisTime = 0;
            if( responTime > pingTime ){
                redisTime = responTime - pingTime;
            }else{
                redisTime = 1;
            }
            // redis 调用往返
            redisTime = redisTime/2;
            if( redisTime == 0 ){
                redisTime = 1;
            }
            return redisTime;
        }
    }
}
