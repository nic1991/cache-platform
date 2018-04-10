package com.newegg.ec.cache.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.newegg.ec.cache.model.entity.MachineMonitorInfo;
import com.newegg.ec.cache.model.entity.MachineNetWorkInfo;

import java.util.List;

/**
 * Created by lf52 on 2018/3/21.
 */
public interface  BizService {

    public JSONArray getMachineInfoJsonArray(List<MachineMonitorInfo> list);

    public JSONObject getNetWorkInfoJsonObject(List<MachineNetWorkInfo> infolist);

    public JSONObject MachineAnalysis(String ip);
}
