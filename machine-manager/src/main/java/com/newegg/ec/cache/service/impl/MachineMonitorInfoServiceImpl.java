package com.newegg.ec.cache.service.impl;

import com.newegg.ec.cache.dao.MachineMonitorInfoDao;
import com.newegg.ec.cache.model.entity.MachineMonitorInfo;
import com.newegg.ec.cache.service.MachineMonitorInfoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by lf52 on 2018/3/16.
 */
@Service
public class MachineMonitorInfoServiceImpl implements MachineMonitorInfoService{

    private static Logger logger=Logger.getLogger(MachineMonitorInfoServiceImpl.class);

    @Autowired
    private MachineMonitorInfoDao machineMonitorInfoDao;


    @Override
    public List<MachineMonitorInfo> getInfoList(Map<String, Object> param) {
        return machineMonitorInfoDao.getInfoList(param);
    }

    @Override
    public MachineMonitorInfo getNewestInfo(String ip) {
        return machineMonitorInfoDao.getNewestInfo(ip);
    }

    @Override
    public boolean add(MachineMonitorInfo machineMonitorInfo) {
        boolean flag = true;

        try{
            machineMonitorInfoDao.add(machineMonitorInfo);
        }catch (Exception e){
            flag = false;
            logger.error(e);
        }

        return flag;
    }

    @Override
    public boolean delete() {
        boolean flag = true;

        try{
            machineMonitorInfoDao.delete();
        }catch (Exception e){
            flag = false;
            logger.error(e);
        }

        return flag;
    }
}
