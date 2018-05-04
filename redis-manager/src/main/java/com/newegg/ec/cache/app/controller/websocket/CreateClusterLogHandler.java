package com.newegg.ec.cache.app.controller.websocket;

import net.sf.json.JSONObject;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.*;

import java.util.Map;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by gl49 on 2018/4/22.
 */
public class CreateClusterLogHandler implements WebSocketHandler {
    private static final Map<WebSocketSession, BlockingDeque<String>> logMap = new ConcurrentHashMap<>();
    private static final Map<String, WebSocketSession> webSocketAndClustertable = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        webSocketSession.getAttributes();
        createLogQueue(webSocketSession);
        appendLog(webSocketSession, "success connection");
    }

    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
        JSONObject reqObject = JSONObject.fromObject(webSocketMessage.getPayload().toString());
        webSocketAndClustertable.put( reqObject.getString("id"), webSocketSession);
        while (true){
            BlockingDeque<String> logQueue = getLogQueue( webSocketSession );
            String message = logQueue.poll();
            System.out.println( logQueue.size() + "-------" + message);
            if( !StringUtils.isEmpty( message )){
                webSocketSession.sendMessage(new TextMessage("aaa " + message));
            }else{
                Thread.sleep(1000);
            }
        }
    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
        if (webSocketSession.isOpen()) {
            webSocketSession.close();
        }
        removeLogQueue( webSocketSession );
    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        System.out.println( "close" );
        removeLogQueue( webSocketSession );
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }


    private static void createLogQueue(WebSocketSession webSocketSession){
        logMap.put( webSocketSession, new LinkedBlockingDeque<>());
    }

    private static BlockingDeque<String> getLogQueue(WebSocketSession webSocketSession){
        BlockingDeque<String> blockingDeque = logMap.get( webSocketSession );
        return blockingDeque;
    }

    public static synchronized void appendLog(String clusterid, String msg){
        WebSocketSession webSocketSession = webSocketAndClustertable.get( clusterid );
        if( null != webSocketSession ){
            appendLog( webSocketSession, msg );
        }
    }


    private static void appendLog(WebSocketSession webSocketSession, String msg){
        BlockingDeque<String> blockingDeque = getLogQueue( webSocketSession );
        blockingDeque.add( msg );
    }

    private  static synchronized void removeLogQueue(WebSocketSession webSocketSession){
        BlockingDeque<String> blockingDeque = getLogQueue( webSocketSession );
        blockingDeque = null;
        logMap.remove( webSocketSession );
        for(Map.Entry<String, WebSocketSession> entry : webSocketAndClustertable.entrySet()){
            WebSocketSession socketSession = entry.getValue();
            if( webSocketSession == socketSession ){
                webSocketAndClustertable.remove( entry.getKey() );
                break;
            }
        }
    }
}
