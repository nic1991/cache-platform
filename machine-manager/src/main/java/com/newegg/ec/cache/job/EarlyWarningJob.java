package com.newegg.ec.cache.job;

import com.newegg.ec.cache.dao.*;
import com.newegg.ec.cache.model.entity.Machine;
import com.newegg.ec.cache.model.entity.MachineMonitorInfo;
import com.newegg.ec.cache.model.entity.MachineRule;
import com.newegg.ec.cache.model.entity.MachineWarning;
import com.newegg.ec.cache.service.MachineWarningService;
import com.newegg.ec.cache.utils.CommonUtils;
import com.newegg.ec.cache.utils.LinuxMonitorUtil;
import com.newegg.ec.cache.utils.MathExpressionCalculateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Jay.H.Zou
 * @date 2018/3/29
 */
@Component
public class EarlyWarningJob implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private MachineDao machineDao;

    @Autowired
    private MachineMonitorInfoDao machineMonitorInfoDao;

    @Autowired
    private MachineNetWorkDao machineNetWorkDao;

    @Autowired
    private MachineWarningDao machineWarningDao;

    @Autowired
    private MachineWarningService machineWarningService;

    @Autowired
    private MachineRuleDao machineRuleDao;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
    }

    @Scheduled(fixedDelay = 61 * 1000)
    //@Transactional(rollbackFor = Exception.class)
    public void run(){
        List<Machine> machineList = machineDao.getAllMachine();
        for (Machine machine : machineList){
            MachineMonitorInfo newestInfo = machineMonitorInfoDao.getNewestInfo(machine.getIp());
            Map<String, Object> map = formatMachineInfo(newestInfo);
            List<MachineRule> machineRuleList = machineRuleDao.selectMachineRuleList(machine.getIp());
            for (MachineRule machineRule : machineRuleList){
                String formula = machineRule.getFormula();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date();
                List<String> idList = machineWarningDao.selectDuplicateWarning(machine.getIp(), formula, format.format(date.getTime() - 15 * 60 * 1000));
                if(idList != null && idList.size() > 0){
                    continue;
                }
                try {
                    boolean isWarning;
                    try {
                        isWarning = Boolean.parseBoolean(String.valueOf(MathExpressionCalculateUtil.calculate(formula, map)));
                    } catch (Exception e){
                        System.out.println(machineRule.getIp() + "\t" + machineRule.getFormula() + "\t规则出错");
                       // e.printStackTrace();
                        continue;
                    }
                    if (isWarning){
                        // 进行预警操作，写数据库，发邮件，发短信
                        MachineWarning machineWarning = new MachineWarning();
                        machineWarning.setId(CommonUtils.getUuid());
                        machineWarning.setIp(machineRule.getIp());
                        machineWarning.setLimitName(machineRule.getLimitName());
                        machineWarning.setFormula(machineRule.getFormula());
                        machineWarning.setGrade("1");
                        machineWarning.setDescription(machineRule.getDescription());
                        String dataStr = MathExpressionCalculateUtil.getRuleDataStr(formula, map);
                        machineWarning.setData(dataStr);
                        machineWarning.setUpdateTime(CommonUtils.getFormatTime());
                        System.out.println(machineWarning);
                        machineWarningService.addMachineWarning(machineWarning);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static Map<String, Object> formatMachineInfo(MachineMonitorInfo machineMonitorInfo){
        Map<String, Object> map = new HashMap<>();
        map.put("cup_info", machineMonitorInfo.getCpuInfo());
        String[] memory = machineMonitorInfo.getMemory().split("/");
        map.put("memory_use", memory[0]);
        map.put("memory_total", memory[1]);
        String[] disk = machineMonitorInfo.getDisk().split("/");
        map.put("disk_use", disk[0]);
        map.put("disk_total", disk[1]);
        String[] swap = machineMonitorInfo.getSwap().split("/");
        map.put("swap_use", swap[0]);
        map.put("swap_total", swap[1]);
        map.put("connect_num", machineMonitorInfo.getConnectNum());
        map.put("ps_num", machineMonitorInfo.getPsNum());
        map.put("thread_num", machineMonitorInfo.getTreadNum());
        return map;
    }


}
