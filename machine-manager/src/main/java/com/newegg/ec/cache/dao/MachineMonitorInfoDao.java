package com.newegg.ec.cache.dao;

import com.newegg.ec.cache.model.entity.MachineMonitorInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by lf52 on 2018/3/16.
 */
@Repository
public interface MachineMonitorInfoDao {


    public List<MachineMonitorInfo> getInfoList(@Param("param")Map<String,Object> param);

    public MachineMonitorInfo getNewestInfo(String ip);

    public void Add(MachineMonitorInfo machineMonitorInfo);

    //清空历史监控信息（只保留最近3天的）
    public void Delete();

}
