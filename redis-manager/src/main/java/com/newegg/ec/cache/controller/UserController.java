package com.newegg.ec.cache.controller;

import com.newegg.ec.cache.logic.UserLogic;
import com.newegg.ec.cache.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by gl49 on 2018/4/20.
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserLogic logic;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public List<User> list(){
        return logic.getUserList();
    }

    @RequestMapping(value = "/getUser", method = RequestMethod.GET)
    @ResponseBody
    public User getUser(@RequestParam int id){
        return logic.getUser( id );
    }
}
