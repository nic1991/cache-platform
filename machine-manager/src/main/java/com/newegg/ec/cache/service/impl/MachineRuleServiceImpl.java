package com.newegg.ec.cache.service.impl;

import com.newegg.ec.cache.dao.MachineRuleDao;
import com.newegg.ec.cache.model.entity.MachineRule;
import com.newegg.ec.cache.service.MachineRuleService;
import com.newegg.ec.cache.utils.CommonUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jay.H.Zou
 * @date 2018/3/30
 */
@Service
public class MachineRuleServiceImpl implements MachineRuleService {

    private static final Logger logger = Logger.getLogger(MachineRuleServiceImpl.class);

    @Autowired
    private MachineRuleDao machineRuleDao;

    @Override
    public List<MachineRule> getMachineRuleList(String ip) {
        List<MachineRule> machineRuleList = null;
        if (StringUtils.isNotBlank(ip)){
            List<MachineRule> machineRules = machineRuleDao.selectMachineRuleList(ip);
            if (machineRules.size() > 0){
                machineRuleList = machineRules;
            }
        }
        return machineRuleList;
    }

    @Override
    public MachineRule getMachineRuleById(String id) {
        return machineRuleDao.selectMachineRuleById(id);
    }

    @Override
    public MachineRule getMachineRuleByFormula(Map<String, String> params) {
        return machineRuleDao.selectMachineRuleByFormula(params);
    }

    @Override
    public int updateMachineRule(MachineRule machineRule) {
        int row = 0;
        if (machineRule != null && StringUtils.isNotBlank(machineRule.getIp())){
            machineRule.setUpdateTime(CommonUtils.getFormatTime());
            row = machineRuleDao.updateMachineRule(machineRule);
        }
        return row;
    }

    @Override
    public int addMachineRule(MachineRule machineRule) {
        int row = 0;
        if (machineRule != null && StringUtils.isNotBlank(machineRule.getIp())){
            machineRule.setId(CommonUtils.getUuid());
            machineRule.setUpdateTime(CommonUtils.getFormatTime());
            row = machineRuleDao.addMachineRule(machineRule);
        }
        return row;
    }

    @Override
    public int deleteMachineRuleById(String id) {
        return machineRuleDao.deleteRuleById(id);
    }
}
