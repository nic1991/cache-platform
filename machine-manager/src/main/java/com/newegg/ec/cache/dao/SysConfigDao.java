package com.newegg.ec.cache.dao;

import com.newegg.ec.cache.model.entity.SystemConfig;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by lf52 on 2018/3/16.
 */
@Repository
public interface SysConfigDao {


    public List<SystemConfig> getConfigList(@Param("param")Map<String, Object> param);

    public SystemConfig getConfig(String key);

    public void Add(SystemConfig config);

    public void Update(SystemConfig config);

    public void Delete(String key);


}
