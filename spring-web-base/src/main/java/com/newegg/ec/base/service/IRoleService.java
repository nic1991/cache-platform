package com.newegg.ec.base.service;

import com.newegg.ec.base.model.Role;
import net.sf.json.JSONArray;

/**
 * Created by jn50 on 2017/2/21.
 */
public interface IRoleService {

    JSONArray getRoleList(String searchStr);

    JSONArray getRoles();

    boolean addOrUpdateRole(Role role);

    boolean deleteRole(Role role);

}
