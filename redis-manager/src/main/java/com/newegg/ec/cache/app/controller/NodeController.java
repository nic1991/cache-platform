package com.newegg.ec.cache.app.controller;

import com.newegg.ec.cache.app.component.NodeManager;
import com.newegg.ec.cache.app.controller.security.WebSecurityConfig;
import com.newegg.ec.cache.app.model.User;
import com.newegg.ec.cache.plugin.INodeRequest;
import com.newegg.ec.cache.plugin.basemodel.PluginType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.annotation.Resource;

/**
 * Created by lzz on 2018/4/20.
 */
@Controller
@RequestMapping("/node")
public class NodeController {
    private INodeRequest nodeRequest;
    @Resource
    private NodeManager nodeManager;

    @RequestMapping("/selectClusterType")
    public String selectType(Model model) {
        return "selectClusterType";
    }

    @RequestMapping("/install")
    public String cluster(Model model, @RequestParam PluginType pluginType, @SessionAttribute(WebSecurityConfig.SESSION_KEY) User user) {
        nodeRequest = nodeManager.factoryRequest(pluginType);
        String template = nodeRequest.showInstall();
        model.addAttribute("user", user);
        return template;
    }

    @RequestMapping("/manager")
    public String manager(Model model, @RequestParam PluginType pluginType) {
        nodeRequest = nodeManager.factoryRequest(pluginType);
        String template = nodeRequest.showManager();
        return template;
    }

}
