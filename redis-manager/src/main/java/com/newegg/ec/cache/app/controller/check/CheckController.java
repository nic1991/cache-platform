package com.newegg.ec.cache.app.controller.check;

import com.newegg.ec.cache.app.controller.security.WebSecurityConfig;
import com.newegg.ec.cache.app.model.Response;
import com.newegg.ec.cache.app.model.User;
import com.newegg.ec.cache.app.util.MathExpressionCalculateUtil;
import com.newegg.ec.cache.core.userapi.UserAccess;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

/**
 * Created by gl49 on 2018/4/22.
 */
@Controller
@RequestMapping("/check")
@UserAccess(autoCreate = false)
public class CheckController {
    @Resource
    private CheckLogic logic;
    @RequestMapping("/checkPortPass")
    @ResponseBody
    public Response checkPortPass(@RequestParam String ip, @RequestParam int port){
        return logic.checkPortPass(ip, port, true);
    }

    @RequestMapping("/checkPortNotPass")
    @ResponseBody
    public Response checkPortNotPass(@RequestParam String ip, @RequestParam int port){
        return logic.checkPortPass(ip, port, false);
    }

    @RequestMapping("/checkIp")
    @ResponseBody
    public Response checkIp(@RequestParam String ip){
        return logic.checkIp(ip);
    }

    @RequestMapping("/checkAddress")
    @ResponseBody
    public Response checkAddress(@RequestParam String address){
        return logic.checkAddress(address);
    }


    @RequestMapping(value = "/checkClusterName", method = RequestMethod.GET)
    @ResponseBody
    public Response checkClusterName(@RequestParam String clusterId, @SessionAttribute(WebSecurityConfig.SESSION_KEY) User user){
        return logic.checkClusterNameByUserid( clusterId, user.getId());
    }

    @RequestMapping(value = "/checkRule", method = RequestMethod.POST)
    @ResponseBody
    public Response checkRule(@RequestBody String req){
        JSONObject object = JSONObject.fromObject( req );
        String formula = object.getString("formula");
        Boolean check = MathExpressionCalculateUtil.checkRule( formula );
        return check ? Response.Success() : Response.Error("fail");
    }

}
