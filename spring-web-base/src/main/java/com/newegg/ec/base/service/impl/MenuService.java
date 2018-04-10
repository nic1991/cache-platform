package com.newegg.ec.base.service.impl;

import com.newegg.ec.base.dao.mysql.IMenuDao;
import com.newegg.ec.base.model.Menu;
import com.newegg.ec.base.model.User;
import com.newegg.ec.base.service.IMenuService;
import com.newegg.ec.base.util.UUIDUtil;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by jn50 on 2017/2/21.
 */
@Service
@Transactional
public class MenuService implements IMenuService{

    @Autowired
    private IMenuDao menuDao;

    @Override
    public JSONArray getMenus(User user) {
        if(user.getId().equals("1"))
            return JSONArray.fromObject(menuDao.getMenuList());
        else
            return JSONArray.fromObject(menuDao.getMenuByUser(user.getId()));
    }

    @Override
    public boolean addMenu(Menu menu) {
        if(menu!=null && menu.getId()==null){
            menu.setId(UUIDUtil.getUUID());
            menu.setIsSysMenu("false");
        }
        return menuDao.addMenu(menu);
    }

    @Override
    public boolean updateMenu(Menu menu) {
        return menuDao.updateMenu(menu);
    }

    @Override
    public boolean deleteMenu(Menu menu) {
        return menuDao.deleteMenu(menu);
    }
}
