package com.newegg.ec.cache.app.model;

/**
 * Created by lf52 on 2018/4/27.
 */
public class RedisSlowLog {

    //慢查询标识id
    private  long id;
    //发生的时间戳
    private  long timeStamp;
    //命令耗时
    private  long executionTime;
    //命令内容
    private  String command;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public long getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(long executionTime) {
        this.executionTime = executionTime;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    @Override
    public String toString() {
        return "RedisSlowLog{" +
                "id=" + id +
                ", timeStamp=" + timeStamp +
                ", executionTime=" + executionTime +
                ", command='" + command + '\'' +
                '}';
    }

}
