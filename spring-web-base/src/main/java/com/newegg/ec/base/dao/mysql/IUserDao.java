package com.newegg.ec.base.dao.mysql;

import com.newegg.ec.base.model.User;
import java.util.List;
import java.util.Map;

/**
 * Created by jn50 on 2016/12/27.
 */
public interface IUserDao {

    int getUserCount();

    User getLoginUser(User user);

    User getLoginUserByName(String name);

    List<User> getAllUser();

    List<User> getFilterUser(String searchStr);

    List<User> getUserByName(String name);

    List<User> getUserByNameAndId(User user);

    boolean addUser(User user);

    boolean deleteUser(User user);

    boolean updateUser(User user);

    boolean updateUserPassword(User user);

    boolean addUserRole(Map<String, String> map);

    boolean deleteUserRole(String userId);

    List<String> getUserRoleIds(String userId);
}
