package com.newegg.ec.cache.controller;

import com.newegg.ec.cache.core.userapi.UserAccess;
import com.newegg.ec.cache.logic.ClusterLogic;
import com.newegg.ec.cache.model.Cluster;
import com.newegg.ec.cache.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public Response list(){
        List<Cluster> listCluster = logic.getClusterList();
        return Response.Obj(0, listCluster);
    }

    @RequestMapping(value = "/getCluster", method = RequestMethod.GET)
    @ResponseBody
    public Response getCluster(@RequestParam int id){
        Cluster cluster = logic.getCluster( id );
        return Response.Obj(0, cluster);
    }

    @RequestMapping(value = "/addCluster", method = RequestMethod.POST)
    @ResponseBody
    public Response getCluster(@RequestBody Cluster cluster){
        boolean res = logic.addCluster( cluster );
        return Response.Obj(0, res);
    }

    @RequestMapping(value = "/removeCluster", method = RequestMethod.GET)
    @ResponseBody
    public Response removeCluster(@RequestParam int id){
        boolean res = logic.removeCluster( id );
        return Response.Obj(0, res);
    }
}
