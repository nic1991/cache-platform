package com.newegg.ec.base.controller;

import com.newegg.ec.base.model.CommonResponse;
import com.newegg.ec.base.model.User;
import com.newegg.ec.base.util.Constant;
import net.sf.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by jn50 on 2018/5/5.
 */
@Controller
@RequestMapping("/base/*")
public class BaseController {

    @RequestMapping(value="/getAppName",method= RequestMethod.GET)
    @ResponseBody
    public CommonResponse getAppName(HttpServletRequest request){
        CommonResponse cmmResponse = new CommonResponse();
        cmmResponse.setStatus("1");
        cmmResponse.setInfo("");
        cmmResponse.setResult(Constant.APPNAME);
        return cmmResponse;
    }
}
