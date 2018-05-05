package com.newegg.ec.cache.app.controller.check;

import com.newegg.ec.cache.app.model.Response;
import com.newegg.ec.cache.app.util.MathExpressionCalculateUtil;
import com.newegg.ec.cache.core.userapi.UserAccess;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by gl49 on 2018/4/22.
 */
@Controller
@RequestMapping("/check")
@UserAccess(autoCreate = false)
public class CheckController {
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


    @RequestMapping(value = "/batchHostAccess", method = RequestMethod.POST)
    @ResponseBody
    public Response batchHostAcess(@RequestBody String req){
        JSONObject object = JSONObject.fromObject( req );
        String clusterName = object.getString("clusterId");
        return Response.Success();
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
