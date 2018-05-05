package com.newegg.ec.cache.app.controller.websocket;

import com.newegg.ec.cache.app.controller.security.WebSecurityConfig;
import com.newegg.ec.cache.app.model.Response;
import com.newegg.ec.cache.app.model.User;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by lzz on 2018/5/4.
 */
@Controller
@RequestMapping("/logcheck")
public class CheckLogController {
    private CheckLogLogic checkLogLogic;

    @RequestMapping(value = "/checkClusterName", method = RequestMethod.GET)
    @ResponseBody
    public Response checkClusterName(@RequestParam String clusterId, @SessionAttribute(WebSecurityConfig.SESSION_KEY) User user){
        checkLogLogic = new CheckLogLogic(user.getId());
        return checkLogLogic.checkClusterName(clusterId);
    }

    @RequestMapping(value = "/batchHumpbackContainerName", method = RequestMethod.POST)
    @ResponseBody
    public Response checkBatchHumpbackContainerName(@RequestBody String req, @SessionAttribute(WebSecurityConfig.SESSION_KEY) User user){
        JSONObject jsonObject = JSONObject.fromObject( req );
        System.out.println( jsonObject  + "container");
        checkLogLogic = new CheckLogLogic(user.getId());
        return Response.Success();
    }

    @RequestMapping(value = "/batchHostNotPass", method = RequestMethod.POST)
    @ResponseBody
    public Response checkBatchHostNotPass(@RequestBody String req, @SessionAttribute(WebSecurityConfig.SESSION_KEY) User user){
        JSONObject jsonObject = JSONObject.fromObject( req );
        String ipList = jsonObject.getString("iplist");
        System.out.println( ipList );
        checkLogLogic = new CheckLogLogic(user.getId());
        return checkLogLogic.checkBatchHostNotPass( ipList );
    }
}
