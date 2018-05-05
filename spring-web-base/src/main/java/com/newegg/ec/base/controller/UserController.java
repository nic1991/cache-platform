package com.newegg.ec.base.controller;

import com.newegg.ec.base.model.CommonResponse;
import com.newegg.ec.base.model.User;
import com.newegg.ec.base.service.IUserService;
import com.newegg.ec.base.util.Constant;
import net.sf.json.JSONArray;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


/**
 * Created by jn50 on 2016/4/12.
 */
@Controller
@RequestMapping("/rest/user/*")
public class UserController {

    @Autowired
    private IUserService userService;

    private static Logger logger=Logger.getLogger(UserController.class.getName());

    @RequestMapping(value="/login",method= RequestMethod.POST)
    @ResponseBody
    public CommonResponse login(@RequestBody User user, HttpServletRequest request){
        CommonResponse cmmResponse = new CommonResponse();
        user = userService.getLoginUser(user);
        if(user!=null) {
            request.getSession().setAttribute("id", user.getId());
            request.getSession().setAttribute("name", user.getName());
            request.getSession().setAttribute("password", user.getPassword());
            Map<String,Boolean> myPath = userService.getPathPermission(user);
            request.getSession().setAttribute("myPath", myPath);
            request.getSession().setAttribute("password", user.getPassword());
            cmmResponse.setStatus("1");
            cmmResponse.setInfo("login Success!");
            cmmResponse.setResult(user);
        }else{
            cmmResponse.setStatus("2");
            cmmResponse.setInfo("login failed!");
            cmmResponse.setError("name or password is not correct!");
        }
        return cmmResponse;
    }

    @RequestMapping(value="/domainLogin",method= RequestMethod.GET)
    @ResponseBody
    public CommonResponse domainLogin(@RequestParam("nameStr") String nameStr,HttpServletRequest request){
        CommonResponse cmmResponse = new CommonResponse();
        User user = userService.getDomainLoginUser(nameStr);
        if(user!=null) {
            request.getSession().setAttribute("id", user.getId());
            request.getSession().setAttribute("name", user.getName());
            request.getSession().setAttribute("password", user.getPassword());
            Map<String,Boolean> myPath = userService.getPathPermission(user);
            request.getSession().setAttribute("myPath", myPath);
            request.getSession().setAttribute("password", user.getPassword());
            cmmResponse.setStatus("1");
            cmmResponse.setInfo("login Success!");
            cmmResponse.setResult(user);
        }else{
            cmmResponse.setStatus("2");
            cmmResponse.setInfo("login failed!");
            cmmResponse.setError("name or password is not correct!");
        }
        return cmmResponse;
    }


    @RequestMapping(value="/getUser",method= RequestMethod.GET)
    @ResponseBody
    public CommonResponse getUser(HttpServletRequest request) throws Exception {
        CommonResponse cmmResponse = new CommonResponse();
        User user = new User();
        if(request.getSession().getAttribute("name")!=null && request.getSession().getAttribute("password")!=null ) {
            user.setId((String) (request.getSession().getAttribute("id")));
            user.setName((String) (request.getSession().getAttribute("name")));
            user.setPassword((String) (request.getSession().getAttribute("password")));
            cmmResponse.setStatus("1");
            cmmResponse.setInfo("");
            cmmResponse.setResult(user);
        }else{
            cmmResponse.setStatus("2");
            cmmResponse.setError("user invalid");
        }
        return cmmResponse;
    }

    @RequestMapping(value="/getUsers",method= RequestMethod.GET)
    @ResponseBody
    public CommonResponse getUsers(@RequestParam("searchStr") String searchStr){
        CommonResponse cmmResponse = new CommonResponse();
        JSONArray users;
        users = userService.getUsers(searchStr);
        cmmResponse.setStatus("1");
        cmmResponse.setInfo("");
        cmmResponse.setResult(users);
        return cmmResponse;
    }

    @RequestMapping(value="/updateUserPassword",method= RequestMethod.POST)
    @ResponseBody
    public CommonResponse updateUserPassword(@RequestBody User user, HttpServletRequest request){
        CommonResponse cmmResponse = new CommonResponse();
        boolean b = userService.updateUserPassword(user);
        if(b) {
            cmmResponse.setStatus("1");
            cmmResponse.setInfo("update user Success!");
            cmmResponse.setResult(user);
        }else{
            cmmResponse.setStatus("2");
            cmmResponse.setInfo("add user failed!");
            cmmResponse.setError("user name may exist!");
        }
        return cmmResponse;
    }

    @RequestMapping(value="/saveUser",method= RequestMethod.POST)
    @ResponseBody
    public CommonResponse saveUser(@RequestBody User user, HttpServletRequest request){
        CommonResponse cmmResponse = new CommonResponse();
        boolean b = userService.addOrUpdateUser(user);
        if(b) {
            cmmResponse.setStatus("1");
            cmmResponse.setInfo("add user Success!");
            cmmResponse.setResult(user);
        }else{
            cmmResponse.setStatus("2");
            cmmResponse.setInfo("add user failed!");
            cmmResponse.setError("user name may exist!");
        }
        return cmmResponse;
    }

    @RequestMapping(value="/deleteUser",method= RequestMethod.POST)
    @ResponseBody
    public CommonResponse deleteUser(@RequestBody User user, HttpServletRequest request){
        CommonResponse cmmResponse = new CommonResponse();
        boolean b = userService.deleteUser(user);
        if(b) {
            cmmResponse.setStatus("1");
            cmmResponse.setInfo("delete user Success!");
            cmmResponse.setResult(user);
        }else{
            cmmResponse.setStatus("2");
            cmmResponse.setInfo("delete user failed!");
            cmmResponse.setError("something wrong!");
        }
        return cmmResponse;
    }

    @RequestMapping(value="/logout",method= RequestMethod.GET)
    @ResponseBody
    public String logout( HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.getSession().invalidate();
        response.sendRedirect(Constant.BASEPATH + Constant.PAGEPATH+"/index.html");
        return "ok";
    }

    @RequestMapping(value="/loginAlert",method= RequestMethod.GET)
    @ResponseBody
    public CommonResponse loginAlert( HttpServletRequest request, HttpServletResponse response) throws Exception {
        CommonResponse cmmResponse = new CommonResponse();
        cmmResponse.setStatus("0");
        cmmResponse.setError("please login!");
        return cmmResponse;
    }

    @RequestMapping(value="/permissionAlert",method= RequestMethod.GET)
    @ResponseBody
    public CommonResponse permissionAlert( HttpServletRequest request, HttpServletResponse response) throws Exception {
        CommonResponse cmmResponse = new CommonResponse();
        cmmResponse.setStatus("-1");
        cmmResponse.setError("not permitted!");
        return cmmResponse;
    }

}
