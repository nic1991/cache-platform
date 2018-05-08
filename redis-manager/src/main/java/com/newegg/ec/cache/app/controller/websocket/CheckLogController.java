package com.newegg.ec.cache.app.controller.websocket;

import com.newegg.ec.cache.app.controller.security.WebSecurityConfig;
import com.newegg.ec.cache.app.model.Common;
import com.newegg.ec.cache.app.model.Response;
import com.newegg.ec.cache.app.model.User;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by lzz on 2018/5/4.
 */
@Controller
@RequestMapping("/logcheck")
public class CheckLogController {
    @Resource
    private CheckLogLogic checkLogLogic;

    @RequestMapping(value = "/checkClusterName", method = RequestMethod.GET)
    @ResponseBody
    public Response checkClusterName(@RequestParam String clusterId){
        return checkLogLogic.checkClusterName(clusterId);
    }

    @RequestMapping(value = "/batchHumpbackContainerName", method = RequestMethod.POST)
    @ResponseBody
    public Response checkBatchHumpbackContainerName(@RequestBody String req){
        JSONObject jsonObject = JSONObject.fromObject( req );
        System.out.println( jsonObject  + "container");
        return Response.Success();
    }

    @RequestMapping(value = "/batchHostNotPass", method = RequestMethod.POST)
    @ResponseBody
    public Response checkBatchHostNotPass(@RequestBody String req){
        JSONObject jsonObject = JSONObject.fromObject( req );
        String ipList = jsonObject.getString("iplist");
        System.out.println( ipList );
        return checkLogLogic.checkBatchHostNotPass( ipList );
    }
}
