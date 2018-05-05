package com.newegg.ec.cache.app.controller.websocket;

import com.newegg.ec.cache.app.model.Response;
import com.newegg.ec.cache.app.util.NetUtil;
import org.apache.commons.lang.StringUtils;

/**
 * Created by lzz on 2018/5/4.
 */
public class CheckLogLogic {
    private int userId;
    public CheckLogLogic(int userId){
        this.userId = userId;
    }
    private String checkLog(String msg){
        CreateClusterLogHandler.appendLog(String.valueOf(this.userId), msg);
        return msg + "<br>";
    }

    public  Response checkClusterName(String clusterName) {
        checkLog("check cluster is ok");
        return Response.Error("errror");
    }

    public  Response checkBatchHostNotPass(String iplist) {
        String errorMsg = "";
        String[] ipArr = iplist.split("\n");
        for(String line : ipArr) {
            try {
                checkLog("start check " + line );
                String[] tmpArr = line.split(":");
                if (tmpArr.length >= 2) {
                    String ip = tmpArr[0].trim();
                    if( !NetUtil.checkIp(ip, 5000) ){
                        errorMsg += checkLog( ip + " is not pass");
                        continue;
                    }
                    int port = Integer.parseInt(tmpArr[1].trim());
                    if( !NetUtil.checkIpAndPort(ip, port) ){
                        checkLog( line + " is ok");
                    }else{
                        errorMsg += checkLog( line + " the port is alreay use" );
                    }
                }else{
                    errorMsg += checkLog( line + " is format error" );
                }
            }catch (Exception e){
                checkLog( e.getMessage() );
            }
        }
        if( !StringUtils.isBlank( errorMsg) ){
            System.out.println( errorMsg );
            return Response.Error( errorMsg );
        }else{
            return Response.Success();
        }
    }
}
