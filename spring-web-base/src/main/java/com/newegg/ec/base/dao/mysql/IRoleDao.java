package com.newegg.ec.base.dao.mysql;

import com.newegg.ec.base.model.Role;
import java.util.List;
import java.util.Map;

/**
 * Created by jn50 on 2017/2/21.
 */
public interface IRoleDao {

    List<Role> getRoleList();

    List<Role> getFilterRoleList(String searchStr);

    List<String> getRoleMenuIds(String roleId);

    boolean addRoleMenu(Map<String, String> map);

    boolean deleteRoleMenu(String roleId);

    List<String> getRoleUrlIds(String roleId);

    boolean addRoleUrl(Map<String, String> map);

    boolean deleteRoleUrl(String roleId);

    boolean addRole(Role role);

    boolean updateRole(Role role);

    boolean deleteRole(Role role);
}
