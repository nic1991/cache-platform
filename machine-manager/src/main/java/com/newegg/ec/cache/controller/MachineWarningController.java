package com.newegg.ec.cache.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.newegg.ec.cache.model.entity.MachineWarning;
import com.newegg.ec.cache.service.MachineWarningService;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * @author Jay.H.Zou
 * @date 2018/3/31
 */
@Controller
@RequestMapping("/rest/warning/*")
public class MachineWarningController {

    @Autowired
    private MachineWarningService machineWarningService;

    @RequestMapping(value = "/getWarningList", method = RequestMethod.POST)
    @ResponseBody
    public JSONArray getMachineWarningList(HttpServletRequest request) throws IOException {
        String ip = IOUtils.toString(request.getInputStream(), "UTF-8");
        JSONArray jsonArray = new JSONArray();
        List<MachineWarning> machineWarningList = machineWarningService.getMachineWarningListByIp(ip);
        if (machineWarningList != null && machineWarningList.size() > 0){
            for (MachineWarning machineWarning : machineWarningList){
                jsonArray.add(machineWarning);
            }
        }
        return jsonArray;
    }

    @RequestMapping(value = "/checkWarning", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject checkWarning(String id, String ip) {
        System.out.println(id);
        JSONObject jsonObject = new JSONObject();
        if (StringUtils.isNotBlank(id) && StringUtils.isNotBlank(ip)){
            int row = machineWarningService.checkWarning(id);
            jsonObject.put("code", row);
            /*if (row > 0){
                List<MachineWarning> machineWarningList = machineWarningService.getMachineWarningListByIp(ip);
                if (machineWarningList != null && machineWarningList.size() > 0){
                    for (MachineWarning machineWarning : machineWarningList){
                        jsonArray.add(machineWarning);
                    }
                }
            }*/
        }
        return jsonObject;
    }

    @RequestMapping(value = "/checkAllWarning", method = RequestMethod.POST)
    @ResponseBody
    public JSONArray checkAllWarning(HttpServletRequest request) throws IOException {
        final String ip = IOUtils.toString(request.getInputStream(), "UTF-8");
        JSONArray jsonArray = null;
        if (StringUtils.isNotBlank(ip)){
            jsonArray= new JSONArray();
            machineWarningService.checkAllWarning(ip);
            List<MachineWarning> machineWarningList = machineWarningService.getMachineWarningListByIp(ip);
            if (machineWarningList != null && machineWarningList.size() > 0){
                for (MachineWarning machineWarning : machineWarningList){
                    jsonArray.add(machineWarning);
                }
            }
        }
        return jsonArray;
    }

    @RequestMapping(value = "/deleteWarningById", method = RequestMethod.POST)
    @ResponseBody
    public JSONArray deleteWarningById(String id, String ip) {
        JSONArray jsonArray = null;
        if (StringUtils.isNotBlank(id) && StringUtils.isNotBlank(ip)){
            jsonArray= new JSONArray();
            machineWarningService.deleteWarningById(id);
            List<MachineWarning> machineWarningList = machineWarningService.getMachineWarningListByIp(ip);
            if (machineWarningList != null && machineWarningList.size() > 0){
                for (MachineWarning machineWarning : machineWarningList){
                    jsonArray.add(machineWarning);
                }
            }
        }
        return jsonArray;
    }

    @RequestMapping(value = "/deleteAllWarningByIp", method = RequestMethod.POST)
    @ResponseBody
    public JSONArray deleteAllWarningByIp(HttpServletRequest request) throws IOException {
        final String ip = IOUtils.toString(request.getInputStream(), "UTF-8");
        JSONArray jsonArray = null;
        if (StringUtils.isNotBlank(ip)){
            jsonArray= new JSONArray();
            machineWarningService.deleteAllCheckedWarningByIp(ip);
            List<MachineWarning> machineWarningList = machineWarningService.getMachineWarningListByIp(ip);
            if (machineWarningList != null && machineWarningList.size() > 0){
                for (MachineWarning machineWarning : machineWarningList){
                    jsonArray.add(machineWarning);
                }
            }
        }
        return jsonArray;
    }
}
