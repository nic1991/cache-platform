package com.newegg.ec.cache.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by jn50 on 2018/1/31.
 */
@Controller
@RequestMapping("/rest/pages/*")
public class MachineManagerHtmlController {


    @RequestMapping("/machine")
    public String machine(Model model) {
        return "machine";
    }

    @RequestMapping("/machineinfo")
    public String machineInfo(Model model) {
        return "machineinfo";
    }

    @RequestMapping("/systemconfig")
    public String systemConfig(Model model) {
        return "systemconfig";
    }

    @RequestMapping("/earlywarning")
    public String earlyWarning(){
        return "earlywarning";
    }

}
