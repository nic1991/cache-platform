package com.newegg.ec.cache.controller;

import com.newegg.ec.cache.logic.MonitorLogic;
import com.newegg.ec.cache.model.NodeInfo;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by gl49 on 2018/4/21.
 */
@Controller
@RequestMapping("/monitor")
public class MonitorController {
    @Resource
    private MonitorLogic logic;

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    @ResponseBody
    public boolean create(@RequestParam String tableName){
        return logic.addCluster( tableName );
    }


    @RequestMapping(value = "/node-info", method = RequestMethod.GET)
    @ResponseBody
    public NodeInfo nodeInfo(@RequestParam String tableName, @RequestParam int id){
        return logic.getNodeInfo(tableName, id);
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
