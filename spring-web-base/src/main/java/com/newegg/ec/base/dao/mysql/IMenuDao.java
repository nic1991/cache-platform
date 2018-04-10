package com.newegg.ec.base.dao.mysql;

import com.newegg.ec.base.model.Menu;
import java.util.List;


/**
 * Created by jn50 on 2016/12/27.
 */
public interface IMenuDao {

    List<Menu> getMenuList();

    List<Menu> getMenuByUser(String userId);

    boolean addMenu(Menu menu);

    boolean updateMenu(Menu menu);

    boolean deleteMenu(Menu menu);

}
