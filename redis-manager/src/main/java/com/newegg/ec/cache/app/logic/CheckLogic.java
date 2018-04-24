package com.newegg.ec.cache.app.logic;

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

    public static JSONObject checkResult(int errorCode, String msg){
        JSONObject result = new JSONObject();
        result.put("sparrow_error_code", errorCode);
        result.put("error_msg", msg);
        return result;
    }
    public static JSONObject checkResult(int errorCode, String msg, String extend){
        JSONObject result = new JSONObject();
        result.put("sparrow_error_code", errorCode);
        result.put("error_msg", msg);
        result.put("extend_msg", extend);
        return result;
    }

    public static JSONObject checkClusterName(String clusterName) {

        return checkResult( 0, "the cluster name is exist");
    }

    public static JSONObject checkUserGroup(String group) {
        int errorCode = 0;
        return null;
    }

    public static JSONObject checkUserName(String name) {
        int errorCode = 0;
        return null;
    }

    public static JSONObject checkPort(String ip, int port){
        int errorCode = 0;
        boolean res = NetUtil.checkIpAndPort(ip, port);
        if( res ){
            errorCode = 1;
        }
        return checkResult( errorCode, "the port is exist");
    }

    public static JSONObject checkPortPass(String ip, int port){
        int errorCode = 0;
        boolean res =NetUtil.checkIpAndPort(ip, port);
        if( !res ){
            errorCode = 1;
        }
        return checkResult( errorCode, "the port is no pass");
    }

    public static JSONObject checkIp1(String ip){
        int errorCode = 0;
        boolean res = NetUtil.checkIp(ip, 4000);
        if( !res ){
            errorCode = 1;
        }
        return checkResult( errorCode, "the ip ping timeout");
    }

    public static JSONObject checkBatchResult(String iplist, String clusterid, String team){
        boolean addCluster = false;
        int errocode = 0;
        String errMsg = "";
        String extend = "";
        String[] ipArr = iplist.split("\n");
        int failCount = 0;
        for(String line : ipArr) {
            try {
                String[] tmpArr = line.split("\\s+");
                if (tmpArr.length >= 2) {
                    boolean res = NetUtil.checkIpAndPort( tmpArr[0], Integer.parseInt(tmpArr[1]));
                    if( !res ){
                        failCount ++;
                        errocode = 1;
                        String failMsg = line + " is install fail";
                        errMsg = failMsg + " <br>";
                        System.out.println( errMsg );
                    }else{
                        if( !StringUtils.isBlank( clusterid ) && !addCluster ){
                            JSONObject clusterInfo = new JSONObject();
                            clusterInfo.put("cluster_name", clusterid);
                            clusterInfo.put("ip", tmpArr[0]);
                            clusterInfo.put("port", tmpArr[1]);
                            clusterInfo.put("group", team);
                            /*
                            ConfigUtil config = new ConfigUtil();
                            String setres = config.get( clusterid );
                            if( StringUtils.isBlank( setres ) ){
                                config.set( clusterid, clusterInfo.toString() );
                                // 如果配置文件里面没有该字段就删除好了
                                MysqlUtil.createNodeInfoTable( MysqlUtil.getNodeInfoTableName(clusterid) );
                            }
                            addCluster = true;
                            */
                        }
                        //formLogger(clusterid, "success install " + line);
                        extend += tmpArr[0] + ":" + tmpArr[1] + "\n";
                    }

                }else{
                    failCount ++;
                    errocode = 1;
                    errMsg =  line + " is error format <br>";
                }
            }catch (Exception e ){
                failCount ++;
                //logger.error("", e );
                //formLogger(clusterid, e.getMessage());
            }
        }

        if( 0 == errocode ){
            errMsg = "all node is success";
        }else if( failCount == ipArr.length ){
            errMsg = "all node is fail";
            errocode = 2;
        }else{
            errMsg = "fail " + failCount + " node";
        }
        //formLogger(clusterid, "finish " + errMsg);
        return checkResult( errocode, errMsg, extend );
    }


    public static JSONObject checkBatchDockerNode(String iplist, String clusterid){
        int errocode = 0;
        String errMsg = "";
        String[] ipArr = iplist.split("\n");
        for(String line : ipArr) {
            try {
                //formLogger(clusterid, "start check " + line );
                String[] tmpArr = line.split("\\s+");
                if (tmpArr.length >= 2) {
                    String ip = tmpArr[0].trim();
                    int port = Integer.parseInt(tmpArr[1].trim());
                    if( NetUtil.checkIpAndPort( ip, port ) ){
                        errocode = 1;
                        errMsg = line + " port is already use";
                        //formLogger( clusterid, errMsg );
                        errMsg += " <br>";
                    }

                    if( StringUtils.isBlank( checkDockerDeamon() ) ){
                        errocode = 1;
                        errMsg = ip + " the host is without docker deamon";
                        //formLogger(clusterid, errMsg);
                        errMsg += " <br>";
                    }

                }else{
                    errocode = 1;
                    errMsg =  line + " is error format <br>";
                }
                //formLogger(clusterid, line + " check finish" );
            }catch (Exception e ){
                logger.error("", e );
                //formLogger( clusterid, e.getMessage() );
            }
        }
        return checkResult( errocode, errMsg );
    }


    public static JSONObject checkBatchMachineNode(String clusterid,String iplist, String username, String password, String install_path) {
        // 用户名密码只检查第一个， 端口每个都要检查
        String[] ipArr = iplist.split("\n");
        boolean logincheck = false;
        int errocode = 0;
        String errMsg = "";
        for(String line : ipArr){
            try {
                String[] tmpArr = line.split("\\s+");
                if( tmpArr.length >= 2 ){
                    //formLogger(clusterid, line + " start check.....");
                    String ip = tmpArr[0].trim();
                    String port = tmpArr[1].trim();

                    if( NetUtil.checkIpAndPort( ip, Integer.parseInt(port)) ){
                        errocode = 1;
                        errMsg += "the ip:" + ip + " port:" + port + " is already use";
                        //formLogger(clusterid, errMsg );
                        errMsg += " <br>";
                    }
                    if( logincheck != true ){
                        boolean res = NetUtil.checkIpAnduserAccess(ip, username, password);
                        if( !res ){
                            errocode = 1;
                            return checkResult(errocode, "ssh username:" + username + " password:" + password + "  login host: " + ip + " is fail<br>" );
                        }
                        logincheck = true;
                    }
                    //formLogger(clusterid, line + " is finish check!");
                }else {
                    errocode = 1;
                    errMsg = line + " is error format (ip port)";
                    //formLogger( clusterid, errMsg );
                    errMsg += " <br>";
                }
            }catch (Exception e){
                errocode = 1;
                errMsg = e.getMessage();
                //formLogger( clusterid, errMsg );
                logger.error("", e );
            }

        }
        return checkResult( errocode, errMsg );
    }

    public static JSONObject checkUsernameAndPassword(String ip, String username, String password){
        int errorCode = 0;
        boolean res = NetUtil.checkIpAnduserAccess(ip, username, password);
        if( !res ){
            errorCode = 1;
        }
        return checkResult( errorCode, "the password or username is error");
    }

    public static JSONObject checkDir(String ip,String username, String password, String installPath) {
        int errocode = 0;
        String errMsg = "";

        String[] cmds = new String[2];
        cmds[0] = getCheckDirExist( installPath );
        cmds[1] = getCheckDirPermission( installPath );
        String cmd = StringUtils.join( cmds, ";");
        RemoteShellUtil remoteShellUtil = new RemoteShellUtil(ip, username, password);
        String checkDirRes = remoteShellUtil.exec(cmd);
        if( !StringUtils.isBlank( checkDirRes )) {
            errocode = 1;
            errMsg += "IP: " + ip + " Username:" + username + " Password:" + password + "<br>" + checkDirRes;
        }
        return  checkResult( errocode, errMsg );
    }

    public static JSONObject checkBatchDirAndWget(String clusterid, String iplist, String username, String password, String installPath) {
        Set set = getIpSet( iplist );
        int errocode = 0;
        String errMsg = "";
        Iterator<String> it = set.iterator();
        while ( it.hasNext() ){
            String ip = it.next();
            //formLogger(clusterid, ip + " start mkdir " + installPath + ".....");
            String checkRes = checkDirAndWget(ip, username, password, installPath);
            if( !StringUtils.isBlank( checkRes )) {
                errocode = 1;
                errMsg += "the host " + ip + " : " + checkRes;
                //formLogger(clusterid, errMsg );
                errMsg += " <br>";
            }
        }
        return checkResult(errocode, errMsg);
    }

    private static Set getIpSet(String iplist) {
        Set res = new HashSet<>();
        String[] ipArr = iplist.split("\n");
        for(String line : ipArr){
            try {
                String[] tmpArr = line.split("\\s+");
                if( tmpArr.length == 2 ){
                    res.add( tmpArr[0] );
                }
            }catch (Exception e){
                logger.error("", e );
            }
        }
        return res;
    }

    public static String checkDirAndWget(String ip, String username, String password, String installPath) {
        String errorMsg = "";
        if(StringUtils.isBlank( installPath ) ){
            errorMsg = "install path is require";
            return errorMsg;
        }
        String installTag ="tag";
        String[] cmds = new String[2];
        cmds[0] = getCheckCommand( "wget" );
        cmds[1] = "mkdir -p " + installPath + "/" + installTag;
        String cmd = StringUtils.join( cmds, ";");
        System.out.println( cmd );
        RemoteShellUtil remoteShellUtil = new RemoteShellUtil(ip, username, password);
        errorMsg = remoteShellUtil.exec(cmd);
        return  errorMsg;
    }

    public static String getCheckDirPermission(String installPath){
        return "if [ ! -w '" + installPath + "' ];then echo '"+ installPath +" without permision <br>'; fi";
    }
    public static String getCheckDirExist(String installPath){
        return  "if [ ! -d '" + installPath + "' ]; then echo 'without the " + installPath + " dir <br>'; fi";
    }

    public static String getCheckCommand(String command){
        return "if [ ! `command -v " + command +"` ]; then echo 'no exists " + command + " <br>'; fi";
    }

    private static String checkDockerDeamon() {
        return "if [ 0 == `ps -ef |grep 'docker' |grep -v 'grep' |wc -l` ]; then echo 'without docker deamon <br>'; fi";
    }
}
