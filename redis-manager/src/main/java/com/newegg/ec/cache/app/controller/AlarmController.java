package com.newegg.ec.cache.app.controller;

import com.newegg.ec.cache.app.dao.IClusterCheckLogDao;
import com.newegg.ec.cache.app.dao.IClusterCheckRuleDao;
import com.newegg.ec.cache.app.model.ClusterCheckLog;
import com.newegg.ec.cache.app.model.ClusterCheckRule;
import com.newegg.ec.cache.app.model.Response;
import com.newegg.ec.cache.app.util.CommonUtil;
import com.newegg.ec.cache.app.util.DateUtil;
import com.newegg.ec.cache.app.util.MathExpressionCalculateUtil;
import com.newegg.ec.cache.core.userapi.UserAccess;
import net.sf.json.JSON;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tc72 on 2018/5/3.
 */
@Controller
@RequestMapping("/alarm")
@UserAccess
public class AlarmController {
    @Resource
    IClusterCheckRuleDao clusterCheckRuleDao;
    @Resource
    IClusterCheckLogDao clusterCheckLogDao;

    @RequestMapping("/alarmSystem")
    public String ruleList(Model model){
        return "alarmSystem";
    }

    @RequestMapping(value = "/getRuleList", method = RequestMethod.GET)
    @ResponseBody
    public Response getRuleList(@RequestParam String clusterId){
        List<ClusterCheckRule> res = clusterCheckRuleDao.getClusterRuleList( clusterId );
        return Response.Result(0, res);
    }

    @RequestMapping(value = "/addRule", method = RequestMethod.POST)
    @ResponseBody
    public Response addRule(@RequestBody ClusterCheckRule rule){
        Boolean check = MathExpressionCalculateUtil.checkRule(rule.getFormula());
        if( check ){
            rule.setId(CommonUtil.getUuid());
            rule.setUpdateTime(DateUtil.getTime());
            System.out.println(rule);
            Boolean boo = clusterCheckRuleDao.addClusterCheckRule( rule );

            return Response.Result(0, boo);
        }else {
            return Response.Result(0, Boolean.FALSE);
        }
    }

    @RequestMapping(value = "/checkRule", method = RequestMethod.POST)
    @ResponseBody
    public Response checkRule(@RequestBody ClusterCheckRule rule){
        System.out.println( rule );
        Boolean check = MathExpressionCalculateUtil.checkRule(rule.getFormula());
        System.out.println( check + "----------");
        if(check){
            return Response.Info("success!");
        }else {
            return Response.Warn("you are fail");
        }
    }

    @RequestMapping(value = "/deleteRule", method = RequestMethod.GET)
    @ResponseBody
    public Response deleteRule(@RequestParam String ruleId){
        Map<String,Object> params = new HashMap<>();
        params.put("id",ruleId);
        Boolean boo = clusterCheckRuleDao.delClusterCheckRule( params );
        if(boo){
            return Response.Info("success!");
        }else {
            return Response.Warn("you are fail");
        }
    }

    @RequestMapping(value = "/getCaseLogs", method = RequestMethod.GET)
    @ResponseBody
    public Response getCaseList(@RequestParam String clusterId){
        Map<String,Object> params = new HashMap<>();
        params.put("clusterId", clusterId);
        List<ClusterCheckLog> res = clusterCheckLogDao.getClusterCheckLogs( params );
        return Response.Result(0, res);
    }

    @RequestMapping(value = "/deleteCaseLog", method = RequestMethod.GET)
    @ResponseBody
    public Response deleteCaseLog(@RequestParam String logId){
        Map<String,Object> params = new HashMap<>();
        params.put("id",logId);
        Boolean boo = clusterCheckLogDao.delLogs( params );
        if(boo){
            return Response.Info("success!");
        }else {
            return Response.Warn("you are fail");
        }
    }

}
