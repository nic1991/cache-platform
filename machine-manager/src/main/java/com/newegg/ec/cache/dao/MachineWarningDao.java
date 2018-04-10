package com.newegg.ec.cache.dao;

import com.newegg.ec.cache.model.entity.MachineWarning;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * machine warning info
 * @author Jay.H.Zou
 * @date 2018/3/29
 */
@Repository
public interface MachineWarningDao {

    int addMachineWarning(MachineWarning machineWarning);

    List<MachineWarning> selectMachineWarningByIp(String ip);

    MachineWarning selectMachineWarningById(String id);

    List<String> selectDuplicateWarning(String ip, String formula, String startTime);

    int countWarningNum(String ip);

    int checkWarningById(String id);

    int checkAllWarningByIp(String ip);

    int deleteWarningById(String id);

    void deleteAllWarningCheckedByIp(String ip);

    int batchDeleteWarning(List<String> ids);

    /**
     * TODO:定时删除
     * @return
     */
    int deleteData();
}
