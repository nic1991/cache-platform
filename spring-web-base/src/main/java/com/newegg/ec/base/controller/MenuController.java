package com.newegg.ec.base.controller;

import com.newegg.ec.base.model.CommonResponse;
import com.newegg.ec.base.model.Menu;
import com.newegg.ec.base.model.User;
import com.newegg.ec.base.service.IMenuService;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by jn50 on 2017/2/21.
 */
@Controller
@RequestMapping("/rest/menu/*")
public class MenuController {

    @Autowired
    private IMenuService menuService;

    @RequestMapping(value="/getMenus",method= RequestMethod.GET)
    @ResponseBody
    public CommonResponse getMenus(HttpServletRequest request){
        CommonResponse cmmResponse = new CommonResponse();
        JSONArray menus;
        User user= new User();
        if(request.getSession().getAttribute("id")!=null){
            user.setId(request.getSession().getAttribute("id").toString());
        }
        menus = menuService.getMenus(user);
        cmmResponse.setStatus("1");
        cmmResponse.setInfo("");
        cmmResponse.setResult(menus);
        return cmmResponse;
    }

    @RequestMapping(value="/addMenu",method= RequestMethod.POST)
    @ResponseBody
    public CommonResponse addMenu(@RequestBody Menu menu){
        CommonResponse cmmResponse = new CommonResponse();
        boolean b = menuService.addMenu(menu);
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

    @RequestMapping(value="/updateMenu",method= RequestMethod.POST)
    @ResponseBody
    public CommonResponse updateMenu(@RequestBody Menu menu){
        CommonResponse cmmResponse = new CommonResponse();
        boolean b = menuService.updateMenu(menu);
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

    @RequestMapping(value="/deleteMenu",method= RequestMethod.POST)
    @ResponseBody
    public CommonResponse deleteMenu(@RequestBody Menu menu){
        CommonResponse cmmResponse = new CommonResponse();
        boolean b = menuService.deleteMenu(menu);
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

}
