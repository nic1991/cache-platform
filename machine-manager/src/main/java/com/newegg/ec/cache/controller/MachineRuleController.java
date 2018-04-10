package com.newegg.ec.cache.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.newegg.ec.cache.model.entity.Machine;
import com.newegg.ec.cache.model.entity.MachineRule;
import com.newegg.ec.cache.model.entity.MachineRule;
import com.newegg.ec.cache.service.MachineRuleService;
import com.newegg.ec.cache.service.MachineService;
import com.newegg.ec.cache.utils.CommonUtils;
import com.newegg.ec.cache.utils.MathExpressionCalculateUtil;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.groovy.tools.shell.IO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jay.H.Zou
 * @date 2018/3/30
 */
@Controller
@RequestMapping("/rest/rule/*")
public class MachineRuleController {
    private static final Logger logger = Logger.getLogger(MachineRuleController.class);

    @Autowired
    private MachineRuleService machineRuleService;

    @Autowired
    private MachineService machineService;

    @RequestMapping(value = "/getRuleList", method = RequestMethod.POST)
    @ResponseBody
    public JSONArray getRuleList(HttpServletRequest request) throws IOException {
        final String ip = IOUtils.toString(request.getInputStream(), "UTF-8");
        List<MachineRule> machineRuleList = machineRuleService.getMachineRuleList(ip);
        JSONArray jsonArray = new JSONArray();
        if (machineRuleList != null) {
            machineRuleList.forEach(machineRule -> {
                jsonArray.add(machineRule);
            });
        }
        return jsonArray;
    }

    @RequestMapping(value = "/getMachineRuleById",  method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getMachineRuleById(HttpServletRequest request) throws IOException {
        final String id = IOUtils.toString(request.getInputStream(), "UTF-8");
        MachineRule machineRule = machineRuleService.getMachineRuleById(id);
        JSONObject jsonObject = null;
        if (machineRule != null){
            jsonObject = JSONObject.parseObject(JSON.toJSONString(machineRule));
        }
        return jsonObject;
    }

    @RequestMapping(value = "/checkMachineRule", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject checkMachineRule(HttpServletRequest request) throws IOException {
        String formula = IOUtils.toString(request.getInputStream(), "UTF-8");
        JSONObject jsonObject = new JSONObject();
        if (StringUtils.isNotBlank(formula)){
            jsonObject.put("code", MathExpressionCalculateUtil.checkRule(formula));
        } else {
            jsonObject.put("code", 0);
        }
        return jsonObject;
    }

    @RequestMapping(value = "/saveMachineRule", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject addMachineRule(HttpServletRequest request) throws IOException {
        final String data = IOUtils.toString(request.getInputStream(), "UTF-8");
        JSONObject jsonObject = JSONObject.parseObject(data);
        String ip = jsonObject.getString("machineIp");
        Machine machineByIp = machineService.getMachineByIp(ip);
        if (machineByIp == null) {
            jsonObject.put("code", 0);
            return jsonObject;
        }
        Integer type = jsonObject.getInteger("type");
        MachineRule machineRule = new MachineRule();
        machineRule.setIp(ip);
        machineRule.setLimitName(jsonObject.getString("limitName"));
        machineRule.setFormula(jsonObject.getString("formula").replaceAll(" ", ""));
        machineRule.setDescription(jsonObject.getString("description"));
        machineRule.setUpdateTime(CommonUtils.getFormatTime());

        Map<String, String> params = new HashMap<>();
        params.put("ip", machineRule.getIp());
        params.put("formula", machineRule.getFormula());
        params.put("id", jsonObject.getString("machineId"));
        MachineRule machineRuleByFormula = machineRuleService.getMachineRuleByFormula(params);
        if (machineRuleByFormula != null){
            jsonObject.put("code", 2);
            return jsonObject;
        }
        if (type == 0){
            // add rule
            machineRule.setId(CommonUtils.getUuid());
            machineRuleService.addMachineRule(machineRule);
            jsonObject.put("code", 1);
        } else if (type == 1){
            // update rule
            machineRule.setId(jsonObject.getString("machineId"));
            machineRuleService.updateMachineRule(machineRule);
            jsonObject.put("code", 1);
        }
        return jsonObject;
    }

    @RequestMapping(value = "/deleteMachineRuleById", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject deleteMachineRuleById(HttpServletRequest request) throws IOException {
        final String id = IOUtils.toString(request.getInputStream(), "UTF-8");
        int row = machineRuleService.deleteMachineRuleById(id);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", row);
        return jsonObject;
    }
}
