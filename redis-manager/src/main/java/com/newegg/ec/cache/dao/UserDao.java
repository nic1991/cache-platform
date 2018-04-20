package com.newegg.ec.cache.dao;

import com.newegg.ec.cache.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by gl49 on 2018/4/20.
 */
@Repository
public interface  UserDao {
    public List<User> getUserList();
    public User getUser(int id);
    public void removeUser(int id);
    public void addUser(User user);
}
