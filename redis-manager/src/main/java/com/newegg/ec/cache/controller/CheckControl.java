package com.newegg.ec.cache.controller;

import com.newegg.ec.cache.logic.CheckLogic;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by gl49 on 2018/4/22.
 */
@Controller
@RequestMapping("/check")
public class CheckControl {
    @RequestMapping("/check_port")
    @ResponseBody
    public JSONObject checkPort(@RequestParam(value="pass", defaultValue="false") String pass, @RequestBody String jsonBody){
        JSONObject reqObject = JSONObject.fromObject(jsonBody);
        String ip = reqObject.getString("ip");
        int port = 0;
        if( reqObject.containsKey("port") ){
            port = reqObject.getInt("port");
        }else{
            port = reqObject.getInt("macheine_port");
        }
        if( "true".equals(pass) ){
            return CheckLogic.checkPortPass(ip, port);
        }
        return CheckLogic.checkPort(ip, port);
    }


    @RequestMapping("/check_ip")
    @ResponseBody
    public JSONObject checkHost(@RequestBody String jsonBody){
        JSONObject reqObject = JSONObject.fromObject(jsonBody);
        String ip = reqObject.getString("ip");
        return CheckLogic.checkIp1(ip);
    }


    @RequestMapping("/check_dir")
    @ResponseBody
    public JSONObject checkDir(@RequestBody String jsonBody){
        JSONObject reqObject = JSONObject.fromObject(jsonBody);
        String ip = reqObject.getString("ip");
        String username = reqObject.getString("username");
        String password = reqObject.getString("password");
        String installPath = reqObject.getString("install_path");
        return CheckLogic.checkDir(ip, username, password, installPath);
    }

    @RequestMapping("/check_batch_dir_and_wget")
    @ResponseBody
    public JSONObject checkBatchDirAndWget(@RequestBody String jsonBody){
        JSONObject reqObject = JSONObject.fromObject(jsonBody);
        String iplist = reqObject.getString("iplist");
        String username = reqObject.getString("username");
        String password = reqObject.getString("password");
        String installPath = reqObject.getString("install_path");
        String clusterid = reqObject.getString("cluster_name");
        return CheckLogic.checkBatchDirAndWget(clusterid, iplist, username, password, installPath);
    }

    @RequestMapping("/check_batch_docker_node")
    @ResponseBody
    public JSONObject checkBatchDockerNode(@RequestBody String jsonBody){
        JSONObject reqObject = JSONObject.fromObject(jsonBody);
        String iplist = reqObject.getString("docker_iplist");
        String clusterid = reqObject.getString("cluster_name");
        return CheckLogic.checkBatchDockerNode(iplist, clusterid);
    }

    @RequestMapping("/check_batch_machine_node")
    @ResponseBody
    public JSONObject checkBatchMachineNode(@RequestBody String jsonBody){
        JSONObject reqObject = JSONObject.fromObject(jsonBody);
        String iplist = reqObject.getString("iplist");
        String username = reqObject.getString("username");
        String password = reqObject.getString("password");
        String installPath = reqObject.getString("install_path");
        String clusterid = reqObject.getString("cluster_name");
        return CheckLogic.checkBatchMachineNode(clusterid,iplist, username, password, installPath);
    }



    @RequestMapping("/check_ip_password")
    @ResponseBody
    public JSONObject checkIpPassword(@RequestBody String jsonBody){
        JSONObject reqObject = JSONObject.fromObject(jsonBody);
        String ip = reqObject.getString("ip");
        String username = reqObject.getString("username");
        String password = reqObject.getString("password");
        return CheckLogic.checkUsernameAndPassword(ip, username, password);
    }

    @RequestMapping("/check_cluster_name")
    @ResponseBody
    public JSONObject checkClusterName(@RequestBody String jsonBody){
        JSONObject reqObject = JSONObject.fromObject(jsonBody);
        String clusterName = reqObject.getString("cluster_name");
        return CheckLogic.checkClusterName( clusterName );
    }

}
