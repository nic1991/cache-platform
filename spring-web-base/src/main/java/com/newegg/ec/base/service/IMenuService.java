package com.newegg.ec.base.service;

import com.newegg.ec.base.model.Menu;
import com.newegg.ec.base.model.User;
import net.sf.json.JSONArray;

/**
 * Created by jn50 on 2017/2/21.
 */
public interface IMenuService {

    JSONArray getMenus(User user);

    boolean addMenu(Menu menu);

    boolean updateMenu(Menu menu);

    boolean deleteMenu(Menu menu);
}
