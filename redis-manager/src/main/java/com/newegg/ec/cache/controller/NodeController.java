package com.newegg.ec.cache.controller;

import com.newegg.ec.cache.component.NodeManager;
import com.newegg.ec.cache.plugin.manager.INodeRequest;
import com.newegg.ec.cache.plugin.model.PluginType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        String template = nodeRequest.showInstall();;
        return template;
    }

    @RequestMapping("/manager")
    public String manager(Model model, @RequestParam PluginType pluginType) {
        nodeRequest = nodeManager.factoryRequest(pluginType);
        String template = nodeRequest.showManager();;
        return template;
    }
}
