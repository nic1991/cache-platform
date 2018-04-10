package com.newegg.ec.cache.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.newegg.ec.cache.model.entity.Machine;
import com.newegg.ec.cache.model.entity.MachineMonitorInfo;
import com.newegg.ec.cache.service.BizService;
import com.newegg.ec.cache.service.MachineMonitorInfoService;
import com.newegg.ec.cache.service.MachineService;
import com.newegg.ec.cache.utils.CommonUtils;
import com.newegg.ec.cache.utils.LinuxMonitorUtil;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
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
 * Created by lf52 on 2018/3/20.
 */
@Controller
@RequestMapping("/rest/machine/*")
public class MachineController {

    public static final int size = 40;

    @Autowired
    private MachineService machineService;
    @Autowired
    private MachineMonitorInfoService machineMonitorInfoService;

    @Autowired
    private BizService bizService;

    @RequestMapping("/getMachine")
    @ResponseBody
    public JSONArray machine(HttpServletRequest request) throws IOException {

        String data = IOUtils.toString(request.getInputStream(),"UTF-8");
        JSONObject jsonObject = (JSONObject) JSONObject.parse(data);
        String page =  jsonObject.getString("page");
        Map<String, Object > param = new HashMap();
        if(StringUtils.isNotEmpty(page)){
            param.put("page",getPageSize(page));
        }
        param.put("size",size);
        List<Machine> machineList = machineService.getMachineList(param);
        JSONArray array = new JSONArray();
        machineList.forEach(machine->{
            JSONObject obj = new JSONObject();
            MachineMonitorInfo info = machineMonitorInfoService.getNewestInfo(machine.getIp());
            if(info != null){
                obj.put("Memory",info.getMemory());
                obj.put("Swap",info.getSwap());
                obj.put("Disk",info.getDisk());
            }else{
                obj.put("Memory","loading...");
                obj.put("Swap","loading...");
                obj.put("Disk","loading...");
            }

            obj.put("MachineIP", machine.getIp());
            obj.put("MachineName",machine.getMachineName());
            obj.put("Location",machine.getLocation());
            obj.put("CoreSize",machine.getCoreSize());
            obj.put("IsVM",machine.getIsVM());
            if(StringUtils.isNotEmpty(machine.getHostIp())){
                obj.put("HostIP",machine.getHostIp());
            }else{
                obj.put("HostIP","-");
            }
            obj.put("MachineDesc",machine.getRemark());
            obj.put("IsMonitor",machine.getIsMonitor());
            obj.put("Level",machine.getScore());
            obj.put("warningNum",machine.getWarningNum());
            array.add(obj);
        });
        return array;
    }

    @RequestMapping(value = "/saveMachine", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject saveMachine(HttpServletRequest request) throws IOException {
        String data = IOUtils.toString(request.getInputStream(), "UTF-8");
        JSONObject jsonObject = JSONObject.parseObject(data);

        Integer operateType = jsonObject.getInteger("operateType");
        int subBegin = data.lastIndexOf(",");
        data = data.substring(0, subBegin) + data.substring(data.length()-1, data.length());

        Machine machine = JSONObject.parseObject(data, Machine.class);
        machine.setUpdateTime(CommonUtils.getNowDate());
        Machine existsMachine = machineService.getMachineByIp(machine.getIp());
        JSONObject object = new JSONObject();
        if (operateType == 0){
            if (null != existsMachine){
                object.put("code", "1");
            } else {
                if (machineService.Add(machine)){
                    object.put("code", "0");
                } else {
                    object.put("code", "2");
                }
            }
        } else {
            machine.setScore("-");
            if (machineService.Update(machine)){
                object.put("code", "0");
            } else {
                object.put("code", "2");
            }
        }
        return object;
    }

    @RequestMapping(value = "/getMachineById", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getMachineById(HttpServletRequest request) throws IOException {
        String ip = IOUtils.toString(request.getInputStream(), "UTF-8");
        Machine machine = machineService.getMachineByIp(ip);
        JSONObject obj = null;
        if(null != machine){
            obj = new JSONObject();
            obj.put("MachineIP",machine.getIp());
            obj.put("Location",machine.getLocation());
            obj.put("MachineName",machine.getMachineName());
            obj.put("Usename",machine.getUsename());
            obj.put("Passwd",machine.getPasswd());
            obj.put("CoreSize",machine.getCoreSize());
            obj.put("Memory",machine.getMemory());
            obj.put("IsVM",machine.getIsVM());
            obj.put("HostIP",machine.getHostIp());
            obj.put("MachineDesc",machine.getRemark());
            obj.put("IsMonitor", machine.getIsMonitor());
        }
        return obj;
    }

    @RequestMapping(value = "/deleteMachineById", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject deleteMachineById(HttpServletRequest request) throws IOException {
        String ip = IOUtils.toString(request.getInputStream(), "UTF-8");
        boolean delete = machineService.Delete(ip);
        JSONObject object = new JSONObject();
        if (delete){
            object.put("code", "0");
        } else {
            object.put("code", "1");
        }
        return object;
    }

    @RequestMapping("/machineAnalysis")
    @ResponseBody
    public JSONObject machineAnalysis(HttpServletRequest request) throws IOException {

        String ip = IOUtils.toString(request.getInputStream(), "UTF-8");
        return bizService.MachineAnalysis(ip.trim());

    }

    @RequestMapping("/checkAddInfo")
    @ResponseBody
    public JSONObject CheckAddInfo(HttpServletRequest request) throws IOException {

        String data = IOUtils.toString(request.getInputStream(), "UTF-8");
        JSONObject jsonObject = JSONObject.parseObject(data);
        String ip = jsonObject.getString("ip");
        String usename = jsonObject.getString("usename");
        String passwd = jsonObject.getString("passwd");
        String cmd = LinuxMonitorUtil.getCheckInfoCmd();
        Map<String,String> map = LinuxMonitorUtil.getMonitor(ip, usename, passwd, cmd);
        JSONObject result = new JSONObject();
        if(map != null && map.size() > 0){
            result.put("code",0);
            result.put("core",map.get("core"));
            result.put("memory",map.get("memory_total"));
        }else{
            result.put("code",1);
        }
        return result;

    }


    private int getPageSize(String page){
        return (Integer.parseInt(page) - 1) * size ;
    }
}
