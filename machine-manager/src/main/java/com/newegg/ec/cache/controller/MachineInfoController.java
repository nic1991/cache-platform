package com.newegg.ec.cache.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.newegg.ec.cache.dao.MachineNetWorkDao;
import com.newegg.ec.cache.model.constant.Constant;
import com.newegg.ec.cache.model.entity.Machine;
import com.newegg.ec.cache.model.entity.MachineMonitorInfo;
import com.newegg.ec.cache.model.entity.MachineNetWorkInfo;
import com.newegg.ec.cache.model.entity.SystemConfig;
import com.newegg.ec.cache.service.BizService;
import com.newegg.ec.cache.service.MachineMonitorInfoService;
import com.newegg.ec.cache.service.MachineService;
import com.newegg.ec.cache.service.SysConfigService;
import com.newegg.ec.cache.utils.CommonUtils;
import com.newegg.ec.cache.utils.LinuxMonitorUtil;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lf52 on 2018/3/20.
 */
@Controller
@RequestMapping("/rest/machineinfo/*")
public class MachineInfoController {

    @Autowired
    private MachineService machineService;

    @Autowired
    private MachineMonitorInfoService machineMonitorInfoService;

    @Autowired
    private MachineNetWorkDao machineNetWorkDao;

    @Autowired
    private BizService bizService;

    @Autowired
    private SysConfigService sysConfigService;

    @RequestMapping("/getMachineInfo")
    @ResponseBody
    public JSONObject machine(HttpServletRequest request) throws IOException {

        String data = IOUtils.toString(request.getInputStream(),"UTF-8");
        JSONObject jsonObject = (JSONObject) JSONObject.parse(data);
        String ip =  jsonObject.getString("ip");

        Machine machine = machineService.getMachineByIp(ip);
        String cmd = LinuxMonitorUtil.getMachineMonitorRealTimeCmd();
        Map<String,String> map = LinuxMonitorUtil.getMonitor(machine.getIp(), machine.getUsename(), machine.getPasswd(),cmd);
        if(map != null){
            map.put("ip",ip);
            map.put("score", machine.getScore());
            return FormatData(map);
        }
        return null;
    }

    @RequestMapping("/getMonitorInfo")
    @ResponseBody
    public JSONArray monitorInfo(HttpServletRequest request) throws IOException {

        String data = IOUtils.toString(request.getInputStream(),"UTF-8");
        JSONObject jsonObject = JSONObject.parseObject(data);
        String ip =  jsonObject.getString("ip");
        String starttimeStr =  jsonObject.getString("starttime");
        String endtimeStr =  jsonObject.getString("endtime");
        Map<String, Object > param = new HashMap();
        param.put("ip",ip);
        if(StringUtils.isNotEmpty(starttimeStr) && StringUtils.isNotEmpty(endtimeStr)){
            String starttime = CommonUtils.fomatTime(starttimeStr);
            String endtime = CommonUtils.fomatTime(endtimeStr);
            param.put("starttime",starttime);
            param.put("endtime",endtime);
        }else{
            //默认展示2hour数据
            param.put("limit",120);
        }
        List<MachineMonitorInfo> infolist = machineMonitorInfoService.getInfoList(param);
        Collections.reverse(infolist);

        return bizService.getMachineInfoJsonArray(infolist);
    }

    @RequestMapping("/getNetWorkInfo")
    @ResponseBody
    public JSONObject getNetWorkInfo(HttpServletRequest request) throws IOException {

        String data = IOUtils.toString(request.getInputStream(),"UTF-8");
        JSONObject jsonObject = (JSONObject) JSONObject.parse(data);
        String ip =  jsonObject.getString("ip");
        String starttimeStr =  jsonObject.getString("starttime");
        String endtimeStr =  jsonObject.getString("endtime");

        Map<String, Object > param = new HashMap();
        param.put("ip",ip);
        if(StringUtils.isNotEmpty(starttimeStr) && StringUtils.isNotEmpty(endtimeStr)){
            String starttime = CommonUtils.fomatTime(starttimeStr);
            String endtime = CommonUtils.fomatTime(endtimeStr);
            param.put("starttime",starttime);
            param.put("endtime",endtime);
        }else{
            //默认展示2hour数据
            param.put("limit",120);
        }
        List<MachineNetWorkInfo> infolist = machineNetWorkDao.getInfoList(param);
        Collections.reverse(infolist);

        return bizService.getNetWorkInfoJsonObject(infolist);
    }

    @RequestMapping("/getCommamdInfo")
    @ResponseBody
    public JSONObject getCommamdInfo(HttpServletRequest request) throws IOException {

        String data = IOUtils.toString(request.getInputStream(),"UTF-8");
        JSONObject jsonObject = (JSONObject) JSONObject.parse(data);
        String ip =  jsonObject.getString("ip");
        String command =  jsonObject.getString("cmd");
        JSONObject obj = new JSONObject();
        SystemConfig conf = sysConfigService.getConfig(Constant.SYSSHELLCMDUNABLE);
        String[] cmdArr = conf.getConf_value().split(",");
        for (int i = 0; i < cmdArr.length; i++) {
            if(command.startsWith(cmdArr[i])){
                obj.put("data","UnSupport Command");
                return obj;
            }
        }

        Machine machine = machineService.getMachineByIp(ip);
        String cmd = LinuxMonitorUtil.getCmd(FormatCmd(command));
        String str = LinuxMonitorUtil.getShellResult(machine.getIp(), machine.getUsename(), machine.getPasswd(), cmd);
        StringBuilder sb = new StringBuilder();
        if(str != null){
            if(str.contains("| ")){
                String[] arr = str.split("\\|");
                for(int i = 0 ; i < arr.length ; i++ ){
                    sb.append(arr[i] + "\n");
                }
                obj.put("data",sb.toString());
            }else{
                obj.put("data",str);
            }

        }else{
            obj.put("data","Unknown Command");
        }

        return obj;
    }

    private String FormatCmd(String cmd) {
        if(cmd.contains("top")){
            cmd = "`top -b | head -n 12 | awk 'BEGIN{count=0} {print $0 \"|\"}'`";
        }else if(cmd.contains("free")){
            cmd =  "`free -m| awk 'BEGIN{count=0} {print $0 \"|\"}'`";
        }else{
            cmd = "`" + cmd + "`";
        }
        return cmd;
    }

    private JSONObject FormatData(Map<String, String> map) {
        JSONObject obj = new JSONObject();
            obj.put("ip",map.get("ip"));
            obj.put("score",map.get("score"));
            obj.put("osVersion",map.get("osVersion"));
            obj.put("cpuModel",map.get("cpuModel"));
            obj.put("processor", map.get("processor") + "% us");
            obj.put("load_average", map.get("load_average").replaceAll(",", "/"));
            String swap = map.get("swap_use")+"/"+map.get("swap_total");
            obj.put("swap", swap);
            String memory = map.get("memory_use")+"/"+map.get("memory_total");
            obj.put("memory", memory);
            obj.put("netstat",map.get("netstat"));
            obj.put("ps_num",map.get("ps_num"));
            obj.put("thread_num",map.get("thread_num"));
        return obj;
    }

}
