package com.newegg.ec.cache.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.newegg.ec.cache.model.constant.Constant;
import com.newegg.ec.cache.model.entity.Machine;
import com.newegg.ec.cache.model.entity.MachineMonitorInfo;
import com.newegg.ec.cache.model.entity.MachineNetWorkInfo;
import com.newegg.ec.cache.model.entity.SystemConfig;
import com.newegg.ec.cache.service.BizService;
import com.newegg.ec.cache.service.MachineService;
import com.newegg.ec.cache.service.SysConfigService;
import com.newegg.ec.cache.utils.LinuxMonitorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by lf52 on 2018/3/21.
 */
@Service
public class BizServiceImpl implements BizService{

    public static DecimalFormat  df = new DecimalFormat("######0.00");

    @Autowired
    private SysConfigService sysConfigService;

    @Autowired
    private MachineService machineService;

    @Override
    public JSONArray getMachineInfoJsonArray(List<MachineMonitorInfo> infolist) {

        JSONArray array = new JSONArray();
        List<String> timeSpan = new LinkedList<>();
        infolist.forEach((each -> timeSpan.add(each.getUpdateTime())));
        JSONArray timeSpans = new JSONArray();
        timeSpan.forEach((time) -> {
            timeSpans.add(time);
        });

        JSONObject cpuInfo = new JSONObject();
        cpuInfo.put("timeSpan",timeSpans);
        List<String> cpuCountList = new LinkedList<>();
        for(MachineMonitorInfo record:infolist){
            String info = record.getCpuInfo();
            cpuCountList.add(info);
        }
        JSONObject cpuJson = new JSONObject();
        cpuJson.put("name","cpu_used");
        cpuJson.put("data", cpuCountList);
        JSONArray cpudetails = new JSONArray();
        cpudetails.add(cpuJson);
        cpuInfo.put("detail", cpudetails);
        array.add(cpuInfo);

        JSONObject memory = new JSONObject();
        memory.put("timeSpan",timeSpans);
        List<String> memoryusedCountList = new LinkedList<>();
        List<String> memorytotalCountList = new LinkedList<>();
        List<String> swapusedCountList = new LinkedList<>();
        List<String> swaptotalCountList = new LinkedList<>();
        for(MachineMonitorInfo record:infolist){
            String[] memoryinfo = record.getMemory().split("/");
            String[] swapinfo = record.getSwap().split("/");
            memoryusedCountList.add(memoryinfo[0]);
            memorytotalCountList.add(memoryinfo[1]);
            swapusedCountList.add(df.format(Double.parseDouble(swapinfo[0])/1000));
            swaptotalCountList.add(df.format(Double.parseDouble(swapinfo[1])/1000));
        }
        JSONObject memoryusedJson = new JSONObject();
        JSONObject memorytotalJson = new JSONObject();
        JSONObject swapusedJson = new JSONObject();
        JSONObject swaptotalJson = new JSONObject();
        memoryusedJson.put("name","memory_used");
        memoryusedJson.put("data", memoryusedCountList);
        memorytotalJson.put("name","memory_total");
        memorytotalJson.put("data", memorytotalCountList);
        swapusedJson.put("name","swap_used");
        swapusedJson.put("data", swapusedCountList);
        swaptotalJson.put("name","swap_total");
        swaptotalJson.put("data", swaptotalCountList);
        JSONArray memorydetails = new JSONArray();
        memorydetails.add(memoryusedJson);
        memorydetails.add(memorytotalJson);
        memorydetails.add(swapusedJson);
        memorydetails.add(swaptotalJson);
        memory.put("detail", memorydetails);
        array.add(memory);

        JSONObject loadAverage = new JSONObject();
        loadAverage.put("timeSpan",timeSpans);
        List<String> load1CountList = new LinkedList<>();
        List<String> load2CountList = new LinkedList<>();
        List<String> load3CountList = new LinkedList<>();
        for(MachineMonitorInfo record:infolist){
            String[] loadaverage = record.getLoadAverage().split("/");
            load1CountList.add(loadaverage[0]);
            load2CountList.add(loadaverage[1]);
            load3CountList.add(loadaverage[2]);
        }
        JSONObject load1Json = new JSONObject();
        JSONObject load2Json = new JSONObject();
        JSONObject load3Json = new JSONObject();
        load1Json.put("name","last_1min_load");
        load1Json.put("data", load1CountList);
        load2Json.put("name","last_5min_load");
        load2Json.put("data", load2CountList);
        load3Json.put("name","last_15min_load");
        load3Json.put("data", load3CountList);
        JSONArray loaddetails = new JSONArray();
        loaddetails.add(load1Json);
        loaddetails.add(load2Json);
        loaddetails.add(load3Json);
        loadAverage.put("detail", loaddetails);
        array.add(loadAverage);

        return array;
    }

