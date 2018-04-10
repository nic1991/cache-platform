package com.newegg.ec.base.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by jn50 on 2018/1/27.
 */
@Controller
@RequestMapping("/rest/pages/*")
public class HtmlController {

    @RequestMapping("/index")
    public String index(Model model) {
        return "base/index";
    }

    @RequestMapping("/home")
    public String home(Model model) {
        return "base/home";
    }

    @RequestMapping("/main")
    public String main(Model model) {
        return "base/main";
    }

    @RequestMapping("/about")
    public String about(Model model) {
        return "base/about";
    }

    @RequestMapping("/login")
    public String login(Model model) {
        return "base/index";
    }

    @RequestMapping("/profile")
    public String profile(Model model) {
        return "base/profile";
    }

    @RequestMapping("/session_timeout")
    public String session_timeout(Model model) {
        return "base/session_timeout";
    }

    @RequestMapping("/menuManage")
    public String menuManage(Model model) {
        return "base/menuManage";
    }

    @RequestMapping("/urlManage")
    public String urlManage(Model model) {
        return "base/urlManage";
    }

    @RequestMapping("/roleManage")
    public String roleManage(Model model) {
        return "base/roleManage";
    }

    @RequestMapping("/userManage")
    public String userManage(Model model) {
        return "base/userManage";
    }

    @RequestMapping("/blank")
    public String blank(Model model) {
        return "base/blank";
    }



}
