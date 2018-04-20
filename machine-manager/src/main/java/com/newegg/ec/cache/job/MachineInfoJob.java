package com.newegg.ec.cache.job;

import com.newegg.ec.cache.dao.*;
import com.newegg.ec.cache.model.entity.*;
import com.newegg.ec.cache.utils.CommonUtils;
import com.newegg.ec.cache.utils.LinuxMonitorUtil;
import com.newegg.ec.cache.utils.MathExpressionCalculateUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Jay.H.Zou
 * @date 2018/3/31
 */
@Component
public class MachineInfoJob{

    private static final Logger logger = Logger.getLogger(MachineInfoJob.class);

    @Autowired
    private MachineDao machineDao;

    @Autowired
    private MachineMonitorInfoDao machineMonitorInfoDao;

    @Autowired
    private MachineNetWorkDao machineNetWorkDao;

    @Autowired
    private MachineWarningDao machineWarningDao;

    @Autowired
    private MachineRuleDao machineRuleDao;

    public static ExecutorService pool = Executors.newFixedThreadPool(50);
    public static DecimalFormat df;

    @Scheduled(fixedDelay = 61 * 1000)
    public void run() {
        Map<String, Object > param = new HashMap();
        List<Machine> list = machineDao.getAllMachine();
        String monitorCmd = LinuxMonitorUtil.getMachineMonitorScheduleCmd();
        String networkCmd = LinuxMonitorUtil.getNetWorkNameCmd();
        for(Machine machine : list){
            pool.execute(new MachineInfoTask(machine, monitorCmd, networkCmd));
            df = new DecimalFormat("######0.00");
        }
    }

    class MachineInfoTask implements Runnable {
        private Machine machine;
        private String monitorCmd;
        private String networkCmd;

        public MachineInfoTask(Machine machine,String monitorCmd, String networkCmd){
            this.machine =  machine;
            this.monitorCmd = monitorCmd;
            this.networkCmd = networkCmd;
        }

        @Override
        public void run() {
            Map<String,String> monitorMap = LinuxMonitorUtil.getMonitor(this.machine.getIp(), this.machine.getUsename(), this.machine.getPasswd(), this.monitorCmd);
            Map<String,String> networkMap = LinuxMonitorUtil.getMonitor(this.machine.getIp(), this.machine.getUsename(), this.machine.getPasswd(),this.networkCmd);
            monitorMap.put("ip", machine.getIp());

            // monitor info
            MachineMonitorInfo monitorInfo = formatData(monitorMap);
            try {
                machineMonitorInfoDao.add(monitorInfo);
            } catch (DuplicateKeyException e){
                logger.error("主键重复");
            } catch (Exception e){
                logger.error("不明错误");
            }

            System.err.println(monitorInfo);

            // network info
            if (StringUtils.isNotBlank(networkMap.get("networkname"))){
                String[] arr = networkMap.get("networkname").split("\\|");
                List<Map<String, String>> list = new ArrayList();
                for(int i = 0 ; i < arr.length ; i++){
                    String cmd1 = LinuxMonitorUtil.getNetWorkCmd(arr[i]);
                    Map<String,String> map1 = LinuxMonitorUtil.getMonitor(machine.getIp(), machine.getUsename(), machine.getPasswd(),cmd1);
                    map1.put("ip",machine.getIp());
                    list.add(map1);
                }
                //计算结果
                Map<String, String> result = mapCombine(list);
                MachineNetWorkInfo networkInfo = FormatData(result);
                try {
                    machineNetWorkDao.add(networkInfo);
                } catch (DuplicateKeyException e){
                    logger.error("主键重复", e.getCause());
                } catch (Exception e){
                    logger.error("不明错误", e);
                }

                System.err.println(networkInfo);
            }
        }
    }

    private MachineMonitorInfo formatData(Map<String,String> map){
        MachineMonitorInfo info = new MachineMonitorInfo();
        info.setConnectNum(Integer.parseInt(map.get("netstat")));
        info.setTreadNum(Integer.parseInt(map.get("thread_num")));
        info.setPsNum(Integer.parseInt(map.get("ps_num")));
        info.setCpuInfo(map.get("processor"));
        info.setLoadAverage(map.get("load_average").replaceAll(",", "/"));
        String swap = map.get("swap_use")+"/"+map.get("swap_total");
        info.setSwap(swap);
        String memory = map.get("memory_use")+"/"+map.get("memory_total");
        info.setMemory(memory);
        info.setIp(map.get("ip"));
        info.setLogType("Schedule");
        info.setUpdateTime(CommonUtils.getNowDate());

        String[] diskuse = map.get("disk_use").split("\\|");
        String[] disktotal = map.get("disk_total").split("\\|");

        int diskusenum = 0;
        int disktotalnum = 0;
        for(int i = 0 ; i < diskuse.length ; i++ ){
            diskusenum = diskusenum + Integer.parseInt(diskuse[i]);
            disktotalnum = disktotalnum + Integer.parseInt(disktotal[i]);
        }
        String disk = diskusenum/1000+"/"+disktotalnum/1000;
        info.setDisk(disk);
        return info;
    }

    //求和
    public Map<String, String> mapCombine(List<Map<String, String>> list) {
        Map<String, String> map = new HashMap<>();
        double rxpck = 0.00;
        double txpck = 0.00;
        double rxbyt = 0.00;
        double txbyt = 0.00;
        double rxcmp = 0.00;
        double txcmp = 0.00;
        double rxmcst = 0.00;

        if(list.size() > 0){
            for (Map<String, String> m : list) {
                String value = m.get("network");
                String[] arr = value.split("\\|");
                rxpck = rxpck + Double.parseDouble(arr[0]);
                txpck = txpck + Double.parseDouble(arr[1]);
                rxbyt = rxbyt + Double.parseDouble(arr[2]);
                txbyt = txbyt + Double.parseDouble(arr[3]);
                rxcmp = rxcmp + Double.parseDouble(arr[4]);
                txcmp = txcmp + Double.parseDouble(arr[5]);
                rxmcst = rxmcst + Double.parseDouble(arr[6]);
            }
            map.put("rxpck",df.format(rxpck)+"");
            map.put("txpck",df.format(txpck)+"");
            map.put("rxbyt",df.format(rxbyt)+"");
            map.put("txbyt",df.format(txbyt)+"");
            map.put("rxcmp",df.format(rxcmp)+"");
            map.put("txcmp",df.format(txcmp)+"");
            map.put("rxmcst",df.format(rxmcst)+"");
            map.put("ip",list.get(0).get("ip"));
        }
        return map;
    }

    private MachineNetWorkInfo FormatData(Map<String,String> map){
        MachineNetWorkInfo info = new MachineNetWorkInfo();
        info.setIp(map.get("ip"));
        info.setRxpck( map.get("rxpck") );
        info.setTxpck( map.get("txpck") );
        info.setRxbyt( map.get("rxbyt") );
        info.setTxbyt( map.get("txbyt") );
        info.setRxcmp( map.get("rxcmp") );
        info.setTxcmp( map.get("txcmp") );
        info.setRxmcst( map.get("rxmcst"));
        info.setUpdateTime(CommonUtils.getNowDate());
        return info;
    }
}
