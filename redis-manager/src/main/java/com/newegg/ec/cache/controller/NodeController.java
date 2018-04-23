package com.newegg.ec.cache.controller;

import com.newegg.ec.cache.component.NodeManager;
import com.newegg.ec.cache.controller.websocket.CreateClusterLogHandler;
import com.newegg.ec.cache.model.Response;
import com.newegg.ec.cache.plugin.INodeRequest;
import com.newegg.ec.cache.plugin.basemodel.PluginType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by lzz on 2018/4/20.
 */
@Controller
@RequestMapping("/node")
public class NodeController {
    INodeRequest nodeRequest;
    NodeManager nodeManager;

    @RequestMapping("/cluster")
    public String cluster(Model model, @RequestParam PluginType pluginType) {
        nodeRequest = nodeManager.factoryRequest(pluginType);
        String template = nodeRequest.showInstall();
        return template;
    }

    @RequestMapping("/create")
    public String create222(Model model) {
        return "createCluster";
    }

    @RequestMapping(value = "/sendMsg", method = RequestMethod.GET)
    @ResponseBody
    public Response getUser(@RequestParam int id){
        CreateClusterLogHandler.appendLog("abcd", "hello " + id);
        return Response.OK();
    }

    @RequestMapping("/manager")
    public String manager(Model model, @RequestParam PluginType pluginType) {
        nodeRequest = nodeManager.factoryRequest(pluginType);
        String template = nodeRequest.showManager();
        return template;
    }

}
