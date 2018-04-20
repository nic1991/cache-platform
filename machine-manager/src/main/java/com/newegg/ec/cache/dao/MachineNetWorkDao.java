package com.newegg.ec.cache.dao;

import com.newegg.ec.cache.model.entity.MachineNetWorkInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by lf52 on 2018/3/16.
 */
@Repository
public interface MachineNetWorkDao {

    public List<MachineNetWorkInfo> getInfoList(@Param("param") Map<String, Object> param);

    public void add(MachineNetWorkInfo machineNetWorkInfo);

    //清空历史监控信息（只保留最近3天的）
    public void delete();

}
