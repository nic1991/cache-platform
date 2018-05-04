package com.newegg.ec.cache.app.controller.check;

import com.newegg.ec.cache.app.model.Response;
import com.newegg.ec.cache.app.util.NetUtil;
import com.newegg.ec.cache.core.logger.CommonLogger;

/**
 * Created by gl49 on 2018/4/22.
 */
public class CheckLogic {
    public static CommonLogger logger = new CommonLogger(CheckLogic.class);

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
}
