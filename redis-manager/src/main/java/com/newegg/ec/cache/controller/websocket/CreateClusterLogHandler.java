package com.newegg.ec.cache.controller.websocket;

import net.sf.json.JSONObject;
import org.springframework.web.socket.*;
import java.util.ArrayList;

/**
 * Created by gl49 on 2018/4/22.
 */
public class CreateClusterLogHandler implements WebSocketHandler {
    private static final ArrayList<WebSocketSession> users = new ArrayList();

    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        webSocketSession.sendMessage(new TextMessage("success connction"));
    }

    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
        JSONObject reqObject = JSONObject.fromObject(webSocketMessage.getPayload().toString());
        for(int i = 0; i < 10;i++){
            webSocketSession.sendMessage(new TextMessage("aaa " + i));
            Thread.sleep(2000);
        }
    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
        if (webSocketSession.isOpen()) {
            webSocketSession.close();
        }
        users.remove(webSocketSession);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        System.out.println( "close" );
        users.remove(webSocketSession);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
