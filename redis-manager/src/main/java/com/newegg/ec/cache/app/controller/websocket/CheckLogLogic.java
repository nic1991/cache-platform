package com.newegg.ec.cache.app.controller.websocket;

import com.newegg.ec.cache.app.model.Response;

/**
 * Created by lzz on 2018/5/4.
 */
public class CheckLogLogic {
    private static void checkLog(int id, String msg){
        CreateClusterLogHandler.appendLog(String.valueOf(id), msg);
    }

    public static Response checkClusterName(String clusterName, int userId) {
        checkLog(userId, "check cluster is ok");
        return Response.Success();
    }
}