    @Override
    public JSONObject getNetWorkInfoJsonObject(List<MachineNetWorkInfo> infolist) {
        List<String> timeSpan = new LinkedList<>();
        infolist.forEach((each -> timeSpan.add(each.getUpdateTime())));
        JSONArray timeSpans = new JSONArray();
        timeSpan.forEach((time) -> {
            timeSpans.add(time);
        });
        JSONObject network = new JSONObject();
        network.put("timeSpan",timeSpans);

        List<String> rxpckCountList = new LinkedList<>();
        List<String> txpckCountList = new LinkedList<>();
        List<String> rxbytCountList = new LinkedList<>();
        List<String> txbytCountList = new LinkedList<>();
        List<String> rxcmpCountList = new LinkedList<>();
        List<String> txcmpCountList = new LinkedList<>();
        List<String> rxmsctCountList = new LinkedList<>();
        for(MachineNetWorkInfo record:infolist){
            rxpckCountList.add(df.format(Double.parseDouble(record.getRxpck())/1000));
            txpckCountList.add(df.format(Double.parseDouble(record.getTxpck())/1000));
            rxbytCountList.add(df.format(Double.parseDouble(record.getRxbyt())/1000));
            txbytCountList.add(df.format(Double.parseDouble(record.getTxbyt())/1000));
            rxcmpCountList.add(df.format(Double.parseDouble(record.getRxcmp())/1000));
            txcmpCountList.add(df.format(Double.parseDouble(record.getTxcmp())/1000));
            rxmsctCountList.add(df.format(Double.parseDouble(record.getRxmcst())/1000));
        }
        JSONObject rxpckJson = new JSONObject();
        JSONObject txpckJson = new JSONObject();
        JSONObject rxbytJson = new JSONObject();
        JSONObject txbytJson = new JSONObject();
        JSONObject rxcmpJson = new JSONObject();
        JSONObject txcmpJson = new JSONObject();
        JSONObject rxmsctJson = new JSONObject();
        rxpckJson.put("name","rxpck(接收数据包:mb/s)");
        rxpckJson.put("data", rxpckCountList);
        txpckJson.put("name","txpck(发送数据包:mb/s)");
        txpckJson.put("data", txpckCountList);
        rxbytJson.put("name","rxbyt(接收字节数:mb/s)");
        rxbytJson.put("data", rxbytCountList);
        txbytJson.put("name","txbyt(发送字节数:mb/s)");
        txbytJson.put("data", txbytCountList);
        rxcmpJson.put("name","rxcmp(接收压缩数据包:mb/s)");
        rxcmpJson.put("data", rxcmpCountList);
        txcmpJson.put("name","txcmp(发送压缩数据包:mb/s)");
        txcmpJson.put("data", txcmpCountList);
        rxmsctJson.put("name","rxmsct接收的多播数据包:mb/s)");
        rxmsctJson.put("data", rxmsctCountList);
        JSONArray networkdetails = new JSONArray();
        networkdetails.add(rxpckJson);
        networkdetails.add(txpckJson);
        networkdetails.add(rxbytJson);
        networkdetails.add(txbytJson);
        networkdetails.add(rxcmpJson);
        networkdetails.add(txcmpJson);
        networkdetails.add(rxmsctJson);
        network.put("detail", networkdetails);
        return network;
    }

    @Override
    public JSONObject MachineAnalysis(String ip) {

        List<SystemConfig> confList = sysConfigService.getConfigList(new HashMap<>());
        Map<String,String> confMap  = new HashMap();
        confList.forEach(conf ->{
            confMap.put(conf.getConf_key(),conf.getConf_value());
        });

        Machine machine = machineService.getMachineByIp(ip);
        String cmd = LinuxMonitorUtil.getMachineMonitorRealTimeCmd();
        Map<String,String> machineInfoMap = LinuxMonitorUtil.getMonitor(machine.getIp(), machine.getUsename(), machine.getPasswd(),cmd);
        //统计cpu ，memory ，swap ，loadaverage 四项评分
        double score = Analysis(confMap,machineInfoMap);
        machine.setScore(getLevel(score));
        machineService.update(machine);
        JSONObject obj = new JSONObject();
        obj.put("level",getLevel(score));
        return obj;
    }

    private double Analysis(Map<String, String> confMap, Map<String, String> machineInfoMap) {

        float cpu_used_limit = Float.parseFloat(confMap.get(Constant.MACHINECPUUSEDLIMIT));
        float memory_used_limit = Float.parseFloat(confMap.get(Constant.MACHINEMEMORYUSEDLIMIT));
        float swap_used_limit = Float.parseFloat(confMap.get(Constant.MACHINESWAPUSEDLIMIT));
        float loadaverage_used_limit = Float.parseFloat(confMap.get(Constant.MACHINELOADAVERAGEUSEDLIMIT));

        String cpuPer =  machineInfoMap.get("processor");
        int cpu_score = getScore(cpu_used_limit, cpuPer);
        String memoryPer = (Float.parseFloat(machineInfoMap.get("memory_use")) / Float.parseFloat(machineInfoMap.get("memory_total"))*100) + "";
        int memory_score = getScore(memory_used_limit, memoryPer);
        String swapPer = (Float.parseFloat(machineInfoMap.get("swap_use")) / Float.parseFloat(machineInfoMap.get("swap_total"))*100) + "";
        int swap_score = getScore(swap_used_limit, swapPer);
        String[] arr = machineInfoMap.get("load_average").split(",");
        int load_score = getScore(loadaverage_used_limit,arr[arr.length-1]);
        return cpu_score * 0.3 + memory_score * 0.2  + swap_score * 0.3 + load_score * 0.2;

    }

    private int getScore(float limit, String current) {
        float score = Float.parseFloat(current);
        if(score > limit){
            return 0;
        }else if(score < limit && score > limit/2 ){
            return 5;
        }else if(score < limit/2 && score > limit/4 ){
            return 7;
        }else if(score < limit/4 && score > limit/10 ){
            return 8;
        }else{
            return 10;
        }
    }

    private String getLevel(double score) {
      if(score < 6){
          return "C";
      }else if(score >= 6 && score < 8){
          return "B";
      }else{
          return "A";
      }
    }



}
