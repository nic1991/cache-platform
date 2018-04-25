package com.newegg.ec.cache.app.controller;

import com.newegg.ec.cache.app.model.Response;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gl49 on 2018/4/25.
 */
@Controller
@RequestMapping("/demo")
public class DemoController {
    @RequestMapping("/smarty")
    public String list(Model model){
        return "demo/smarty";
    }

    @RequestMapping("/form")
    public String form(Model model){
        return "demo/form";
    }

    @RequestMapping(value = "/getList", method = RequestMethod.GET)
    @ResponseBody
    public Response getList(){
        List list = new ArrayList<>();
        Map<String, String> obj = new HashMap<>();
        obj.put("username", "username001");
        obj.put("password", "password001");
        list.add( obj );
        return Response.Obj(0, list);
    }

    @RequestMapping(value = "/postList", method = RequestMethod.POST)
    @ResponseBody
    public Response postList(@RequestBody String req) throws InterruptedException {
        List list = new ArrayList<>();
        Map<String, String> obj = new HashMap<>();
        obj.put("username", "username001");
        obj.put("password", "password001");
        obj.put("req", req.toString());
        list.add( obj );
        Thread.sleep(2000);
        return Response.Obj(0, list);
    }

    @RequestMapping(value = "/check", method = RequestMethod.POST)
    @ResponseBody
    public Response check(@RequestBody JSONObject req) throws InterruptedException {
        System.out.println( req );
        Thread.sleep(2000);
        return Response.OK();
    }

}
