package com.newegg.ec.base.service.impl;

import com.newegg.ec.base.dao.mysql.IUrlDao;
import com.newegg.ec.base.dao.mysql.IUserDao;
import com.newegg.ec.base.model.Url;
import com.newegg.ec.base.model.User;
import com.newegg.ec.base.service.IUserService;
import com.newegg.ec.base.util.DecryptUtil;
import com.newegg.ec.base.util.UUIDUtil;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by jn50 on 2016/12/27.
 */
@Service
@Transactional
public class UserService implements IUserService{

    @Autowired
    private IUserDao userDao;

    @Autowired
    private IUrlDao urlDao;

    @Override
    public User getLoginUser(User user) {
        return userDao.getLoginUser(user);
    }

    @Override
    public User getDomainLoginUser(String nameStr) {
        try {
            String name = DecryptUtil.DecryptDES(nameStr, "xxx");
            return userDao.getLoginUserByName(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getUserCount() {
        return userDao.getUserCount();
    }

    @Override
    public JSONArray getUsers(String searchStr) {
        List<User> users = userDao.getFilterUser(searchStr);
        for(int i=0;i<users.size();i++){
            List<String> roleIds = userDao.getUserRoleIds(users.get(i).getId());
            users.get(i).setRoles(JSONArray.fromObject(roleIds));
        }
        return JSONArray.fromObject(users);
    }

    @Override
    public boolean addOrUpdateUser(User user) {
        if(user.getId()==null || "".equals(user.getId())) {
            List<User> userList = userDao.getUserByName(user.getName());
            if (userList == null || userList.size() == 0) {
                user.setId(UUIDUtil.getUUID());
                userDao.addUser(user);
            }
        }else{
            userDao.updateUser(user);
        }
        updateUserRole(user.getId(),user.getRoles());
        return true;
    }

    @Override
    public boolean updateUserPassword(User user) {
        List<User> userList = userDao.getUserByNameAndId(user);
        if (userList == null || userList.size() == 0) {
            userDao.updateUserPassword(user);
            return true;
        }else{
            return false;
        }
    }

    public void updateUserRole(String userId,JSONArray roles){
        userDao.deleteUserRole(userId);
        if(roles!=null) {
            for (int i = 0; i < roles.size(); i++) {
                Map<String, String> map = new HashMap();
                map.put("userId", userId);
                map.put("roleId", roles.getString(i));
                userDao.addUserRole(map);
            }
        }
    }

    @Override
    public boolean deleteUser(User user) {
        if(!"root".equals(user.getName())) {
            userDao.deleteUserRole(user.getId());
            userDao.deleteUser(user);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Map<String, Boolean> getPathPermission(User user) {
        Map<String, Boolean> myPath = new HashMap();
        if(user.getId().equals("1")){

        }else {
            List<Url> urls = urlDao.getUrlList();
            List<Url> permittedUrls = urlDao.getUserUrls(user.getId());
            for (int i = 0; i < urls.size(); i++) {
                myPath.put(urls.get(i).getUrl(), false);
            }
            for (int i = 0; i < permittedUrls.size(); i++) {
                if (myPath.get(permittedUrls.get(i).getUrl()) != true) {
                    myPath.put(permittedUrls.get(i).getUrl(), true);
                }
            }
        }
        return myPath;
    }

}
