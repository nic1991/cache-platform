package com.newegg.ec.cache.service;

import com.newegg.ec.cache.model.entity.MachineRule;

import java.util.List;
import java.util.Map;

/**
 * @author Jay.H.Zou
 * @date 2018/3/30
 */
public interface MachineRuleService {

    List<MachineRule> getMachineRuleList(String ip);

    MachineRule getMachineRuleById(String id);

    MachineRule getMachineRuleByFormula(Map<String, String> params);

    int updateMachineRule(MachineRule machineRule);

    int addMachineRule(MachineRule machineRule);

    int deleteMachineRuleById(String id);
}
