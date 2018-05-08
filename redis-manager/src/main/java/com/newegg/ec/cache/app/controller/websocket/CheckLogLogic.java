package com.newegg.ec.cache.app.controller.websocket;

import com.newegg.ec.cache.app.dao.IClusterCheckLogDao;
import com.newegg.ec.cache.app.dao.IClusterDao;
import com.newegg.ec.cache.app.dao.IUserDao;
import com.newegg.ec.cache.app.model.Cluster;
import com.newegg.ec.cache.app.model.Response;
import com.newegg.ec.cache.app.model.User;
import com.newegg.ec.cache.app.util.NetUtil;
import com.newegg.ec.cache.app.util.RequestUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lzz on 2018/5/4.
 */
@Component
public class CheckLogLogic {
    @Resource
    private IClusterDao clusterDao;

    private String checkLog(String msg){
        CreateClusterLogHandler.appendLog(String.valueOf( RequestUtil.getUser().getId() ), msg);
        return msg + "<br>";
    }

    public  Response checkClusterName(String clusterName) {
        User user  = RequestUtil.getUser();
        List<Cluster> resList = clusterDao.getClusterList( user.getUserGroup() );
        if( resList.size() > 0 ){
            return Response.Success();
        }else{
            return Response.Error("errror");
        }
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
