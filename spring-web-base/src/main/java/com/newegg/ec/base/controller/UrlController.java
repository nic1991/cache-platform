package com.newegg.ec.base.controller;

import com.newegg.ec.base.model.CommonResponse;
import com.newegg.ec.base.model.Url;
import com.newegg.ec.base.service.IUrlService;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by jn50 on 2017/2/21.
 */
@Controller
@RequestMapping("/rest/url/*")
public class UrlController {

    @Autowired
    private IUrlService urlService;

    @RequestMapping(value="/getUrls",method= RequestMethod.GET)
    @ResponseBody
    public CommonResponse getUrls(@RequestParam("searchStr") String searchStr){
        CommonResponse cmmResponse = new CommonResponse();
        JSONArray urls;
        urls = urlService.getUrlList(searchStr);
        cmmResponse.setStatus("1");
        cmmResponse.setInfo("");
        cmmResponse.setResult(urls);
        return cmmResponse;
    }

    @RequestMapping(value="/saveUrl",method= RequestMethod.POST)
    @ResponseBody
    public CommonResponse saveUrl(@RequestBody Url url){
        CommonResponse cmmResponse = new CommonResponse();
        boolean b=urlService.addOrUpdateUrl(url);
        if(b) {
            cmmResponse.setStatus("1");
            cmmResponse.setInfo("add menu Success!");
            cmmResponse.setResult(b);
        }else{
            cmmResponse.setStatus("2");
            cmmResponse.setInfo("add menu failed!");
            cmmResponse.setError("something wrong!");
        }
        return cmmResponse;
    }

    @RequestMapping(value="/deleteUrl",method= RequestMethod.POST)
    @ResponseBody
    public CommonResponse deleteUrl(@RequestBody Url url){
        CommonResponse cmmResponse = new CommonResponse();
        boolean b = urlService.deleteUrl(url);
        if(b) {
            cmmResponse.setStatus("1");
            cmmResponse.setInfo("add url Success!");
            cmmResponse.setResult(b);
        }else{
            cmmResponse.setStatus("2");
            cmmResponse.setInfo("add url failed!");
            cmmResponse.setError("something wrong!");
        }
        return cmmResponse;
    }

}
