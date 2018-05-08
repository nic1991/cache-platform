package com.newegg.ec.cache.app.controller;

import com.newegg.ec.cache.app.component.NodeManager;
import com.newegg.ec.cache.app.controller.security.WebSecurityConfig;
import com.newegg.ec.cache.app.model.Common;
import com.newegg.ec.cache.app.model.Response;
import com.newegg.ec.cache.app.model.User;
import com.newegg.ec.cache.core.userapi.UserAccess;
import com.newegg.ec.cache.plugin.INodeOperate;
import com.newegg.ec.cache.plugin.INodeRequest;
import com.newegg.ec.cache.plugin.basemodel.PluginType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lzz on 2018/4/20.
 */
@Controller
@RequestMapping("/node")
@UserAccess
public class NodeController {
    private INodeRequest nodeRequest;
    private INodeOperate nodeOperate;
    @Resource
    private NodeManager nodeManager;

    @RequestMapping("/selectClusterType")
    public String selectType(Model model) {
        return "selectClusterType";
    }

    @RequestMapping("/install")
    public String cluster(Model model, @RequestParam PluginType pluginType, @SessionAttribute(Common.SESSION_USER_KEY) User user) {
        nodeRequest = nodeManager.factoryRequest(pluginType);
        String template = nodeRequest.showInstall();
        model.addAttribute("user", user);
        return template;
    }

    @RequestMapping("/manager")
    public String manager(Model model, @RequestParam PluginType pluginType, @SessionAttribute(Common.SESSION_USER_KEY) User user) {
        nodeRequest = nodeManager.factoryRequest(pluginType);
        String template = nodeRequest.showManager();
        return template;
    }


    @RequestMapping(value = "/getImageList", method = RequestMethod.GET)
    @ResponseBody
    public Response getImageList(@RequestParam PluginType pluginType, @SessionAttribute(Common.SESSION_USER_KEY) User user){
        nodeOperate = nodeManager.factoryOperate( pluginType );
        List<String> imageList = nodeOperate.getImageList();
        return Response.Result(Response.DEFAULT, imageList);
    }

}
