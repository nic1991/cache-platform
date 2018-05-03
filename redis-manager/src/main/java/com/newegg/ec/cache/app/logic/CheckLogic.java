package com.newegg.ec.cache.app.logic;

import com.newegg.ec.cache.app.controller.websocket.CreateClusterLogHandler;
import com.newegg.ec.cache.app.model.Response;
import com.newegg.ec.cache.core.logger.CommonLogger;
import com.newegg.ec.cache.app.util.NetUtil;
import com.newegg.ec.cache.app.util.RemoteShellUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by gl49 on 2018/4/22.
 */
public class CheckLogic {
    public static CommonLogger logger = new CommonLogger(CheckLogic.class);
    private static void checkLog(String clusterId, String msg){
        CreateClusterLogHandler.appendLog(clusterId, msg);
    }

    public static Response checkPortPass(String ip, int port, boolean isPass){
        boolean res =NetUtil.checkIpAndPort(ip, port);
        if( isPass ){
            return res ? Response.Success() : Response.Error("port is not pass");
        }else{
            return !res ? Response.Success() : Response.Error("port is already use");
        }
    }

    public static Response checkIp(String ip){
        boolean res = NetUtil.checkIp(ip, 5000);
        return res ? Response.Success() : Response.Error("ip is not access");
    }

    public static Response checkClusterName(String clusterName, String user) {
        checkLog(clusterName, "check cluster is ok");
        return Response.Success();
    }
}
