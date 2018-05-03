package com.newegg.ec.cache.app.controller;

import com.newegg.ec.cache.app.controller.security.WebSecurityConfig;
import com.newegg.ec.cache.app.model.Response;
import com.newegg.ec.cache.core.userapi.UserAccess;
import com.newegg.ec.cache.app.logic.CheckLogic;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by gl49 on 2018/4/22.
 */
@Controller
@RequestMapping("/check")
@UserAccess(autoCreate = false)
public class CheckControl {
    @RequestMapping("/checkPortPass")
    @ResponseBody
    public Response checkPortPass(@RequestParam String ip, @RequestParam int port){
        return CheckLogic.checkPortPass(ip, port, true);
    }

    @RequestMapping("/checkPortNotPass")
    @ResponseBody
    public Response checkPortNotPass(@RequestParam String ip, @RequestParam int port){
        return CheckLogic.checkPortPass(ip, port, false);
    }

    @RequestMapping("/checkIp")
    @ResponseBody
    public Response checkIp(@RequestParam String ip){
        return CheckLogic.checkIp(ip);
    }

    @RequestMapping(value = "/checkClusterName", method = RequestMethod.GET)
    @ResponseBody
    public Response checkClusterName(@RequestParam String clusterId, @SessionAttribute(WebSecurityConfig.SESSION_KEY) String user){
        return CheckLogic.checkClusterName(clusterId, user);
    }

    @RequestMapping(value = "/batchHostAccess", method = RequestMethod.POST)
    @ResponseBody
    public Response batchHostAcess(@RequestBody String req){
        JSONObject object = JSONObject.fromObject( req );
        String clusterName = object.getString("clusterId");
        return CheckLogic.checkClusterName(clusterName, "fdsafdasfd");
    }
}
