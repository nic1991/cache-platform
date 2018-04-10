package com.newegg.ec.base.service.impl;

import com.newegg.ec.base.dao.mysql.IRoleDao;
import com.newegg.ec.base.model.Role;
import com.newegg.ec.base.service.IRoleService;
import com.newegg.ec.base.util.UUIDUtil;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jn50 on 2017/2/21.
 */
@Service
@Transactional
public class RoleService implements IRoleService{

    @Autowired
    private IRoleDao roleDao;

    @Override
    public JSONArray getRoleList(String searchStr) {
        List<Role> roleList;
        if(searchStr!=null && !"".equals(searchStr)){
            roleList =  roleDao.getFilterRoleList(searchStr);
        }else {
            roleList =  roleDao.getRoleList();
        }
        for(int i=0;i<roleList.size();i++){
            List<String> menuIds = roleDao.getRoleMenuIds(roleList.get(i).getId());
            List<String> urlIds = roleDao.getRoleUrlIds(roleList.get(i).getId());
            roleList.get(i).setMenuArray(JSONArray.fromObject(menuIds));
            roleList.get(i).setUrlArray(JSONArray.fromObject(urlIds));
        }
        return JSONArray.fromObject(roleList);
    }

    @Override
    public JSONArray getRoles() {
        List<Role> roleList;
        roleList =  roleDao.getRoleList();
        return JSONArray.fromObject(roleList);
    }

    @Override
    public boolean addOrUpdateRole(Role role) {
        if(role!=null && role.getId()!=null && !"".equals(role.getId())){
            roleDao.updateRole(role);
        }else{
            role.setId(UUIDUtil.getUUID());
            roleDao.addRole(role);
        }
        updateRoleMenu(role.getId(),role.getMenuArray());
        updateRoleUrl(role.getId(),role.getUrlArray());

        return true;
    }

    public void updateRoleMenu(String roleId,JSONArray menuArray){
        roleDao.deleteRoleMenu(roleId);
        if(menuArray!=null) {
            for (int i = 0; i < menuArray.size(); i++) {
                Map<String, String> map = new HashMap();
                map.put("roleId", roleId);
                map.put("menuId", menuArray.getString(i));
                roleDao.addRoleMenu(map);
            }
        }
    }

    public void updateRoleUrl(String roleId,JSONArray roleArray){
        roleDao.deleteRoleUrl(roleId);
        if(roleArray!=null) {
            for (int i = 0; i < roleArray.size(); i++) {
                Map<String, String> map = new HashMap();
                map.put("roleId", roleId);
                map.put("urlId", roleArray.getString(i));
                roleDao.addRoleUrl(map);
            }
        }
    }

    @Override
    public boolean deleteRole(Role role) {
        if (role != null && role.getId() != null) {
            roleDao.deleteRoleMenu(role.getId());
            roleDao.deleteRoleUrl(role.getId());
            roleDao.deleteRole(role);
            return true;
        } else {
            return false;
        }
    }
}
