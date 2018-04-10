package com.newegg.ec.base.controller;

import com.newegg.ec.base.model.CommonResponse;
import com.newegg.ec.base.model.Role;
import com.newegg.ec.base.service.IRoleService;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by jn50 on 2017/2/21.
 */
@Controller
@RequestMapping("/rest/role/*")
public class RoleController {

    @Autowired
    private IRoleService roleService;

    @RequestMapping(value="/getRoles",method= RequestMethod.GET)
    @ResponseBody
    public CommonResponse getRoles(@RequestParam("searchStr") String searchStr){
        CommonResponse cmmResponse = new CommonResponse();
        JSONArray urls;
        urls = roleService.getRoleList(searchStr);
        cmmResponse.setStatus("1");
        cmmResponse.setInfo("");
        cmmResponse.setResult(urls);
        return cmmResponse;
    }

    @RequestMapping(value="/getRolesAsCandidates",method= RequestMethod.GET)
    @ResponseBody
    public CommonResponse getRolesAsCandidates(){
        CommonResponse cmmResponse = new CommonResponse();
        JSONArray urls;
        urls = roleService.getRoles();
        cmmResponse.setStatus("1");
        cmmResponse.setInfo("");
        cmmResponse.setResult(urls);
        return cmmResponse;
    }

    @RequestMapping(value="/saveRole",method= RequestMethod.POST)
    @ResponseBody
    public CommonResponse saveRole(@RequestBody Role role){
        CommonResponse cmmResponse = new CommonResponse();
        boolean b=roleService.addOrUpdateRole(role);
        if(b) {
            cmmResponse.setStatus("1");
            cmmResponse.setInfo("add role Success!");
            cmmResponse.setResult(b);
        }else{
            cmmResponse.setStatus("2");
            cmmResponse.setInfo("add role failed!");
            cmmResponse.setError("something wrong!");
        }
        return cmmResponse;
    }

    @RequestMapping(value="/deleteRole",method= RequestMethod.POST)
    @ResponseBody
    public CommonResponse deleteRole(@RequestBody Role role){
        CommonResponse cmmResponse = new CommonResponse();
        boolean b = roleService.deleteRole(role);
        if(b) {
            cmmResponse.setStatus("1");
            cmmResponse.setInfo("add role Success!");
            cmmResponse.setResult(b);
        }else{
            cmmResponse.setStatus("2");
            cmmResponse.setInfo("add role failed!");
            cmmResponse.setError("something wrong!");
        }
        return cmmResponse;
    }

}
