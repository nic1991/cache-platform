package com.newegg.ec.cache.dao;

import java.util.Map;

/**
 * Created by lf52 on 2018/3/16.
 */
public interface HttpClientDao {

    public String get(String url) throws Exception;

    public String post(String url) throws Exception;

    public String doGet(String url, Map<String, Object> map) throws Exception;

    public String doPost(String url,Map<String, Object> map) throws Exception;

}
