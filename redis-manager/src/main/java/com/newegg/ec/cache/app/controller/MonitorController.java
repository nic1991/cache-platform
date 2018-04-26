package com.newegg.ec.cache.app.controller;

import com.newegg.ec.cache.app.logic.MonitorLogic;
import com.newegg.ec.cache.app.model.NodeInfo;
import com.newegg.ec.cache.app.model.Response;
import net.sf.json.JSONObject;
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
public class MonitorController {
    @Resource
    private MonitorLogic logic;

    @RequestMapping(value = "/manager")
    public java.lang.String manager(Model model){
        return "monitor";
    }

    @RequestMapping(value = "/getGroupNodeInfo", method = RequestMethod.GET)
    @ResponseBody
    public Response getGroupNodeInfo(String tableName,int startTime,int endTime, String host, String type, String date){
        List<NodeInfo> list = logic.getGroupNodeInfo(tableName, startTime, endTime, host, type, date);
        return Response.Result(0, list);
    }

    @RequestMapping(value = "/getMaxField", method = RequestMethod.GET)
    @ResponseBody
    public Response getMaxField(String tableName, int startTime, int endTime, String key, int limit){
        List<Map> list = logic.getMaxField(tableName, startTime, endTime, key, limit);
        return Response.Result(0, list);
    }

    @RequestMapping(value = "/getMinField", method = RequestMethod.GET)
    @ResponseBody
    public Response getMinField(String tableName,int startTime,int endTime, String key, int limit){
        List<Map> list = logic.getMinField(tableName, startTime, endTime, key, limit);
        return Response.Result(0, list);
    }

    @RequestMapping(value = "/getAvgField", method = RequestMethod.GET)
    @ResponseBody
    public Response getAvgField(String tableName, int startTime, int endTime, String host, String key){
        String res = logic.getAvgField(tableName, startTime, endTime, host, key);
        return Response.Result(0, res);
    }

    @RequestMapping(value = "/getAllField", method = RequestMethod.GET)
    @ResponseBody
    public Response getAllField(String tableName, int startTime, int endTime, String key){
        String res = logic.getAllField(tableName, startTime, endTime, key);
        return Response.Result(0, res);
    }

    @RequestMapping(value = "/getLastNodeInfo", method = RequestMethod.GET)
    @ResponseBody
    public Response getLastNodeInfo(String tableName, int startTime, int endTime, String host){
        NodeInfo list = logic.getLastNodeInfo(tableName, startTime, endTime, host);
        return Response.Result(0, list);
    }


    @RequestMapping(value = "/slowLogs", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject slowLogs(@RequestBody String jsonBody){
        JSONObject reqObject =  JSONObject.fromObject( jsonBody );
        MonitorLogic logic = new MonitorLogic();
        JSONObject jsonObject = new JSONObject();
        jsonObject = logic.getSlowLogs( reqObject );
        return  jsonObject;
    }
}
