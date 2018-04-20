package com.newegg.ec.cache.service.impl;

import com.newegg.ec.cache.dao.SysConfigDao;
import com.newegg.ec.cache.model.entity.SystemConfig;
import com.newegg.ec.cache.service.SysConfigService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by lf52 on 2018/3/22.
 */
@Service
public class SysConfigServiceImp implements SysConfigService {

    private static Logger logger=Logger.getLogger(SysConfigServiceImp.class);

    @Autowired
    private SysConfigDao sysConfigDao;


    @Override
    public List<SystemConfig> getConfigList(Map<String, Object> param) {
        return sysConfigDao.getConfigList(param);
    }

    @Override
    public SystemConfig getConfig(String key) {
        return sysConfigDao.getConfig(key);
    }

    @Override
    public boolean add(SystemConfig config) {

        boolean flag = true;

        try{
            sysConfigDao.Add(config);
        }catch (Exception e){
            flag = false;
            logger.error(e);
        }

        return flag;
    }

    @Override
    public boolean update(SystemConfig config) {
        boolean flag = true;

        try{
            sysConfigDao.Update(config);
        }catch (Exception e){
            flag = false;
            logger.error(e);
        }

        return flag;
    }

    @Override
    public boolean delete(String key) {
        boolean flag = true;

        try{
            sysConfigDao.Delete(key);
        }catch (Exception e){
            flag = false;
            logger.error(e);
        }

        return flag;
    }
}
