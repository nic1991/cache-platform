package com.newegg.ec.cache.dao;

import com.newegg.ec.cache.model.entity.Machine;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by lf52 on 2018/3/16.
 */
@Repository
public interface MachineDao {

    Machine getMachineByIp(String ip);

    List<Machine> getMachineList(@Param("param") Map<String, Object> param);

    List<Machine> getAllMachine();

    void add(Machine machine);

    void update(Machine machine);

    void delete(String ip);

    int returnWarningNumToZero(String ip);

    int setWarningNum(String ip, Integer warningNum);

}
