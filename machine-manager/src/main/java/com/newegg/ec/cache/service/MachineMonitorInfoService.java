package com.newegg.ec.cache.service;

import com.newegg.ec.cache.model.entity.MachineMonitorInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by lf52 on 2018/3/16.
 */
public interface MachineMonitorInfoService {

    public List<MachineMonitorInfo> getInfoList(Map<String, Object> param);

    public MachineMonitorInfo getNewestInfo(String ip);

    public boolean Add(MachineMonitorInfo machineMonitorInfo);

    public boolean Delete();
}
