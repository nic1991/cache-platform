package com.newegg.ec.cache.controller;

import com.newegg.ec.cache.logic.MonitorLogic;
import com.newegg.ec.cache.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by gl49 on 2018/4/21.
 */
@Controller
@RequestMapping("/monitor")
public class MonitorController {
    @Resource
    private MonitorLogic logic;

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    @ResponseBody
    public boolean create(){
        return logic.addCluster("aaaddd");
    }
}
