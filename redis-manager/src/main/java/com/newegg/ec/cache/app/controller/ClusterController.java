package com.newegg.ec.cache.app.controller;

import com.newegg.ec.cache.app.component.RedisManager;
import com.newegg.ec.cache.core.userapi.UserAccess;
import com.newegg.ec.cache.app.logic.ClusterLogic;
import com.newegg.ec.cache.app.model.Cluster;
import com.newegg.ec.cache.app.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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

    @RequestMapping(value = "/listCluster", method = RequestMethod.GET)
    @ResponseBody
    public Response listCluster(@RequestParam String group){
        List<Cluster> listCluster = logic.getClusterList( group );
        return Response.Result(0, listCluster);
    }

    @RequestMapping(value = "/getCluster", method = RequestMethod.GET)
    @ResponseBody
    public Response getCluster(@RequestParam int id){
        Cluster cluster = logic.getCluster( id );
        return Response.Result(0, cluster);
    }

    @RequestMapping(value = "/addCluster", method = RequestMethod.POST)
    @ResponseBody
    public Response addCluster(@RequestBody Cluster cluster){
        boolean res = logic.addCluster( cluster );
        return Response.Result(0, res);
    }

    @RequestMapping(value = "/removeCluster", method = RequestMethod.GET)
    @ResponseBody
    public Response removeCluster(@RequestParam int id, @RequestParam String clusterName){
        boolean res = logic.removeCluster(id, clusterName);
        return Response.Result(0, res);
    }

    @RequestMapping(value = "/getClusterInfo", method = RequestMethod.GET)
    @ResponseBody
    public Response removeCluster(@RequestParam String ip, @RequestParam int port){
        Map<String, String> res = logic.getClusterInfo(ip, port);
        return Response.Result(0, res);
    }
}
