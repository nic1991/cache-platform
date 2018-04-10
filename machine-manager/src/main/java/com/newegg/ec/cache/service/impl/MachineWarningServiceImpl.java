package com.newegg.ec.cache.service.impl;

import com.newegg.ec.cache.dao.MachineDao;
import com.newegg.ec.cache.dao.MachineWarningDao;
import com.newegg.ec.cache.model.entity.MachineWarning;
import com.newegg.ec.cache.service.MachineWarningService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Jay.H.Zou
 * @date 2018/3/31
 */
@Service
public class MachineWarningServiceImpl implements MachineWarningService {
    private static final Logger logger = Logger.getLogger(MachineWarningServiceImpl.class);

    @Autowired
    private MachineWarningDao machineWarningDao;

    @Autowired
    private MachineDao machineDao;

    @Override
    public List<MachineWarning> getMachineWarningListByIp(String ip) {
        List<MachineWarning> machineWarningList = null;
        if (StringUtils.isNotBlank(ip)){
            machineWarningList = machineWarningDao.selectMachineWarningByIp(ip);
        }
        return machineWarningList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addMachineWarning(MachineWarning machineWarning) {
        int row = machineWarningDao.addMachineWarning(machineWarning);
        if (row == 1){
            int num = machineWarningDao.countWarningNum(machineWarning.getIp());
            machineDao.setWarningNum(machineWarning.getIp(), num);
        }
        return row;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int checkWarning(String id) {
        final MachineWarning machineWarning = machineWarningDao.selectMachineWarningById(id);
        int result = 0;
        if (machineWarning != null){
            result = machineWarningDao.checkWarningById(id);
            int num = machineWarningDao.countWarningNum(machineWarning.getIp());
            machineDao.setWarningNum(machineWarning.getIp(), num);
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void checkAllWarning(String ip) {
        int row = machineWarningDao.checkAllWarningByIp(ip);
        machineDao.returnWarningNumToZero(ip);
    }

    @Override
    public int deleteWarningById(String id) {
        return machineWarningDao.deleteWarningById(id);
    }

    @Override
    public void deleteAllCheckedWarningByIp(String ip) {
        machineWarningDao.deleteAllWarningCheckedByIp(ip);
    }
}
