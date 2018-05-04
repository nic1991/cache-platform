package com.newegg.ec.cache.app.controller;

import com.newegg.ec.cache.app.controller.security.WebSecurityConfig;
import com.newegg.ec.cache.app.dao.impl.NodeInfoDao;
import com.newegg.ec.cache.app.logic.ClusterLogic;
import com.newegg.ec.cache.app.model.Cluster;
import com.newegg.ec.cache.app.model.Host;
import com.newegg.ec.cache.app.model.RedisQueryParam;
import com.newegg.ec.cache.app.model.Response;
import com.newegg.ec.cache.app.model.User;
import com.newegg.ec.cache.core.userapi.UserAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by gl49 on 2018/4/21.
 */
@Controller
@RequestMapping("/cluster")
@UserAccess
public class ClusterController {
    @Autowired
    private ClusterLogic logic;

    @Autowired
    private NodeInfoDao nodeInfoTable;

    @RequestMapping("/clusterListManager")
    public String clusterListManager(Model model){
        return "clusterListManager";
    }

    @RequestMapping("/clusterManager")
    public String clusterManager(Model model){
        return "clusterManager";
    }

    @RequestMapping(value = "/redisQuery", method = RequestMethod.POST)
    @ResponseBody
    public Response redisQuery(@RequestBody RedisQueryParam redisQueryParam){
        Object res = logic.query( redisQueryParam );
        return Response.Result(0, res);
    }

    @RequestMapping(value = "/listCluster", method = RequestMethod.GET)
    @ResponseBody
    public Response listCluster(@SessionAttribute(WebSecurityConfig.SESSION_KEY) User user){
        List<Cluster> listCluster = null;
        if (user != null) {
            listCluster = logic.getClusterList( user.getUserGroup());
        }
        return Response.Result(0, listCluster);
    }

    @RequestMapping(value = "/getClusterListInfo", method = RequestMethod.GET)
    @ResponseBody
    public Response getClusterListInfo(@SessionAttribute(WebSecurityConfig.SESSION_KEY) User user){
        Map<String, Integer> clusterListInfo = null;
        if (user != null) {
            clusterListInfo = logic.getClusterListInfo(user.getUserGroup());
        }
        return Response.Result(0, clusterListInfo);
    }


    @RequestMapping(value = "/getCluster", method = RequestMethod.GET)
    @ResponseBody
    public Response getCluster(@RequestParam int id){
        Cluster cluster = logic.getCluster( id );
        return Response.Result(0, cluster);
    }

    @RequestMapping(value = "/getClusterHost", method = RequestMethod.GET)
    @ResponseBody
    public Response getClusterHost(@RequestParam int id){
        Host host = logic.getClusterHost( id );
        return Response.Result(0, host);
    }

    @RequestMapping(value = "/addCluster", method = RequestMethod.POST)
    @ResponseBody
    public Response addCluster(@RequestBody Cluster cluster){
        boolean res = logic.addCluster( cluster );
        return Response.Result(0, res);
    }

    @RequestMapping(value = "/removeCluster", method = RequestMethod.POST)
    @ResponseBody
    public Response removeCluster(@RequestParam String clusterId){
        int id = Integer.parseInt(clusterId);
        boolean res = logic.removeCluster(id);
        return Response.Result(0, res);
    }

    @RequestMapping(value = "/getClusterInfo", method = RequestMethod.GET)
    @ResponseBody
    public Response getClusterInfo(@RequestParam String ip, @RequestParam int port){
        Map<String, String> res = logic.getClusterInfo(ip, port);
        return Response.Result(0, res);
    }

    @RequestMapping(value = "/getClusterInfoByAddress", method = RequestMethod.GET)
    @ResponseBody
    public Response getClusterInfoByAddress(@RequestParam String address){
        Map<String, String> res = logic.getClusterInfo(address);
        return Response.Result(0, res);
    }

    @RequestMapping(value = "/getNodeInfo", method = RequestMethod.GET)
    @ResponseBody
    public Response getNodeInfo(@RequestParam String address){
        Map<String, String> res = logic.getNodeInfo(address);
        return Response.Result(0, res);
    }

    @RequestMapping(value = "/getRedisConfig", method = RequestMethod.GET)
    @ResponseBody
    public Response getRedisConfig(@RequestParam String address){
        Map<String, String> res = logic.getRedisConfig(address);
        System.out.println(res);
        return Response.Result(0, res);
    }

    @RequestMapping(value = "/nodeList", method = RequestMethod.GET)
    @ResponseBody
    public Response nodeList(@RequestParam String address){
        List<Map<String, String>> list = logic.nodeList(address);
        return Response.Result(0, list);
    }


    @RequestMapping(value = "/detailNodeList", method = RequestMethod.GET)
    @ResponseBody
    public Response detailNodeList(@RequestParam String address){
        Map<String, Map> detailNodeList = logic.detailNodeList(address);
        return Response.Result(0, detailNodeList);
    }
}
