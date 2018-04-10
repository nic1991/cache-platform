package com.newegg.ec.cache.service.impl;

import com.newegg.ec.cache.dao.MachineDao;
import com.newegg.ec.cache.model.entity.Machine;
import com.newegg.ec.cache.service.MachineService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by lf52 on 2018/3/16.
 */
@Service
public class MachineServiceImpl implements MachineService{

    private static Logger logger=Logger.getLogger(MachineServiceImpl.class);

    @Autowired
    private MachineDao machineDao;

    @Override
    public Machine getMachineByIp(String ip) {
        return machineDao.getMachineByIp(ip);
    }

    @Override
    public List<Machine> getMachineList(Map<String, Object> param) {
        return machineDao.getMachineList(param);
    }

    @Override
    public boolean Add(Machine machine) {
        boolean flag = true;

        try{
            machineDao.Add(machine);
        }catch (Exception e){
            flag = false;
            logger.error(e);
            e.printStackTrace();
        }

        return flag;
    }

    @Override
    public boolean Update(Machine machine) {
        boolean flag = true;

        try{
            machineDao.Update(machine);
        }catch (Exception e){
            flag = false;
            logger.error(e);
        }

        return flag;
    }

    @Override
    public boolean Delete(String ip) {
        boolean flag = true;

        try{
            machineDao.Delete(ip);
        }catch (Exception e){
            flag = false;
            logger.error(e);
        }

        return flag;
    }

}
