package com.newegg.ec.cache.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.newegg.ec.cache.model.entity.SystemConfig;
import com.newegg.ec.cache.service.SysConfigService;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lf52 on 2018/3/20.
 */
@Controller
@RequestMapping("/rest/sysconfig/*")
public class SysConfigController {


    @Autowired
    private SysConfigService sysConfigService;

    @RequestMapping("/getSysConfig")
    @ResponseBody
    public JSONArray getSysConfig(HttpServletRequest request) throws IOException {

        String data = IOUtils.toString(request.getInputStream(), "UTF-8");
        JSONObject jsonObject = (JSONObject) JSONObject.parse(data);
        String type =  jsonObject.getString("type").trim();
        JSONArray array = new JSONArray();
        Map<String,Object> param = new HashMap();
        if(StringUtils.isNotEmpty(type)){
            param.put("type",type);
        }
        List<SystemConfig> list = sysConfigService.getConfigList(param);
        list.forEach(conf ->{
            JSONObject obj = new JSONObject();
            obj.put("key",conf.getConf_key());
            obj.put("value",conf.getConf_value());
            obj.put("info",conf.getInfo());
            array.add(obj);
        });
        return array;

    }

    @RequestMapping("/delSysConfig")
    @ResponseBody
    public JSONObject delSysConfig(HttpServletRequest request) throws IOException {

        String data = IOUtils.toString(request.getInputStream(), "UTF-8");
        JSONObject jsonObject = (JSONObject) JSONObject.parse(data);
        String key =  jsonObject.getString("key").trim();

        JSONObject obj = new JSONObject();
        if(sysConfigService.Delete(key)){
            obj.put("code", "0");
        }else{
            obj.put("code", "1");
        }
        return obj;
    }

    @RequestMapping("/SaveConfig")
    @ResponseBody
    public JSONObject SaveConfig(HttpServletRequest request) throws IOException {

        String data = IOUtils.toString(request.getInputStream(), "UTF-8");
        JSONObject jsonObject = (JSONObject) JSONObject.parse(data);
        String key =  jsonObject.getString("key").trim();
        String value =  jsonObject.getString("value").trim();
        String info =  jsonObject.getString("info").trim();
        String type =  jsonObject.getString("type").trim();
        SystemConfig conf = new SystemConfig();
        conf.setConf_key(key);
        conf.setConf_value(value);
        conf.setInfo(info);
        conf.setType(type);
        JSONObject obj = new JSONObject();
        if(sysConfigService.Add(conf)){
            obj.put("code", "0");
        }else{
            obj.put("code", "1");
        }
        return obj;
    }

    @RequestMapping("/UpdateConfig")
    @ResponseBody
    public JSONObject UpdateConfig(HttpServletRequest request) throws IOException {

        String data = IOUtils.toString(request.getInputStream(), "UTF-8");
        JSONObject jsonObject = (JSONObject) JSONObject.parse(data);
        String key =  jsonObject.getString("key").trim();
        String value =  jsonObject.getString("value").trim();
        SystemConfig conf = sysConfigService.getConfig(key);
        conf.setConf_value(value);
        JSONObject obj = new JSONObject();
        if(sysConfigService.Update(conf)){
            obj.put("code", "0");
        }else{
            obj.put("code", "1");
        }
        return obj;
    }

}
