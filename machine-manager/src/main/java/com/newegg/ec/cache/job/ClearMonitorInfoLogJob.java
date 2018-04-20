package com.newegg.ec.cache.job;

import com.newegg.ec.cache.dao.MachineMonitorInfoDao;
import com.newegg.ec.cache.dao.MachineNetWorkDao;
import com.newegg.ec.cache.dao.MachineWarningDao;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by lf52 on 2018/3/15.
 */
@Component
public class ClearMonitorInfoLogJob  {

    private static Logger logger=Logger.getLogger(ClearMonitorInfoLogJob.class);

    @Autowired
    private MachineMonitorInfoDao machineMonitorInfoDao;

    @Autowired
    private MachineNetWorkDao machineNetWorkDao;

    @Autowired
    private MachineWarningDao machineWarningDao;

    @Scheduled(cron="0 0 1 * * ? ")
    public void run() {
        logger.info("Start Clear MonitorInfoLog .");
        //删除3天前的日志
        machineMonitorInfoDao.delete();
        machineNetWorkDao.delete();
        logger.info("Clear MonitorInfoLog Success .");
        machineWarningDao.deleteData();
        logger.info("Clear Machine Warning Success .");
    }
}