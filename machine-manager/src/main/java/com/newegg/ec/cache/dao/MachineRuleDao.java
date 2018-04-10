package com.newegg.ec.cache.dao;

import com.newegg.ec.cache.model.entity.MachineRule;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


/**
 * @author Jay.H.Zou
 * @date 2018/3/29
 */
@Repository
public interface MachineRuleDao {

    List<MachineRule> selectMachineRuleList(String ip);

    MachineRule selectMachineRuleById(String id);

    MachineRule selectMachineRuleByFormula(@Param("params") Map<String, String> params);

    int addMachineRule(MachineRule machineRule);

    int updateMachineRule(MachineRule machineRule);

    int deleteRuleById(String id);
}
