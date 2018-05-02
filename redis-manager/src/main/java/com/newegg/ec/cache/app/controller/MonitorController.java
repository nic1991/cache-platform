package com.newegg.ec.cache.app.controller;

import com.newegg.ec.cache.app.logic.MonitorLogic;
import com.newegg.ec.cache.app.model.NodeInfo;
import com.newegg.ec.cache.app.model.RedisSlowLog;
import com.newegg.ec.cache.app.model.Response;
import com.newegg.ec.cache.app.model.SlowLogParam;
import com.newegg.ec.cache.core.userapi.UserAccess;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by gl49 on 2018/4/21.
 */
@Controller
@RequestMapping("/monitor")
@UserAccess(name = "monitor")
public class MonitorController {
    @Resource
    private MonitorLogic logic;

    @RequestMapping(value = "/manager")
    public String manager(Model model){
        return "monitor";
    }

    @RequestMapping("/clusterMonitorList")
    public String accessMonitorList(){
        return "clusterMonitorList";
    }

    @RequestMapping(value = "/getGroupNodeInfo", method = RequestMethod.GET)
    @ResponseBody
    public Response getGroupNodeInfo(@RequestParam int clusterId,@RequestParam int startTime,@RequestParam int endTime,@RequestParam  String host,@RequestParam String type,@RequestParam String date){
        List<NodeInfo> list = logic.getGroupNodeInfo(clusterId, startTime, endTime, host, type, date);
        return Response.Result(Response.DEFAULT, list);
    }

    @RequestMapping(value = "/getMaxField", method = RequestMethod.GET)
    @ResponseBody
    public Response getMaxField(int clusterId, int startTime, int endTime, String key, int limit){
        List<Map> list = logic.getMaxField(clusterId, startTime, endTime, key, limit);
        return Response.Result(0, list);
    }

    @RequestMapping(value = "/getMinField", method = RequestMethod.GET)
    @ResponseBody
    public Response getMinField(int clusterId,int startTime,int endTime, String key, int limit){
        List<Map> list = logic.getMinField(clusterId, startTime, endTime, key, limit);
        return Response.Result(0, list);
    }

    @RequestMapping(value = "/getAvgField", method = RequestMethod.GET)
    @ResponseBody
    public Response getAvgField(int clusterId, int startTime, int endTime, String host, String key){
        String res = logic.getAvgField(clusterId, startTime, endTime, host, key);
        return Response.Result(0, res);
    }

    @RequestMapping(value = "/getAllField", method = RequestMethod.GET)
    @ResponseBody
    public Response getAllField(int clusterId, int startTime, int endTime, String key){
        String res = logic.getAllField(clusterId, startTime, endTime, key);
        return Response.Result(0, res);
    }

    @RequestMapping(value = "/getLastNodeInfo", method = RequestMethod.GET)
    @ResponseBody
    public Response getLastNodeInfo(int clusterId, int startTime, int endTime, String host){
        NodeInfo list = logic.getLastNodeInfo(clusterId, startTime, endTime, host);
        return Response.Result(0, list);
    }


    @RequestMapping(value = "/slowLogs", method = RequestMethod.POST)
    @ResponseBody
    public Response slowLogs(@RequestBody SlowLogParam logParam){
        MonitorLogic logic = new MonitorLogic();
        List<RedisSlowLog> redisSlowLogs = logic.getSlowLogs( logParam );
        return  Response.Result(0, redisSlowLogs);
    }
}
