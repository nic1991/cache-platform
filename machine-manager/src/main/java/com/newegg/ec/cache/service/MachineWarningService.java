package com.newegg.ec.cache.service;

import com.newegg.ec.cache.model.entity.MachineWarning;

import java.util.List;

/**
 * @author Jay.H.Zou
 * @date 2018/3/31
 */
public interface MachineWarningService {

    List<MachineWarning> getMachineWarningListByIp(String ip);

    int addMachineWarning(MachineWarning machineWarning);

    int checkWarning(String id);

    void checkAllWarning(String ip);

    int deleteWarningById(String id);

    void deleteAllCheckedWarningByIp(String ip);
}
