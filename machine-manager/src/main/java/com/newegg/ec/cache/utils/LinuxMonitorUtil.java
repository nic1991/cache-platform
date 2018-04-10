package com.newegg.ec.cache.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class LinuxMonitorUtil {


    private static Logger logger=Logger.getLogger(LinuxMonitorUtil.class);

    public static Map<String, String> getMonitor(String ip, String username, String pass,String cmd){
        RemoteShellUtil rms = new RemoteShellUtil(ip, username, pass);
        String result = rms.exec( cmd );
        if(StringUtils.isBlank( result )){
            return null;
        }
        Map map = formatResult( result );
        return map;
    }

    public static String getShellResult(String ip, String username, String pass,String cmd){
        RemoteShellUtil rms = new RemoteShellUtil(ip, username, pass);
        String result = rms.exec(cmd);
        if(StringUtils.isBlank( result )){
            return null;
        }
        return result;
    }


    public static String  formatMonitorCmd(String field, String cmd){
        return "res=${res}\"" + field + "'" + cmd + "\\n" +"\"\n";
    }

    public static String formatMonitorCmd( String cmd){
        return "res=${res}\""  + cmd + "\\n" +"\"\n";
    }

    private static Map formatResult(String result){
        Map<String,String> map = new HashMap();
        String[] lines = result.split("\n");
        for (String line : lines) {
            if( StringUtils.isBlank( line ) ){
                continue;
            }
            String[] tmp = line.split("'");
            if( "top".equals(tmp[0]) ){
                String arr[] = tmp[1].split("\\|");
                map.put("top_1", arr[0].trim());
                map.put("top_2", arr[1].trim());
                map.put("top_3", arr[2].trim());
            }else{
                map.put(tmp[0].trim(), tmp[1].trim());
            }
        }
        return map;
    }

    public static String getMachineMonitorScheduleCmd(){
        String cmd = "export TERM=linux;res=\"\"\n" +
                //cpu使用率
                LinuxMonitorUtil.formatMonitorCmd("processor", "`sar -u 1 1 |grep 'Average'|awk '{print $3}'`")+
                //网络连接数
                LinuxMonitorUtil.formatMonitorCmd("netstat", "`netstat -nat | wc -l`")+
                //loadaverage
                LinuxMonitorUtil.formatMonitorCmd("load_average", "`uptime |awk '{print $(NF-2) $(NF-1) $NF}'`")+
                //内存使用相关
                LinuxMonitorUtil.formatMonitorCmd("memory_total", "`free -g | grep Mem | awk '{print $2}'`")+
                LinuxMonitorUtil.formatMonitorCmd("memory_use", "`free -g | grep Mem | awk '{print $3}'`")+
                LinuxMonitorUtil.formatMonitorCmd("memory_free", "`free -g | grep Mem | awk '{print $4}'`")+
                LinuxMonitorUtil.formatMonitorCmd("memory_available", "`free -g | grep Mem | awk '{print $7}'`")+
                //swap相关
                LinuxMonitorUtil.formatMonitorCmd("swap_total", "`free -m | grep Swap | awk '{print $2}'`")+
                LinuxMonitorUtil.formatMonitorCmd("swap_use", "`free -m | grep Swap | awk '{print $3}'`")+
                LinuxMonitorUtil.formatMonitorCmd("swap_free", "`free -m | grep Swap | awk '{print $4}'`")+
                //进程数，线程数
                LinuxMonitorUtil.formatMonitorCmd("ps_num", "`ps -ea | wc -l`")+
                LinuxMonitorUtil.formatMonitorCmd("thread_num", "`ps -eo nlwp | tail -n +2 | awk '{num_threads += $1} END {print num_threads}'`")+
                LinuxMonitorUtil.formatMonitorCmd("disk_total", "`df -lm | grep /dev/ | awk 'BEGIN{count=0} {printf grep $2 \"|\"}'`")+
                LinuxMonitorUtil.formatMonitorCmd("disk_use", "`df -lm | grep /dev/ | awk 'BEGIN{count=0} {printf grep $3 \"|\"}'`")+
                "echo -e $res";
        return cmd;
    }

    public static String getMachineMonitorRealTimeCmd(){
        String cmd = "export TERM=linux;res=\"\"\n" +
                //cpu型号
                LinuxMonitorUtil.formatMonitorCmd("cpuModel", "`cat /proc/cpuinfo | grep name | cut -f2 -d: | uniq -c`")+
                //os版本
                LinuxMonitorUtil.formatMonitorCmd("osVersion", "`cat /etc/redhat-release`")+
                //cpu使用率
                LinuxMonitorUtil.formatMonitorCmd("processor", "`sar -u 1 1 |grep 'Average'|awk '{print $3}'`")+
                //网络连接数
                LinuxMonitorUtil.formatMonitorCmd("netstat", "`netstat -nat | wc -l`")+
                //loadaverage
                LinuxMonitorUtil.formatMonitorCmd("load_average", "`uptime |awk '{print $(NF-2) $(NF-1) $NF}'`")+
                //内存使用相关
                LinuxMonitorUtil.formatMonitorCmd("memory_total", "`free -g | grep Mem | awk '{print $2}'`")+
                LinuxMonitorUtil.formatMonitorCmd("memory_use", "`free -g | grep Mem | awk '{print $3}'`")+
                LinuxMonitorUtil.formatMonitorCmd("memory_free", "`free -g | grep Mem | awk '{print $4}'`")+
                LinuxMonitorUtil.formatMonitorCmd("memory_available", "`free -g | grep Mem | awk '{print $7}'`")+
                //swap相关
                LinuxMonitorUtil.formatMonitorCmd("swap_total", "`free -m | grep Swap | awk '{print $2}'`")+
                LinuxMonitorUtil.formatMonitorCmd("swap_use", "`free -m | grep Swap | awk '{print $3}'`")+
                LinuxMonitorUtil.formatMonitorCmd("swap_free", "`free -m | grep Swap | awk '{print $4}'`")+
                //进程数，线程数
                LinuxMonitorUtil.formatMonitorCmd("ps_num", "`ps -ea | wc -l`")+
                LinuxMonitorUtil.formatMonitorCmd("thread_num", "`ps -eo nlwp | tail -n +2 | awk '{num_threads += $1} END {print num_threads}'`")+
                //cpu使用前3进程
                LinuxMonitorUtil.formatMonitorCmd("top", "`top -b | head -n 10 | tail -n 3 | awk 'BEGIN{count=0} {print $0 \"|\"}'`")+
                "echo -e $res";
        return cmd;
    }

    public static String getNetWorkNameCmd(){
        String cmd = "export TERM=linux;res=\"\"\n" +
                //获取当前机器所有网卡name
                LinuxMonitorUtil.formatMonitorCmd("networkname", "`/usr/sbin/ifconfig -a | grep 'mtu' | awk -F ':' '{printf $1 \"|\"}'`")+
                "echo -e $res";
        return cmd;
    }

    public static String getNetWorkCmd(String networkname){
        //获取机器一个网卡的流量 sar -n DEV 1 1 | grep Average |  grep lo | awk '{print $3 "|" $4 $5 $6 $7 $8 $9}'
        String cmd = "export TERM=linux;res=\"\"\n" +
                //获取当前机器所有网卡name
                LinuxMonitorUtil.formatMonitorCmd("network", "`sar -n DEV 1 1 | grep Average |  grep '"+ networkname +"' | awk '{print $3 \"|\" $4 \"|\" $5 \"|\" $6 \"|\" $7 \"|\" $8 \"|\" $9 \"|\"}'`")+
                "echo -e $res";
        return cmd;
    }

    public static String getCheckInfoCmd(){
        String cmd = "export TERM=linux;res=\"\"\n" +
                LinuxMonitorUtil.formatMonitorCmd("memory_total", "`free -g | grep Mem | awk '{print $2}'`")+
                LinuxMonitorUtil.formatMonitorCmd("core", "`cat /proc/cpuinfo| grep \"processor\"| wc -l`")+
                "echo -e $res";
        return cmd;
    }


    public static String getCmd(String command){
        String cmd = "export TERM=linux;res=\"\"\n" +
                LinuxMonitorUtil.formatMonitorCmd(command)+
                "echo -e $res";
        return cmd;
    }

}
