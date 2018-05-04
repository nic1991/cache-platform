package com.newegg.ec.cache.app.controller;

import com.newegg.ec.cache.app.dao.IClusterCheckRuleDao;
import com.newegg.ec.cache.app.model.Cluster;
import com.newegg.ec.cache.app.model.ClusterCheckRule;
import com.newegg.ec.cache.app.model.Response;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by tc72 on 2018/5/3.
 */
@Controller
@RequestMapping("/alarm")
public class AlarmController {
    @Resource
    IClusterCheckRuleDao clusterCheckRuleDao;

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
}
