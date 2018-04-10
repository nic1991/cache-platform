package com.newegg.ec.base.service;

import com.newegg.ec.base.model.User;
import net.sf.json.JSONArray;

import java.util.Map;

/**
 * Created by jn50 on 2016/12/27.
 */
public interface IUserService {

    User getLoginUser(User user);

    User getDomainLoginUser(String nameStr);

    int getUserCount();

    JSONArray getUsers(String searchStr);

    boolean addOrUpdateUser(User user);

    boolean updateUserPassword(User user);

    boolean deleteUser(User user);

    Map<String,Boolean> getPathPermission(User user);

}
