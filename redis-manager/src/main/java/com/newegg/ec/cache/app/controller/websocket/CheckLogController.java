package com.newegg.ec.cache.app.controller.websocket;

import com.newegg.ec.cache.app.controller.security.WebSecurityConfig;
import com.newegg.ec.cache.app.model.Response;
import com.newegg.ec.cache.app.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by lzz on 2018/5/4.
 */
@Controller
@RequestMapping("/logcheck")
public class CheckLogController {
    private static void checkLog(String clusterId, String msg){
        CreateClusterLogHandler.appendLog(clusterId, msg);
    }


    @RequestMapping(value = "/checkClusterName", method = RequestMethod.GET)
    @ResponseBody
    public Response checkClusterName(@RequestParam String clusterId, @SessionAttribute(WebSecurityConfig.SESSION_KEY) User user){
        System.out.println( user );
        return CheckLogLogic.checkClusterName(clusterId, user.getId());
    }

}
