package com.newegg.ec.cache.dao.impl;

import com.newegg.ec.cache.dao.HttpClientDao;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * Created by lf52 on 2018/3/16.
 */
@Repository
public class HttpClientDaoImpl implements HttpClientDao,ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private CloseableHttpClient httpClient;

    @Autowired
    private RequestConfig config;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

    }

    @Override
    public String get(String url) throws Exception {
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(config);
        // 发起请求
        CloseableHttpResponse response = this.httpClient.execute(httpGet);
        // 判断状态码是否为200
        if (response.getStatusLine().getStatusCode() == 200) {
            // 返回响应体的内容
            return EntityUtils.toString(response.getEntity(), "UTF-8");
        }
        return null;
    }

    @Override
    public String post(String url) throws Exception {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(config);
        CloseableHttpResponse response = this.httpClient.execute(httpPost);
        // 判断状态码是否为200
        if (response.getStatusLine().getStatusCode() == 200) {
            // 返回响应体的内容
            return EntityUtils.toString(response.getEntity(), "UTF-8");
        }
        return null;
    }

    @Override
    public String doGet(String url, Map<String, Object> map) throws Exception {
        URIBuilder uriBuilder = new URIBuilder(url);
        if (map != null) {
            // 遍历map,拼接请求参数
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                uriBuilder.setParameter(entry.getKey(), entry.getValue().toString());
            }
        }

        // 调用不带参数的get请求
        return this.get(uriBuilder.build().toString());
    }

    @Override
    public String doPost(String url, Map<String, Object> map) throws Exception {

        return null;
    }


}
