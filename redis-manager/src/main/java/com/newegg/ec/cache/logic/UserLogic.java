package com.newegg.ec.cache.logic;

import com.newegg.ec.cache.dao.IUserDao;
import com.newegg.ec.cache.model.User;
import com.newegg.ec.cache.util.RedisNodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by gl49 on 2018/4/20.
 */
@Component
public class UserLogic {
    @Resource
    RedisNodeUtil redisNodeUtil;

    @Autowired
    private IUserDao userDao;

    public List<User> getUserList(){
        return userDao.getUserList();
    }

    public User getUser(int id){
        return userDao.getUser( id );
    }

}
